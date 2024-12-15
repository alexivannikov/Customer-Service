package com.mycompany.interviewtask.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Статус клиента
 */
@AllArgsConstructor
public enum ClientStatus {

    @JsonProperty("silver")
    SILVER("silver", 10),

    @JsonProperty("gold")
    GOLD("gold", 100),

    @JsonProperty("platinum")
    PLATINUM("platinum", 1000);

    private final String code;

    @Getter
    private final Integer rate;

    @JsonCreator
    public static ClientStatus getStatusFromCode(String code) {
       return Arrays.stream(ClientStatus.values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Статус клиента %s нераспознан", code)));
    }
}
