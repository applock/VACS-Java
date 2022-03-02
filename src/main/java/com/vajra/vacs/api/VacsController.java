package com.vajra.vacs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.vajra.vacs.service.MessagingService;


@RestController
public class VacsController {

	@Autowired
	private MessagingService messagingService;

	@Autowired
	private ConfigurableApplicationContext context;
	
}
