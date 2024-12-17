package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.mapper.CommonMapper;
import com.mycompany.interviewtask.model.dao.Customer;
import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.repository.CustomerRepository;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.utils.CommonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CommonMapper mapper;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public byte[] create(List<CustomerDto> customerDtoList) throws IOException {
        var phoneNumbers = customerRepository.saveAll(mapper.fromDtoList(customerDtoList)).stream()
                .map(Customer::getPhoneNumber)
                .filter(phoneNumber -> !phoneNumber.contains("+7"))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return CommonUtils.writeStringToOutputStream(CommonUtils.convertCollectionToString(phoneNumbers));
    }
}
