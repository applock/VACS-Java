package com.vajra.vacs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vajra.vacs.pojo.VacsInput;
import com.vajra.vacs.service.MessagingService;

@RequestMapping("/v1")
@RestController
public class VacsController {

	@Autowired
	private MessagingService messagingService;

	@Autowired
	private ConfigurableApplicationContext context;
	
	@PostMapping("/push")
	@ResponseStatus(value = HttpStatus.OK)
	ResponseEntity<Void> pushMessage(@RequestBody VacsInput input){
		return null;
		
	}
}
