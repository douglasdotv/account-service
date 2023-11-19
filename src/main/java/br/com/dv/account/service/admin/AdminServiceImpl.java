package br.com.dv.account.service.admin;

import br.com.dv.account.dto.admin.*;
import br.com.dv.account.entity.Role;
import br.com.dv.account.entity.User;
import br.com.dv.account.enums.UserAccessOperation;
import br.com.dv.account.enums.UserRoleOperation;
import br.com.dv.account.enums.StatusMessage;
import br.com.dv.account.exception.custom.RoleNotFoundException;
import br.com.dv.account.exception.custom.UserNotFoundException;
import br.com.dv.account.mapper.UserMapper;
import br.com.dv.account.repository.RoleRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.service.securityevent.logger.SecurityEventLogger;
import br.com.dv.account.validation.RoleValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String USER_ACCESS_UPDATE_MESSAGE = "User %s %s!";
    private static final String LOCKED = "locked";
    private static final String UNLOCKED = "unlocked";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidationService;
    private final SecurityEventLogger securityEventLogger;

    public AdminServiceImpl(UserRepository userRepository,
                            UserMapper userMapper,
                            RoleRepository roleRepository,
                            RoleValidationService roleValidationService,
                            SecurityEventLogger securityEventLogger) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.roleValidationService = roleValidationService;
        this.securityEventLogger = securityEventLogger;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserResponse> getUsers() {
        List<User> users = userRepository.findAllByOrderByIdAsc();
        return userMapper.mapToAdminUserResponseList(users);
    }

    @Override
    @Transactional
    public UserDeletionResponse deleteUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        roleValidationService.ensureUserIsNotRoleAdminBeforeDeletion(user);
        userRepository.delete(user);
        securityEventLogger.logDeleteUserEvent(userEmail);
        return new UserDeletionResponse(userEmail, StatusMessage.DELETED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserRoles(RoleUpdateRequest roleUpdateRequest) {
        User user = findUserByEmail(roleUpdateRequest.userEmail());
        Role role = findRoleByName(roleUpdateRequest.role());

        roleValidationService.validateRoleUpdate(roleUpdateRequest, user);
        applyRoleUpdateToUser(user, role, roleUpdateRequest);
        userRepository.save(user);

        securityEventLogger.logRoleUpdateEvent(user.getEmail(), roleUpdateRequest.role(),
                roleUpdateRequest.operation());

        return userMapper.mapToAdminUserResponse(user);
    }

    @Override
    @Transactional
    public AccessUpdateResponse updateUserAccess(AccessUpdateRequest accessUpdateRequest) {
        User user = findUserByEmail(accessUpdateRequest.userEmail());

        roleValidationService.ensureUserIsNotRoleAdminBeforeLock(user);
        applyAccessUpdateToUser(user, accessUpdateRequest);
        userRepository.save(user);

        securityEventLogger.logUserAccessUpdateEvent(user.getEmail(), accessUpdateRequest.operation());

        String responseStatusMessage = getAccessUpdateStatusMessage(user, accessUpdateRequest.operation());
        return new AccessUpdateResponse(responseStatusMessage);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    private Role findRoleByName(String role) {
        return roleRepository.findByName(ROLE_PREFIX + role).orElseThrow(() -> new RoleNotFoundException(role));
    }

    private void applyRoleUpdateToUser(User user, Role role, RoleUpdateRequest roleUpdateRequest) {
        UserRoleOperation operation = roleUpdateRequest.operation();
        if (operation == UserRoleOperation.REMOVE) {
            user.getRoles().remove(role);
        } else if (operation == UserRoleOperation.GRANT) {
            user.getRoles().add(role);
        }
    }

    private void applyAccessUpdateToUser(User user, AccessUpdateRequest accessUpdateRequest) {
        UserAccessOperation operation = accessUpdateRequest.operation();
        if (operation == UserAccessOperation.LOCK) {
            user.setLocked(true);
        } else if (operation == UserAccessOperation.UNLOCK) {
            user.setLocked(false);
        }
    }

    private String getAccessUpdateStatusMessage(User user, UserAccessOperation operation) {
        String operationResult = operation == UserAccessOperation.LOCK ? LOCKED : UNLOCKED;
        return String.format(
                USER_ACCESS_UPDATE_MESSAGE,
                user.getEmail(), operationResult
        );
    }

}
