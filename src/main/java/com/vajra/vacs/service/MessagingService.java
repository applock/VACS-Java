package com.vajra.vacs.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
	private Logger logger = LoggerFactory.getLogger(MessagingService.class);

	@Autowired
	private IMqttClient mqttClient;

	public void publish(final String topic, final String payload, int qos, boolean retained)
			throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);

		mqttClient.publish(topic, mqttMessage);

		// mqttClient.publish(topic, payload.getBytes(), qos, retained);

		mqttClient.disconnect();
	}

	public void subscribe(final String topic) throws MqttException, InterruptedException {
		logger.debug("Messages from Topic :" + topic);

		mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
			String messagePayload = new String(msg.getPayload());
			logger.debug("Messages - {}", msg.getId() + " -> " + messagePayload);
		});
	}

}
