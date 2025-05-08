package com.bd.blooddonerfinder.mqttServices;

import java.util.List;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bd.blooddonerfinder.config.MqttConfig;
import com.bd.blooddonerfinder.payload.request.SysNotificationsModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttClientService {

	@Autowired
	private MqttConfig mqttConfig;

	@Value("${mqtt_server_url}")
	private String mqttServerUrl;

	@Value("${mqtt.topic}")
	private String topic;

	int qos = 1;

	public void connectAndPublish(String payload) throws MqttException {
		try {
			MqttClient client = new MqttClient(mqttServerUrl, MqttClient.generateClientId());
			client.connect(mqttConfig.getMqttConnectionOptions());

			MqttMessage message = new MqttMessage(payload.getBytes());
			message.setQos(1);
			message.setRetained(true);

			client.publish(topic, message);
			System.out.println("Message published to topic: " + topic);

			client.disconnect();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectAndSubscribe(String topic) throws MqttException {
		try {
			MqttClient client = new MqttClient(mqttServerUrl, MqttClient.generateClientId(), new MemoryPersistence());

			client.setCallback(new MqttCallback() {

				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost: " + cause.getMessage());
				}

				public void messageArrived(String topic, MqttMessage message) {
					System.out.println("topic: " + topic);
					System.out.println("Qos: " + message.getQos());
					System.out.println("message content: " + new String(message.getPayload()));

				}

				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete---------" + token.isComplete());
				}

			});
			client.connect(mqttConfig.getMqttConnectionOptions());
			client.subscribe(topic, qos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendNotificationToUser(List<SysNotificationsModel> notificationModels) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			MqttClient client = new MqttClient(mqttServerUrl, MqttClient.generateClientId());
			client.connect(mqttConfig.getMqttConnectionOptions());

			// Send notification to the subscribed clients

			for (SysNotificationsModel model : notificationModels) {
				// Convert the Java Object to JSON
				String jsonPayload = mapper.writeValueAsString(model);
				MqttMessage message = new MqttMessage(jsonPayload.getBytes());
				message.setQos(2);

				client.publish(model.getToUser(), message);
				System.out.println("Notification sent to User's topic!");
			}

			client.disconnect();
			client.close();

		} catch (Exception e) {
			System.out.println("Could not send notification to User's topic!");
			System.out.println(e.getMessage());
		}

	}

}
