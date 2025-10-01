package com.csvfile.Impl;

import com.csvfile.dto.CustomerDTO;
import com.csvfile.entity.Customer;
import com.csvfile.repositories.CustomerRepository;
import com.csvfile.service.CustomerService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerDTO> uploadCsv(MultipartFile file) {

        // 1. Validate file type
        if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new RuntimeException("Invalid file. Only CSV files are allowed.");
        }

        List<CustomerDTO> customers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] header = reader.readNext(); // read header line

            // 2. Validate header
            String[] expectedHeader = {"customerId", "name", "email"};
            if (header == null || header.length != expectedHeader.length) {
                throw new RuntimeException("Invalid CSV header. Expected: customerId,name,email");
            }
            for (int i = 0; i < expectedHeader.length; i++) {
                if (!header[i].trim().equalsIgnoreCase(expectedHeader[i])) {
                    throw new RuntimeException("Invalid CSV header at column " + (i + 1)
                            + ". Expected: " + expectedHeader[i] + " but found: " + header[i]);
                }
            }

            String[] line;
            int rowCount = 0;

            // 3. Read data rows
            while ((line = reader.readNext()) != null) {
                rowCount++;

                // Max row validation
                if (rowCount > 20) {
                    throw new RuntimeException("CSV file should not contain more than 20 rows.");
                }

                if (line.length < 3) continue; // skip incomplete rows

                String customerId = line[0].trim();
                String name = line[1].trim();
                String email = line[2].trim();

                // 4. Check if record already exists in DB by business key
                boolean exists = repository.existsByCustomerId(customerId);
                if (exists) {
                    throw new RuntimeException("Duplicate record found in DB for customerId: " + customerId);
                }

                // Save entity
                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customer.setName(name);
                customer.setEmail(email);
                Customer saved = repository.save(customer);

                // Map to DTO
                CustomerDTO dto = new CustomerDTO();
                dto.setCustomerId(saved.getCustomerId());
                dto.setName(saved.getName());
                dto.setEmail(saved.getEmail());

                customers.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while reading CSV file: " + e.getMessage(), e);
        }

        return customers;
    }


}
