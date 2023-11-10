package br.com.dv.account.mapper;

import br.com.dv.account.dto.PasswordChangeResponse;
import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private static final String EMAIL_TO_LOWERCASE_EXPRESSION = "java(user.getEmail().toLowerCase())";
    private static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = "The password has been successfully updated.";

    @Mapping(target = "password", ignore = true)
    public abstract User signupRequestToUser(SignupRequest signupRequest);

    @Mapping(target = "userId", source = "id")
    public abstract SignupResponse userToSignupResponse(User user);

    @Mapping(target = "email", expression = EMAIL_TO_LOWERCASE_EXPRESSION)
    @Mapping(target = "status", constant = PASSWORD_UPDATE_SUCCESS_MESSAGE)
    public abstract PasswordChangeResponse mapToPasswordChangeResponse(User user);

}
