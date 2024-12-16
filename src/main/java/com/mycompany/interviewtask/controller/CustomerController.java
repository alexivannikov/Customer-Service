package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.model.dto.CustomerDto;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.utils.CommonUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Контролер для работы с данными покупателей
 */
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Создание покупателей на основе данных из файла
     *
     * @param inputFile файл для считывания
     * @return файл с номерами телефонов покупателей
     */
    @PostMapping(path = "/create/from-file")
    public ResponseEntity<byte[]> create(@RequestParam("inputFile") MultipartFile inputFile) throws IOException {
        return ResponseEntity.ok()
                .headers(CommonUtils.buildHttpHeaders("phone_numbers", "txt"))
                .body(customerService.create(CommonUtils.getDataFromFile(inputFile, CustomerDto.class, true)));
    }
}
