package com.vajra.vacs.api;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vajra.vacs.pojo.VacsInput;
import com.vajra.vacs.repository.VehicleRepository;
import com.vajra.vacs.service.MessagingService;
import com.vajra.vacs.utils.MqttUtils;

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

	@Autowired
	private MqttUtils utils;

	@Value("${mqtt.publishTopic}")
	String topicToPublish;

	@Value("${mqtt.subscribeTopic}")
	String topicToSubcribe;

	@PostMapping("/push")
	// @ResponseStatus(value = HttpStatus.OK)
	ResponseEntity<HttpStatus> pushMessage(@RequestBody VacsInput input) throws MqttException, InterruptedException {
		logger.debug("pushMessage :: Received request to publish: {}", input);

		// messagingService.subscribe(topicToPublish);
		messagingService.publish(topicToPublish, input.getMsg(), 0, true);
		logger.debug("pushMessage :: message published to {}", topicToPublish);

		context.close();

		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/pull/{topic}")
	ResponseEntity<HttpStatus> pullMessageByTopic(@PathVariable("topic") String topic)
			throws MqttException, InterruptedException {
		logger.debug("pullMessageByTopic :: From topic: {}", topic);

		String subscribedTopic = topicToPublish;
		if (!StringUtils.isEmpty(topic))
			subscribedTopic = topic;

		messagingService.subscribe(subscribedTopic);
		context.close();

		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/pull")
	ResponseEntity<HttpStatus> pullMessage() throws Throwable {
		logger.debug("pullMessage :: From topic: {}", topicToPublish);

		// messagingService.subscribe(topicToPublish);
		// context.close();

		final MqttAsyncClient subClient = new MqttAsyncClient("tcp://localhost:1883", "new-sub");
		utils.connect(subClient);
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<MqttMessage> msg = new AtomicReference<MqttMessage>();

		// Subscribe
		final IMqttMessageListener messageListener = new IMqttMessageListener() {

			@Override
			public void messageArrived(final String topic, final MqttMessage message) throws Exception {
				msg.set(message);
				latch.countDown();
			}
		};
		utils.subscribe(subClient, topicToPublish, messageListener);

		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

}
