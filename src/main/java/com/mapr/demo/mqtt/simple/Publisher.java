package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher {


  public static void main(String[] args) throws MqttException {

    String messageString = "Hello World from Java!";

    if (args.length == 2 ) {
      messageString = args[1];
    }

    String broker = "tcp://iot.eclipse.org:1883";
    String clientId     = "JavaSample";
    //MemoryPersistence persistence = new MemoryPersistence();

    System.out.println("== START PUBLISHER == Server:: " + broker);

    MqttClient client = new MqttClient(broker, MqttClient.generateClientId());
    //MqttClient client = new MqttClient(broker, clientId, persistence);
    
    client.connect();
    
    MqttMessage message = new MqttMessage();
    message.setPayload(messageString.getBytes());
    client.publish("iot_data_jr", message);

    System.out.println("\tMessage '"+ messageString +"' to 'iot_data'");

    client.disconnect();

    System.out.println("== END PUBLISHER ==");

  }


}
