package br.com.dv.account.controller;

import br.com.dv.account.dto.PasswordChangeRequest;
import br.com.dv.account.dto.PasswordChangeResponse;
import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = authService.signUp(signupRequest);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/changepass")
    public ResponseEntity<PasswordChangeResponse> changePassword(
            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PasswordChangeResponse passwordChangeResponse = authService.changePassword(
                passwordChangeRequest,
                userDetails.getUsername(),
                userDetails.getPassword()
        );
        return ResponseEntity.ok(passwordChangeResponse);
    }

}
