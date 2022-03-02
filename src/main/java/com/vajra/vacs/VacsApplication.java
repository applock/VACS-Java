package com.vajra.vacs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vajra.vacs")
public class VacsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacsApplication.class, args);
	}

}
