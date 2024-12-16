package com.mycompany.interviewtask.model.dto;

import com.mycompany.interviewtask.enumeration.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель покупателя
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String lastName;
    /**
     * Статус
     */
    private ClientStatus status;
    /**
     * Количество покупок
     */
    private Integer numberOfPurchases;
    /**
     * Количество возвратов
     */
    private Integer numberOfReturns;
    /**
     * Номер телефона
     */
    private String phoneNumber;
}
