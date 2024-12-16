package com.mycompany.interviewtask.mapper;

import com.mycompany.interviewtask.model.dao.Customer;
import com.mycompany.interviewtask.model.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonMapper {

    @Mapping(target = "rating",
            expression = "java(" +
                    "source.getNumberOfPurchases() - source.getNumberOfReturns() + source.getStatus().getRate()" +
                    ")")
    Customer fromDto(CustomerDto source);

    List<Customer> fromDtoList(List<CustomerDto> customerDtoList);
}
