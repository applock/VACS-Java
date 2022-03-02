package com.vajra.vacs.api;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	private Logger logger = LoggerFactory.getLogger(VacsController.class);

	@Autowired
	private MessagingService messagingService;

	@Autowired
	private ConfigurableApplicationContext context;

	@Autowired
	MqttConnectOptions options;

	@Value("${mqtt.publishTopic}")
	String topicToPublish;

	@PostMapping("/push")
	@ResponseStatus(value = HttpStatus.OK)
	ResponseEntity<Void> pushMessage(@RequestBody VacsInput input) throws MqttException, InterruptedException {
		logger.debug("pushMessage :: Received request to publish: {}", input);

		//messagingService.subscribe(topicToPublish);
		messagingService.publish(topicToPublish, input.getMsg(), 0, true);
		logger.debug("pushMessage :: message published to {}", topicToPublish);

		context.close();

		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
