package br.com.dv.account.service;

import br.com.dv.account.dto.PasswordChangeRequest;
import br.com.dv.account.dto.PasswordChangeResponse;
import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;

public interface AuthService {

    SignupResponse signUp(SignupRequest signup);

    PasswordChangeResponse changePassword(PasswordChangeRequest passwordChange,
                                          String userEmail,
                                          String currentPassword);

}
