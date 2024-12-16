package com.mycompany.interviewtask.model.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * Покупатель
 */
@Entity
@Data
@Table(name = "customer")
public class Customer implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * Имя
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Фамилия
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * Номер телефона
     */
    @Column(name = "phone_number")
    private String phoneNumber;
    /**
     * Рейтинг клиента
     */
    @Column(name = "rating")
    private Integer rating;
}
