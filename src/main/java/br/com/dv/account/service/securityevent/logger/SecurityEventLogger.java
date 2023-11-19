package br.com.dv.account.service.securityevent.logger;

import br.com.dv.account.entity.SecurityEvent;
import br.com.dv.account.enums.SecurityAction;
import br.com.dv.account.enums.UserAccessOperation;
import br.com.dv.account.enums.UserRoleOperation;
import br.com.dv.account.repository.SecurityEventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityEventLogger {

    private static final String UNAUTHENTICATED_USER = "Anonymous";
    private static final String GRANT_ROLE_TEMPLATE = "Grant role %s to %s";
    private static final String REMOVE_ROLE_TEMPLATE = "Remove role %s from %s";
    private static final String LOCK_USER_TEMPLATE = "Lock user %s";
    private static final String UNLOCK_USER_TEMPLATE = "Unlock user %s";
    private static final String SIGN_UP_PATH = "/api/auth/signup";
    private static final String CHANGE_PASSWORD_PATH = "/api/auth/changepass";
    private static final String UPDATE_USER_ROLE_PATH = "/api/admin/user/role";
    private static final String UPDATE_USER_ACCESS_PATH = "/api/admin/user/access";
    private static final String DELETE_USER_PATH = "/api/admin/user";

    private final SecurityEventRepository securityEventRepository;

    public SecurityEventLogger(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    public void logCreateUserEvent(String objUsername) {
        logEvent(
                SecurityAction.CREATE_USER,
                UNAUTHENTICATED_USER,
                objUsername.toLowerCase(),
                SIGN_UP_PATH
        );
    }

    public void logChangePasswordEvent(String objUsername) {
        logEvent(
                SecurityAction.CHANGE_PASSWORD,
                getAuthenticatedUser().toLowerCase(),
                objUsername.toLowerCase(),
                CHANGE_PASSWORD_PATH
        );
    }

    public void logRoleUpdateEvent(String objUsername, String objRole, UserRoleOperation operation) {
        if (operation == UserRoleOperation.GRANT) {
            logGrantRoleEvent(objUsername.toLowerCase(), objRole);
        } else if (operation == UserRoleOperation.REMOVE) {
            logRemoveRoleEvent(objUsername.toLowerCase(), objRole);
        }
    }

    public void logUserAccessUpdateEvent(String objUsername, UserAccessOperation operation) {
        if (operation == UserAccessOperation.LOCK) {
            logLockUserEvent(objUsername.toLowerCase());
        } else if (operation == UserAccessOperation.UNLOCK) {
            logUnlockUserEvent(objUsername.toLowerCase());
        }
    }

    public void logDeleteUserEvent(String objUsername) {
        logEvent(
                SecurityAction.DELETE_USER,
                getAuthenticatedUser().toLowerCase(),
                objUsername.toLowerCase(),
                DELETE_USER_PATH
        );
    }

    public void logAccessDeniedEvent(String path) {
        logEvent(
                SecurityAction.ACCESS_DENIED,
                getAuthenticatedUser().toLowerCase(),
                path,
                path
        );
    }

    public void logLoginFailedEvent(String sbjUsername, String path) {
        logEvent(
                SecurityAction.LOGIN_FAILED,
                sbjUsername.toLowerCase(),
                path,
                path
        );
    }

    public void logBruteForceEvent(String sbjUsername, String path) {
        logEvent(
                SecurityAction.BRUTE_FORCE,
                sbjUsername.toLowerCase(),
                path,
                path
        );
    }

    public void logPostBruteForceLockEvent(String objUsername, String path) {
        logEvent(
                SecurityAction.LOCK_USER,
                objUsername.toLowerCase(),
                String.format(LOCK_USER_TEMPLATE, objUsername.toLowerCase()),
                path
        );
    }

    private void logLockUserEvent(String objUsername) {
        logEvent(
                SecurityAction.LOCK_USER,
                getAuthenticatedUser().toLowerCase(),
                String.format(LOCK_USER_TEMPLATE, objUsername),
                UPDATE_USER_ACCESS_PATH
        );
    }

    private void logUnlockUserEvent(String objUsername) {
        logEvent(
                SecurityAction.UNLOCK_USER,
                getAuthenticatedUser().toLowerCase(),
                String.format(UNLOCK_USER_TEMPLATE, objUsername),
                UPDATE_USER_ACCESS_PATH
        );
    }

    private void logGrantRoleEvent(String objUsername, String objRole) {
        logEvent(
                SecurityAction.GRANT_ROLE,
                getAuthenticatedUser().toLowerCase(),
                String.format(GRANT_ROLE_TEMPLATE, objRole, objUsername),
                UPDATE_USER_ROLE_PATH
        );
    }

    private void logRemoveRoleEvent(String objUsername, String objRole) {
        logEvent(
                SecurityAction.REMOVE_ROLE,
                getAuthenticatedUser().toLowerCase(),
                String.format(REMOVE_ROLE_TEMPLATE, objRole, objUsername),
                UPDATE_USER_ROLE_PATH
        );
    }

    private void logEvent(SecurityAction action, String subject, String object, String path) {
        SecurityEvent event = new SecurityEvent(action, subject, object, path);
        securityEventRepository.save(event);
    }

    private String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
