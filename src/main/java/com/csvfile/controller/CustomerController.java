package com.csvfile.controller;

import com.csvfile.dto.CustomerDTO;
import com.csvfile.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<CustomerDTO>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadCsv(file));
    }
}
