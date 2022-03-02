package com.vajra.vacs.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netsdk.demo.module.LoginModule;

@RestController
public class AnprController {

	private Logger logger = LoggerFactory.getLogger(AnprController.class);

	@GetMapping("/probe")
	ResponseEntity<HttpStatus> executeTask() {
		logger.debug("Probing started..");
		System.out.println("Probing started..");

		LoginModule lm = new LoginModule();
		boolean loginStatus = lm.login("192.168.1.108", 37777, "admin", "V@jra01@");
		System.out.println("Login - " + loginStatus);
		logger.debug("Login - " + loginStatus);

		logger.debug("Probing done..");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
