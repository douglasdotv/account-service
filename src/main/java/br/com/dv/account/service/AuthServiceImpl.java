package br.com.dv.account.service;

import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.InvalidEmailDomainException;
import br.com.dv.account.exception.custom.UserEmailAlreadyExistsException;
import br.com.dv.account.mapper.UserMapper;
import br.com.dv.account.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final static String EXPECTED_EMAIL_DOMAIN = "@acme.com";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignupResponse signUp(SignupRequest signupRequest) {
        validateUserEmail(signupRequest.email());

        User user = userMapper.signupRequestToUser(signupRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.userToSignupResponse(savedUser);
    }

    private void validateUserEmail(String userEmail) {
        if (!userEmail.toLowerCase().endsWith(EXPECTED_EMAIL_DOMAIN)) {
            throw new InvalidEmailDomainException(userEmail);
        }

        if (userRepository.existsByEmailIgnoreCase(userEmail)) {
            throw new UserEmailAlreadyExistsException(userEmail);
        }
    }

}
