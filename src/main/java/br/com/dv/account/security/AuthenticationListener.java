package br.com.dv.account.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
public class AuthenticationListener {

    private static final String AUTHENTICATION_SUCCESS_MESSAGE = "Authentication success for user: {}";
    private static final String AUTHENTICATION_FAILURE_MESSAGE = "Authentication failure for user: {}";
    private static final String USER_LOCKED_MESSAGE = "User account is locked.";
    private static final String REQUEST_ATTRIBUTES_IS_NULL_MESSAGE = "Request attributes is null.";

    private final LoginAttemptService loginAttemptService;

    public AuthenticationListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        log.info(AUTHENTICATION_SUCCESS_MESSAGE, event.getAuthentication().getName());
        String username = event.getAuthentication().getName().toLowerCase();
        String path = getRequest().getRequestURI();
        loginAttemptService.updateLoginAttemptsForUser(username, path, true);
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        log.info(AUTHENTICATION_FAILURE_MESSAGE, event.getAuthentication().getName());
        String username = event.getAuthentication().getName().toLowerCase();
        String path = getRequest().getRequestURI();
        loginAttemptService.updateLoginAttemptsForUser(username, path, false);
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureLockedEvent event) {
        log.info(AUTHENTICATION_FAILURE_MESSAGE, event.getAuthentication().getName());
        HttpServletRequest request = getRequest();
        setLockedUserRequestAttributes(request);
    }

    private HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            throw new IllegalStateException(REQUEST_ATTRIBUTES_IS_NULL_MESSAGE);
        }

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private void setLockedUserRequestAttributes(HttpServletRequest request) {
        request.setAttribute("path", request.getRequestURI());
        request.setAttribute("locked_user_message", USER_LOCKED_MESSAGE);
    }

}
