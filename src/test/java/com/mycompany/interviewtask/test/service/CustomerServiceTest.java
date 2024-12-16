package com.mycompany.interviewtask.test.service;

import com.mycompany.interviewtask.configuration.DatasourceConfiguration;
import com.mycompany.interviewtask.enumeration.ClientStatus;
import com.mycompany.interviewtask.mapper.CommonMapper;
import com.mycompany.interviewtask.mapper.CommonMapperImpl;
import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.repository.CustomerRepository;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.service.impl.CustomerServiceImpl;
import com.mycompany.interviewtask.test.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@Import({DatasourceConfiguration.class, CustomerServiceImpl.class, CommonMapperImpl.class})
public class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CommonMapper mapper;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void createCustomersTest() throws IOException {
        List<CustomerDto> customerDtoList = List.of(
                TestUtils.getCustomerDto(10, 5, ClientStatus.PLATINUM, "+7-123-456-78-90"),
                TestUtils.getCustomerDto(15, 5, ClientStatus.PLATINUM, "+9-123-456-78-90"),
                TestUtils.getCustomerDto(20, 5, ClientStatus.PLATINUM, "+8-123-456-78-90")
        );

        var result = List.of(new String(customerService.create(customerDtoList), StandardCharsets.UTF_8).split("\n"));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("+8-123-456-78-90", result.get(0));
        Assertions.assertEquals("+9-123-456-78-90", result.get(1));

        var savedInDatabase = customerRepository.findAll();

        Assertions.assertEquals(3, savedInDatabase.size());
        Assertions.assertNotNull(savedInDatabase.get(0).getId());
        Assertions.assertEquals(1005, savedInDatabase.get(0).getRating());
        Assertions.assertEquals("+7-123-456-78-90", savedInDatabase.get(0).getPhoneNumber());
        Assertions.assertEquals(1010, savedInDatabase.get(1).getRating());
        Assertions.assertEquals("+9-123-456-78-90", savedInDatabase.get(1).getPhoneNumber());
        Assertions.assertEquals(1015, savedInDatabase.get(2).getRating());
        Assertions.assertEquals("+8-123-456-78-90", savedInDatabase.get(2).getPhoneNumber());
    }
}
