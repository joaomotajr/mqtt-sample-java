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
      throw new IllegalArgumentException("Você deve especificar: Subscriber, IP do Broker e IP de serviço");
    }
    
    switch (args[0]) {
      case "publisher":
        Publisher.main(args);
        break;
      case "subscriber":
        Subscriber.main(args);
        break;
      default:
        throw new IllegalArgumentException("São sei o que fazer para o parâmetro " + args[0]);
    }
  }
}

