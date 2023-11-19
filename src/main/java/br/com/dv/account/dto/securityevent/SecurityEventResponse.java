package br.com.dv.account.dto.securityevent;

import br.com.dv.account.enums.SecurityAction;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SecurityEventResponse(
        @JsonProperty("id")
        Long eventId,
        LocalDateTime date,
        SecurityAction action,
        String subject,
        String object,
        String path
) {
}
