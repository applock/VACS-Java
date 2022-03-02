package com.vajra.vacs.listener;

@Component
public class MqttMessageListener implements Runnable {
    @Autowired
    MqttSubscriberApi subscriber;

    @Override
    public void run() {
        while (true) {
            subscriber.subscribeMessage("your mqtt topic name");
        }

    }
