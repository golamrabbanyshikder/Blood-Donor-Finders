package com.bd.blooddonerfinder.MqttController;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.*;

import com.bd.blooddonerfinder.mqttServices.MqttClientService;
import com.bd.blooddonerfinder.payload.request.SysNotificationsModel;



@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttClientService mqttClientService;

    public MqttController(MqttClientService mqttClientService) {
        this.mqttClientService = mqttClientService;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody List<SysNotificationsModel> notificationModels) throws MqttException {
    	
    		mqttClientService.sendNotificationToUser(notificationModels);
            return "Message sent!";
        
    }
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("topic")String topic) throws MqttException {
    	
    		mqttClientService.connectAndSubscribe(topic);
            return "Subscribed Done!";
        
    }
}

