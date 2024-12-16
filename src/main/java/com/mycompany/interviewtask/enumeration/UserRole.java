package com.mycompany.interviewtask.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Роль пользователя
 *
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String code;
}
