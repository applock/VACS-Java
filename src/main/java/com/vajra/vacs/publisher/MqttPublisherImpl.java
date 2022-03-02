package com.vajra.vacs.publisher;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vajra.vacs.config.MqttConfig;

@Component
public class MqttPublisherImpl extends MqttConfig implements MqttCallback {

	private static final Logger logger = LoggerFactory.getLogger(MqttPublisherImpl.class);

	private MqttPublisherImpl() {
		this.config();
	}

	private MqttPublisherImpl(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		this.config(broker, port, ssl, withUserNamePass);
	}

	public static MqttPublisherImpl getInstance() {
		return new MqttPublisherImpl();
	}

	public static MqttPublisherImpl getInstance(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		return new MqttPublisherImpl(broker, port, ssl, withUserNamePass);
	}

	@Override
	public void publishMessage(String topic, String message) {

		try {
			MqttMessage mqttmessage = new MqttMessage(message.getBytes());
			mqttmessage.setQos(this.qos);
			mqttmessage.setRetained(false);
			this.mqttClient.publish(topic, mqttmessage);
		} catch (MqttException me) {
			logger.error("ERROR", me);
		}
		return null;
	}

	@Override
	public void disconnect() {
		try {
			this.mqttClient.disconnect();
		} catch (MqttException me) {
			logger.error("ERROR", me);
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
		logger.info("Connection Lost");

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		logger.info("delivery completed");

	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// Leave it blank for Publisher

	}

	@Override
	protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		// Like we did in MqttSubscribe
	}

	@Override
	protected void config() {
		// Like we did in MqttSubscribe
	}

}
