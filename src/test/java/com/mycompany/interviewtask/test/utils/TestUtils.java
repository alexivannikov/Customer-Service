package com.mycompany.interviewtask.test.utils;

import com.mycompany.interviewtask.enumeration.ClientStatus;
import com.mycompany.interviewtask.model.dto.CustomerDto;

public class TestUtils {
    public static CustomerDto getCustomerDto(Integer numberOfPurchases,
                                             Integer numberOfReturns,
                                             ClientStatus status,
                                             String phoneNumber) {
        return new CustomerDto("firstName", "lastName", status, numberOfPurchases, numberOfReturns, phoneNumber);
    }
}
