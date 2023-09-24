package me.tjens23.mqttchallenge.controllers;

import me.tjens23.mqttchallenge.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class MqttController {

    @Autowired
    MqttGateway mqtGateway;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage){

        try {
            JsonObject jsonObject = new Gson().fromJson(mqttMessage, JsonObject.class);
            String data = jsonObject.getAsJsonObject("message").get("data").getAsString();
            String topic = jsonObject.get("topic").getAsString();
            mqtGateway.sendToMqtt(data, topic);

            return ResponseEntity.ok("Message sent: " + data);
        } catch(Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("Failed to send message");
        }
    }
}
