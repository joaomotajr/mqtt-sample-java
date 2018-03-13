package br.com.eneeyes.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

  public static void main(String[] args) throws MqttException {

	String broker = "tcp://" + args[1] + ":1883";
	
    System.out.println("== START SUBSCRIBER == Server:: " + broker );

    MqttClient client=new MqttClient(broker, MqttClient.generateClientId());
    client.setCallback( new MqttCallBack(args[2]) );
    client.connect();
        
    client.subscribe("#");
  }

}
