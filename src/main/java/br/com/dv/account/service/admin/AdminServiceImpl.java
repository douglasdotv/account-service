package br.com.dv.account.service.admin;

import br.com.dv.account.dto.admin.AdminUserResponse;
import br.com.dv.account.dto.admin.RoleUpdateRequest;
import br.com.dv.account.dto.admin.UserDeletionResponse;
import br.com.dv.account.entity.Role;
import br.com.dv.account.entity.User;
import br.com.dv.account.enums.AdminOperation;
import br.com.dv.account.enums.StatusMessage;
import br.com.dv.account.exception.custom.RoleNotFoundException;
import br.com.dv.account.exception.custom.UserNotFoundException;
import br.com.dv.account.mapper.UserMapper;
import br.com.dv.account.repository.RoleRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.validation.RoleValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidationService;

    public AdminServiceImpl(UserRepository userRepository,
                            UserMapper userMapper,
                            RoleRepository roleRepository,
                            RoleValidationService roleValidationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.roleValidationService = roleValidationService;
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
        User user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        roleValidationService.validateUserDeletion(user);
        userRepository.delete(user);
        return new UserDeletionResponse(userEmail, StatusMessage.DELETED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserRole(RoleUpdateRequest roleUpdateRequest) {
        User user = userRepository.findByEmailIgnoreCase(roleUpdateRequest.userEmail())
                .orElseThrow(() -> new UserNotFoundException(roleUpdateRequest.userEmail()));

        roleValidationService.validateRoleUpdate(roleUpdateRequest, user);

        Role role = roleRepository.findByName(ROLE_PREFIX + roleUpdateRequest.role())
                .orElseThrow(() -> new RoleNotFoundException(roleUpdateRequest.role()));

        AdminOperation operation = roleUpdateRequest.operation();
        if (operation == AdminOperation.REMOVE) {
            user.getRoles().remove(role);
        } else if (operation == AdminOperation.GRANT) {
            user.getRoles().add(role);
        }

        userRepository.save(user);

        return userMapper.mapToAdminUserResponse(user);
    }

}
