package br.com.dv.account.mapper;

import br.com.dv.account.dto.securityevent.SecurityEventResponse;
import br.com.dv.account.entity.SecurityEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SecurityEventMapper {

    @SuppressWarnings("unused")
    @Mapping(target = "eventId", source = "id")
    public abstract SecurityEventResponse toSecurityEventResponse(SecurityEvent securityEvent);

    public abstract List<SecurityEventResponse> toSecurityEventResponseList(List<SecurityEvent> securityEvents);

}
