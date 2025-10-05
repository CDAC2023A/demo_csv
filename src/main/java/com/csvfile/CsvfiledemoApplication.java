package com.csvfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.csvfile.entity")
public class CsvfiledemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvfiledemoApplication.class, args);
	}

}
