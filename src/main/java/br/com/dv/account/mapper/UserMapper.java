package br.com.dv.account.mapper;

import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User signupRequestToUser(SignupRequest signupRequest);

    @Mapping(source = "id", target = "userId")
    SignupResponse userToSignupResponse(User user);

}
