package br.com.dv.account.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusMessage {

    DELETED_SUCCESSFULLY("Deleted successfully!"),
    ADDED_SUCCESSFULLY("Added successfully!"),
    UPDATED_SUCCESSFULLY("Updated successfully!");

    private final String message;

    StatusMessage(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }

}
