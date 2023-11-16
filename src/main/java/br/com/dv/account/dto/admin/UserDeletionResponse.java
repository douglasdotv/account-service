package br.com.dv.account.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDeletionResponse(@JsonProperty("user") String userEmail, String status) {
}
