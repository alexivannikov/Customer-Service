package com.mycompany.interviewtask.test.mapper;

import com.mycompany.interviewtask.enumeration.ClientStatus;
import com.mycompany.interviewtask.mapper.CommonMapper;
import com.mycompany.interviewtask.mapper.CommonMapperImpl;
import com.mycompany.interviewtask.test.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(CommonMapperImpl.class)
public class CommonMapperTest {

    @Autowired
    CommonMapper mapper;

    @Test
    void fromDtoTest() {
        var source = TestUtils.getCustomerDto(10, 5, ClientStatus.PLATINUM, "+7-123-456-78-90");

        var target = mapper.fromDto(source);

        Assertions.assertNull(target.getId());
        Assertions.assertEquals(source.getFirstName(), target.getFirstName());
        Assertions.assertEquals(source.getLastName(), target.getLastName());
        Assertions.assertEquals(
                source.getNumberOfPurchases() - source.getNumberOfReturns() + source.getStatus().getRate(),
                target.getRating()
        );
        Assertions.assertEquals(source.getPhoneNumber(), target.getPhoneNumber());
    }

    @Test
    void fromDtoListTest() {
        var source = List.of(TestUtils.getCustomerDto(10, 5, ClientStatus.PLATINUM, "+7-123-456-78-90"));

        var target = mapper.fromDtoList(source);

        Assertions.assertNull(target.get(0).getId());
        Assertions.assertEquals(source.get(0).getFirstName(), target.get(0).getFirstName());
        Assertions.assertEquals(source.get(0).getLastName(), target.get(0).getLastName());
        Assertions.assertEquals(
                source.get(0).getNumberOfPurchases() -
                        source.get(0).getNumberOfReturns() +
                        source.get(0).getStatus().getRate(),
                target.get(0).getRating()
        );
        Assertions.assertEquals(source.get(0).getPhoneNumber(), target.get(0).getPhoneNumber());
    }
}
