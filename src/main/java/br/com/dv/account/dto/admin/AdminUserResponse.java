package br.com.dv.account.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AdminUserResponse(
        @JsonProperty("id")
        Long userId,
        String name,
        @JsonProperty("lastname")
        String lastName,
        String email,
        List<String> roles
) {
}
