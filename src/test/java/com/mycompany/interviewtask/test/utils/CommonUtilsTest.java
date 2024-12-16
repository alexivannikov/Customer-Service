package com.mycompany.interviewtask.test.utils;

import com.mycompany.interviewtask.enumeration.ClientStatus;
import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

public class CommonUtilsTest {
    @Test
    void getObjectFromFile() throws IOException {
        var fileContent = "{\n" +
                "    \"firstName\": \"Jennifer\",\n" +
                "    \"lastName\": \"Aniston\",\n" +
                "    \"status\": \"platinum\",\n" +
                "    \"numberOfPurchases\": \"1000\",\n" +
                "    \"numberOfReturns\": \"100\",\n" +
                "    \"phoneNumber\": \"+1-202-332-27-33\"\n" +
                "  }";
        var testFile = new MockMultipartFile("test", fileContent.getBytes());

        var result = (CustomerDto)CommonUtils.getDataFromFile(testFile, CustomerDto.class, false);

        Assertions.assertEquals("Jennifer", result.getFirstName());
        Assertions.assertEquals("Aniston", result.getLastName());
        Assertions.assertEquals(ClientStatus.PLATINUM, result.getStatus());
        Assertions.assertEquals(1000, result.getNumberOfPurchases());
        Assertions.assertEquals(100, result.getNumberOfReturns());
        Assertions.assertEquals("+1-202-332-27-33", result.getPhoneNumber());
    }

    @Test
    void getCollectionFromFile() throws IOException {
        var fileContent = "[\n" +
                "  {\n" +
                "    \"firstName\": \"Ivan\",\n" +
                "    \"lastName\": \"Ivanov\",\n" +
                "    \"status\": \"silver\",\n" +
                "    \"numberOfPurchases\": \"100\",\n" +
                "    \"numberOfReturns\": \"9\",\n" +
                "    \"phoneNumber\": \"+7-999-888-77-66\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"firstName\": \"Galina\",\n" +
                "    \"lastName\": \"Petrova\",\n" +
                "    \"status\": \"gold\",\n" +
                "    \"numberOfPurchases\": \"50\",\n" +
                "    \"numberOfReturns\": \"0\",\n" +
                "    \"phoneNumber\": \"+7-111-222-33-44\"\n" +
                "  }\n" +
                "]";
        var testFile = new MockMultipartFile("test", fileContent.getBytes());

        var result = (List<CustomerDto>)CommonUtils.getDataFromFile(testFile, CustomerDto.class, true);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Ivan", result.get(0).getFirstName());
        Assertions.assertEquals("Ivanov", result.get(0).getLastName());
        Assertions.assertEquals(ClientStatus.SILVER, result.get(0).getStatus());
        Assertions.assertEquals(100, result.get(0).getNumberOfPurchases());
        Assertions.assertEquals(9, result.get(0).getNumberOfReturns());
        Assertions.assertEquals("+7-999-888-77-66", result.get(0).getPhoneNumber());
        Assertions.assertEquals("Galina", result.get(1).getFirstName());
        Assertions.assertEquals("Petrova", result.get(1).getLastName());
        Assertions.assertEquals(ClientStatus.GOLD, result.get(1).getStatus());
        Assertions.assertEquals(50, result.get(1).getNumberOfPurchases());
        Assertions.assertEquals(0, result.get(1).getNumberOfReturns());
        Assertions.assertEquals("+7-111-222-33-44", result.get(1).getPhoneNumber());
    }
}
