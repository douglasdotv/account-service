package br.com.dv.account.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    USER("ROLE_USER"),
    ACCOUNTANT("ROLE_ACCOUNTANT");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

}
