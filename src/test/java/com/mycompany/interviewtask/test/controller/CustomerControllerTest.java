package com.mycompany.interviewtask.test.controller;

import com.mycompany.interviewtask.advice.DefaultAdvice;
import com.mycompany.interviewtask.configuration.SecurityConfiguration;
import com.mycompany.interviewtask.controller.CustomerController;
import com.mycompany.interviewtask.service.impl.CustomerServiceImpl;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static com.mycompany.interviewtask.enumeration.Error.FILE_HANDLING_ERROR;
import static com.mycompany.interviewtask.enumeration.Error.INTERNAL_SERVER_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import({SecurityConfiguration.class, DefaultAdvice.class, CustomerController.class})
public class CustomerControllerTest {
    private static String path;
    private static MockMultipartFile testFile;
    private static String fileContent;

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    DefaultAdvice defaultAdvice;

    @MockBean
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() throws IOException {
        path = "/customers/create/from-file";
        fileContent = "[\n" +
                "  {\n" +
                "    \"firstName\": \"Ivan\",\n" +
                "    \"lastName\": \"Ivanov\",\n" +
                "    \"status\": \"silver\",\n" +
                "    \"numberOfPurchases\": \"100\",\n" +
                "    \"numberOfReturns\": \"9\",\n" +
                "    \"phoneNumber\": \"+7-999-888-77-66\"\n" +
                "  }\n" +
                "]";
        testFile = new MockMultipartFile("inputFile", fileContent.getBytes());
        Mockito.when(customerService.create(any())).thenReturn("+7-999-888-77-66".getBytes());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createCustomersSuccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart(path)
                        .file(testFile))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("+7-999-888-77-66", mvcResult.getResponse().getContentAsString());
        Mockito.verify(customerService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createCustomersWhenUserDoesNotHaveAccessRightsStatusIsForbiddenTest() throws Exception {
        mockMvc.perform(multipart(path)
                        .file(testFile))
                .andExpect(status().isForbidden());

        Mockito.verify(customerService, never()).create(any());
    }

    @Test
    @WithAnonymousUser
    void createCustomersWhenUserIsUnauthorizedStatusIsUnauthorizedTest() throws Exception {
        mockMvc.perform(multipart(path)
                        .file(testFile))
                .andExpect(status().isUnauthorized());

        Mockito.verify(customerService, never()).create(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createCustomersWhenInputFileJsonDoesNotMatchToModelStatusIsBadRequestTest() throws Exception {
        mockMvc.perform(multipart(path)
                        .file(new MockMultipartFile("inputFile", "json".getBytes())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value(FILE_HANDLING_ERROR.getDescription()));

        Mockito.verify(customerService, never()).create(any());
        Mockito.verify(defaultAdvice, times(1)).handleIOException(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createCustomersWhenServiceThrowsCommonExceptionStatusIsInternalServerErrorTest() throws Exception {
        Mockito.when(customerService.create(any())).thenThrow(HibernateException.class);
        mockMvc.perform(multipart(path)
                        .file(testFile))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR.getDescription()));

        Mockito.verify(customerService, times(1)).create(any());
        Mockito.verify(defaultAdvice, times(1)).handleCommonException(any(), any());
    }
}
