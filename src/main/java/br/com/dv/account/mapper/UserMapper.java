package br.com.dv.account.mapper;

import br.com.dv.account.dto.auth.PasswordChangeResponse;
import br.com.dv.account.dto.auth.SignupRequest;
import br.com.dv.account.dto.auth.SignupResponse;
import br.com.dv.account.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private static final String EMAIL_TO_LOWERCASE_EXPRESSION = "java(user.getEmail().toLowerCase())";
    private static final String TO_LIST_OF_ROLES_EXPRESSION =
            "java(user.getRoles().stream().map(Role::getName).sorted().collect(java.util.stream.Collectors.toList()))";
    private static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = "The password has been successfully updated.";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract User mapToUser(SignupRequest signupRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "email", expression = EMAIL_TO_LOWERCASE_EXPRESSION)
    @Mapping(target = "roles", expression = TO_LIST_OF_ROLES_EXPRESSION)
    public abstract SignupResponse mapToSignupResponse(User user);

    @Mapping(target = "email", expression = EMAIL_TO_LOWERCASE_EXPRESSION)
    @Mapping(target = "status", constant = PASSWORD_UPDATE_SUCCESS_MESSAGE)
    public abstract PasswordChangeResponse mapToPasswordChangeResponse(User user);

}
