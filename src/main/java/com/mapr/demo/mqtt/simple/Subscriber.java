package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

  public static void main(String[] args) throws MqttException {

	String broker = "tcp://iot.eclipse.org:1883";
	
    System.out.println("== START SUBSCRIBER == Server:: " + broker );

    MqttClient client=new MqttClient(broker, MqttClient.generateClientId());
    client.setCallback( new SimpleMqttCallBack() );
    client.connect();

    client.subscribe("iot_data_jr");

  }

}
