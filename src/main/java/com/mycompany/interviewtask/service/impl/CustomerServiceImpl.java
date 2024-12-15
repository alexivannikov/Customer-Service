package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.exception.DataNotFoundException;
import com.mycompany.interviewtask.mapper.CommonMapper;
import com.mycompany.interviewtask.model.dao.Customer;
import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.repository.CustomerRepository;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.utils.CommonUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CommonMapper mapper;

    @Override
    @Transactional
    public List<UUID> save(List<CustomerDto> customerDtoList) {
        return customerRepository.saveAll(
                        mapper.fromDtoList(customerDtoList)
                ).stream()
                .map(Customer::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public byte[] getPhoneNumbers() throws IOException {
        Collection<String> phoneNumbers = customerRepository.findAll().stream()
                .map(Customer::getPhoneNumber)
                .filter(phoneNumber -> !phoneNumber.contains("+7"))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (phoneNumbers.isEmpty()) {
            throw new DataNotFoundException("Список телефонных номеров покупателей пуст");
        }
        return CommonUtils.writeStringToOutputStream(CommonUtils.convertCollectionToString(phoneNumbers));
    }
}
