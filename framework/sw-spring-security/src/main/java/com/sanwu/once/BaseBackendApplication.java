package com.sanwu.once;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BaseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseBackendApplication.class, args);
	}

}
