package com.vajra.vacs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSubscriber {

	private Logger logger = LoggerFactory.getLogger(RabbitMqSubscriber.class);

	@Bean
	public Queue messageToVacsQueue() {
		return new Queue("messageToVacs");
	}

	@Autowired
	private MessageProcessingService msgProccessor;
/*
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Scheduled(fixedDelay = 5000)
	public void pushMessage() {
		String messageString = "Hello Rabbit @" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
		rabbitTemplate.convertAndSend("messageToVacs", messageString);
	}
*/
	@RabbitListener(queues = "messageToVacs")
	public void rabbitMqListener(String message) {
		logger.debug("rabbitMqListener :: message from queue : {}", message);
		msgProccessor.processMqttMessage(message);
		logger.debug("rabbitMqListener :: processing initiated for : {}", message);
	}

}
