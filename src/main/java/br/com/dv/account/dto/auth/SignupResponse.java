package br.com.dv.account.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SignupResponse(
        @JsonProperty("id")
        Long userId,
        String name,
        @JsonProperty("lastname")
        String lastName,
        String email,
        List<String> roles
) {
}
