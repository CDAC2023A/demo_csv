package com.csvfile.service;

import com.csvfile.dto.CustomerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> uploadCsv(MultipartFile file);

}
