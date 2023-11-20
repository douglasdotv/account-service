package br.com.dv.account.security;

import br.com.dv.account.entity.User;
import br.com.dv.account.enums.RoleType;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.service.securityevent.logger.SecurityEventLogger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int FAILED_ATTEMPT_INCREMENT = 1;

    private final Map<String, Integer> failedLoginAttemptsByUsername = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final SecurityEventLogger securityEventLogger;

    public LoginAttemptService(UserRepository userRepository, SecurityEventLogger securityEventLogger) {
        this.userRepository = userRepository;
        this.securityEventLogger = securityEventLogger;
    }

    public void updateLoginAttemptsForUser(String username, String path, boolean loginSucceeded) {
        if (loginSucceeded) {
            resetFailedLoginAttempts(username);
        } else {
            processFailedLogin(username, path);
        }
    }

    private void resetFailedLoginAttempts(String username) {
        failedLoginAttemptsByUsername.remove(username);
    }

    private void processFailedLogin(String username, String path) {
        securityEventLogger.logLoginFailedEvent(username, path);

        int attempts = incrementFailedAttempts(username);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            handleMaxLoginAttemptsReached(username, path);
        }
    }

    private int incrementFailedAttempts(String username) {
        return failedLoginAttemptsByUsername.merge(username, FAILED_ATTEMPT_INCREMENT, Integer::sum);
    }

    private void handleMaxLoginAttemptsReached(String username, String path) {
        logBruteForceEvent(username, path);

        if (!isAdmin(username)) {
            lockUser(username);
            logLockEvent(username, path);
            resetFailedLoginAttempts(username);
        }
    }

    private void lockUser(String username) {
        userRepository.findByEmailIgnoreCase(username).ifPresent(user -> {
            if (!user.isLocked()) {
                user.setLocked(true);
                userRepository.save(user);
            }
        });
    }

    private void logBruteForceEvent(String username, String path) {
        securityEventLogger.logBruteForceEvent(username, path);
    }

    private void logLockEvent(String username, String path) {
        securityEventLogger.logPostBruteForceLockEvent(username, path);
    }

    private boolean isAdmin(String username) {
        return userRepository.findByEmailIgnoreCase(username)
                .map(User::getRoles)
                .map(roles -> roles.stream().anyMatch(role -> role.getName().equals(RoleType.ADMINISTRATOR.getName())))
                .orElse(false);
    }

}
