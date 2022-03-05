package com.vajra.vacs.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.vajra.vacs.service.MessageProcessingService;

import java.util.Arrays;
import java.util.List;

/**
 * mqtt subscribe to topic
 */
@Configuration
@IntegrationComponentScan
public class MqttSubscribeConfig {

	private Logger logger = LoggerFactory.getLogger(MqttSubscribeConfig.class);

	@Value("${mqtt.clientId}")
	String clientId;

	@Value("${mqtt.hostname}")
	String hostname;

	@Value("${mqtt.username}")
	String username;

	@Value("${mqtt.password}")
	String password;

	@Value("${mqtt.subscribeTopic}")
	String subscribeTopic;

	@Value("${mqtt.automaticReconnect}")
	Boolean automaticReconnect;

	@Value("${mqtt.port}")
	int port;

	@Autowired
	MessageProcessingService mps;

	@Bean
	@ConfigurationProperties(prefix = "mqtt")
	public MqttConnectOptions getReceiverMqttConnectOptions() {
		logger.info("getReceiverMqttConnectOptions : building mqtt connection options..");

		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setUserName(username);
		mqttConnectOptions.setPassword(password.toCharArray());
		List<String> hostList = Arrays.asList("tcp://" + hostname + ":" + port);
		String[] serverURIs = new String[hostList.size()];
		hostList.toArray(serverURIs);
		mqttConnectOptions.setServerURIs(serverURIs);
		mqttConnectOptions.setKeepAliveInterval(2);
		mqttConnectOptions.setAutomaticReconnect(automaticReconnect);

		logger.info("getReceiverMqttConnectOptions : mqtt connection options - {}", mqttConnectOptions);
		return mqttConnectOptions;
	}

	@Bean
	public MqttPahoClientFactory receiverMqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(getReceiverMqttConnectOptions());
		return factory;
	}

	@Bean
	public MessageChannel messageToVacs() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		List<String> topicList = Arrays.asList(subscribeTopic);
		String[] topics = new String[topicList.size()];
		topicList.toArray(topics);

		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,
				receiverMqttClientFactory(), topics);
		// adapter.setCompletionTimeout(completionTimeout);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(messageToVacs());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "messageToVacs")
	public MessageHandler handler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				logger.info("handler -> handleMessage : message received - {}", message);
				String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
				String msg = message.getPayload().toString();

				mps.processMqttMessage(msg);
				logger.info("handler -> handleMessage : async processing for msg [{}] initiated from topic - {}", msg,
						topic);
			}
		};
	}
}