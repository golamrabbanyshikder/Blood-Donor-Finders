package com.bd.blooddonerfinder.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@Configuration
public class MqttConfig {

	@Value("${mqtt_server_url}")
	private String mqttServerUrl;

	@Value("${mqtt_username}")
	private String mqttBrokerUsername;

	@Value("${mqtt_password}")
	private String mqttBrokerPassword;

	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();

		clientFactory.setConnectionOptions(getMqttConnectionOptions());

		return clientFactory;
	}

	@Bean
	public MqttConnectOptions getMqttConnectionOptions() {
		MqttConnectOptions connectionOptions = new MqttConnectOptions();
		connectionOptions.setServerURIs(new String[] { mqttServerUrl });
		connectionOptions.setUserName(mqttBrokerUsername);
		connectionOptions.setPassword(mqttBrokerPassword.toCharArray());

		connectionOptions.setCleanSession(true);
		connectionOptions.setAutomaticReconnect(true);
		connectionOptions.setKeepAliveInterval(30); // Add this!
		connectionOptions.setConnectionTimeout(10); // Optional: timeout for initial connection
		connectionOptions.setMaxReconnectDelay(5000); // Max delay between reconnects

		return connectionOptions;
	}

}
