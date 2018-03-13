package br.com.eneeyes.service;

import org.eclipse.paho.client.mqttv3.MqttException;

import br.com.eneeyes.mqtt.Publisher;
import br.com.eneeyes.mqtt.Subscriber;

/**
 * Basic launcher for Publisher and Subscriber
 */
public class MqttApp {

  public static void main(String[] args) throws MqttException {

    if (args.length < 1) {
      throw new IllegalArgumentException("Voc� deve especificar: Subscriber, IP do Broker e IP de servi�o");
    }
    
    switch (args[0]) {
      case "publisher":
        Publisher.main(args);
        break;
      case "subscriber":
        Subscriber.main(args);
        break;
      default:
        throw new IllegalArgumentException("S�o sei o que fazer para o par�metro " + args[0]);
    }
  }
}

