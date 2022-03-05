package com.vajra.vacs.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.springframework.stereotype.Component;

@Component
public class MqttUtils {

	public static void connect(final IMqttAsyncClient client) throws Throwable {
		connect(client, new MqttConnectOptions());
	}

	public static void connect(final IMqttAsyncClient client, final MqttConnectOptions options) throws Throwable {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> error = new AtomicReference<>();
		final AtomicReference<Boolean> success = new AtomicReference<>(Boolean.TRUE);
		client.connect(options, null, new IMqttActionListener() {

			@Override
			public void onSuccess(final IMqttToken asyncActionToken) {
				latch.countDown();
			}

			@Override
			public void onFailure(final IMqttToken asyncActionToken, final Throwable exception) {
				error.set(exception);
				success.set(Boolean.FALSE);
				latch.countDown();
			}
		});
		latch.await();
		if (!success.get()) {
			throw error.get();
		}
	}

	public static void disconnect(final IMqttAsyncClient client)
			throws MqttException, MqttSecurityException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		client.disconnect(null, new IMqttActionListener() {

			@Override
			public void onSuccess(final IMqttToken asyncActionToken) {
				latch.countDown();
			}

			@Override
			public void onFailure(final IMqttToken asyncActionToken, final Throwable exception) {
				latch.countDown();
			}
		});
		latch.await();
	}

	public static void publish(final IMqttAsyncClient client, final String topic, final byte[] expectedPayload)
			throws MqttException, MqttPersistenceException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		client.publish(topic, new MqttMessage(expectedPayload), null, new IMqttActionListener() {

			@Override
			public void onSuccess(final IMqttToken asyncActionToken) {
				latch.countDown();
			}

			@Override
			public void onFailure(final IMqttToken asyncActionToken, final Throwable exception) {
				latch.countDown();
			}
		});
		latch.await();
	}

	public static void subscribe(final IMqttAsyncClient client, final String topic,
			final IMqttMessageListener messageListener) throws MqttException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final IMqttActionListener callback = new IMqttActionListener() {

			@Override
			public void onSuccess(final IMqttToken asyncActionToken) {
				latch.countDown();
			}

			@Override
			public void onFailure(final IMqttToken asyncActionToken, final Throwable exception) {
				latch.countDown();
			}
		};

		client.subscribe(topic, 0, null, callback, messageListener);
		latch.await();
	}

}