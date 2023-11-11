package br.com.dv.account.service;

import br.com.dv.account.dto.PasswordChangeRequest;
import br.com.dv.account.dto.PasswordChangeResponse;
import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.UserAuthenticationMismatchException;
import br.com.dv.account.mapper.UserMapper;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.service.validation.UserValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           UserValidationService userValidationService,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignupResponse signUp(SignupRequest signup) {
        userValidationService.validateSignup(signup.email(), signup.password());

        User user = userMapper.signupRequestToUser(signup);
        user.setPassword(passwordEncoder.encode(signup.password()));
        User savedUser = userRepository.save(user);

        return userMapper.userToSignupResponse(savedUser);
    }

    @Override
    public PasswordChangeResponse changePassword(PasswordChangeRequest passwordChange,
                                                 String userEmail,
                                                 String currentPassword) {
        userValidationService.validatePasswordChange(passwordChange.newPassword(), currentPassword);

        User user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UserAuthenticationMismatchException(userEmail));
        user.setPassword(passwordEncoder.encode(passwordChange.newPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.userToPasswordChangeResponse(savedUser);
    }

}
