package br.com.dv.account.service;

import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;

public interface AuthService {

    SignupResponse signUp(SignupRequest signupRequest);

}
