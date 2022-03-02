package com.vajra.vacs.config;

import java.sql.Timestamp;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriberImpl extends MqttConfig implements MqttCallback {

	private static final String fota_fetch_record = "fota_fetch_record";
	private String brokerUrl = null;
	final private String colon = ":";
	final private String clientId = UUID.randomUUID().toString();

	private MqttClient mqttClient = null;
	private MqttConnectOptions connectionOptions = null;
	private MemoryPersistence persistence = null;

	private static final Logger logger = LoggerFactory.getLogger(MqttSubscriberImpl.class);

	public MqttSubscriberImpl() {
		logger.info("I am MqttSub impl");
		this.config();
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Connection Lost" + cause);
		this.config();
	}

	@Override
	protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		logger.info("Inside Parameter Config");
		String protocal = this.TCP;

		this.brokerUrl = protocal + this.broker + colon + port;
		this.persistence = new MemoryPersistence();
		this.connectionOptions = new MqttConnectOptions();

		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
			this.connectionOptions.setCleanSession(true);
			this.connectionOptions.setPassword(this.password.toCharArray());
			this.connectionOptions.setUserName(this.userName);
			this.mqttClient.connect(this.connectionOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			logger.error("Not Connected");
			//throw new com.bms.exceptions.MqttException("Not Connected");
		}
	}

	@Override
	protected void config() {
		logger.info("Inside Config with parameter");
		this.brokerUrl = this.TCP + this.broker + colon + this.port;
		this.persistence = new MemoryPersistence();
		this.connectionOptions = new MqttConnectOptions();
		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
			this.connectionOptions.setCleanSession(true);
			this.connectionOptions.setPassword(this.password.toCharArray());
			this.connectionOptions.setUserName(this.userName);
			this.mqttClient.connect(this.connectionOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			logger.error("Not Connected");
			//throw new com.bms.exceptions.MqttException("Not Connected");
		}
	}

	//@Override
	public void subscribeMessage(String topic) {
		try {

			this.mqttClient.subscribe(topic, this.qos);
		} catch (MqttException me) {
			System.out.println("Not able to Read Topic  " + topic);
			// me.printStackTrace();
		}
	}

	//@Override
	public void disconnect() {
		try {
			this.mqttClient.disconnect();
		} catch (MqttException me) {
			logger.error("ERROR", me);
		}
	}

	@Override
	public void messageArrived(String mqttTopic, MqttMessage mqttMessage) throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("***********************************************************************");
		System.out.println("Message Arrived at Time: " + time + "  Topic: " + mqttTopic + "  Message: "
				+ new String(mqttMessage.getPayload()));
//    System.out.println("***********************************************************************");

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
}
