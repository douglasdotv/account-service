package br.com.dv.account.service.auth;

import br.com.dv.account.dto.auth.PasswordChangeRequest;
import br.com.dv.account.dto.auth.PasswordChangeResponse;
import br.com.dv.account.dto.auth.SignupRequest;
import br.com.dv.account.dto.auth.SignupResponse;
import br.com.dv.account.entity.Role;
import br.com.dv.account.entity.User;
import br.com.dv.account.enums.RoleType;
import br.com.dv.account.exception.custom.RoleNotFoundException;
import br.com.dv.account.exception.custom.UserAuthenticationMismatchException;
import br.com.dv.account.mapper.UserMapper;
import br.com.dv.account.repository.RoleRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.service.securityevent.logger.SecurityEventLogger;
import br.com.dv.account.validation.UserValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidationService userValidationService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityEventLogger securityEventLogger;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserValidationService userValidationService,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           SecurityEventLogger securityEventLogger) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidationService = userValidationService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityEventLogger = securityEventLogger;
    }

    @Override
    @Transactional
    public SignupResponse signUp(SignupRequest signupRequest) {
        userValidationService.validateSignup(signupRequest);

        User user = userMapper.mapToUser(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.password()));
        Role role = getSignupUserRole();
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);

        securityEventLogger.logCreateUserEvent(user.getEmail());

        return userMapper.mapToSignupResponse(savedUser);
    }

    @Override
    @Transactional
    public PasswordChangeResponse changePassword(PasswordChangeRequest passwordChangeRequest,
                                                 String userEmail,
                                                 String currentPassword) {
        userValidationService.validatePasswordChange(passwordChangeRequest, currentPassword);

        User user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UserAuthenticationMismatchException(userEmail));
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.newPassword()));
        User savedUser = userRepository.save(user);

        securityEventLogger.logChangePasswordEvent(user.getEmail());

        return userMapper.mapToPasswordChangeResponse(savedUser);
    }

    private Role getSignupUserRole() {
        long userCount = userRepository.count();
        String roleName = userCount == 0
                ? RoleType.ADMINISTRATOR.getName()
                : RoleType.USER.getName();
        return roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }

}
