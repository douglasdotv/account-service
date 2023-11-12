package br.com.dv.account.service.auth;

import br.com.dv.account.dto.auth.PasswordChangeRequest;
import br.com.dv.account.dto.auth.PasswordChangeResponse;
import br.com.dv.account.dto.auth.SignupRequest;
import br.com.dv.account.dto.auth.SignupResponse;

public interface AuthService {

    SignupResponse signUp(SignupRequest signupRequest);

    PasswordChangeResponse changePassword(PasswordChangeRequest passwordChangeRequest,
                                          String userEmail,
                                          String currentPassword);

}
