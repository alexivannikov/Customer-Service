package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.model.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий с данными покупателей
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
