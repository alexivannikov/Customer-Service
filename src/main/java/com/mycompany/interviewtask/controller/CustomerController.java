package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Контролер для работы с данными покупателей
 */
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(path = "/phone-numbers/download")
    public ResponseEntity<byte[]> getPhoneNumbers() throws IOException {
        return ResponseEntity.ok()
                .headers(CommonUtils.buildHttpHeaders("phone_numbers", "txt"))
                .body(customerService.getPhoneNumbers());
    }

    @PostMapping(path = "/save")
    public ResponseEntity<List<UUID>> save(@RequestParam("inputFile") MultipartFile inputFile) throws IOException {
        return ResponseEntity.ok().body(
                customerService.save(CommonUtils.getDataFromFile(inputFile, CustomerDto.class, true))
        );
    }
}
