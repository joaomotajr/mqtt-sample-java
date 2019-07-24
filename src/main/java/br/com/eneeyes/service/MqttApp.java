package br.com.eneeyes.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

import br.com.eneeyes.mqtt.Subscriber;


/**
 * Basic launcher for Publisher and Subscriber
 */
public class MqttApp {
	
	public static final Logger logger = LogManager.getLogger(MqttApp.class);

	public static void main(String[] args) throws MqttException {
		
		BasicConfigurator.configure();

	    if (args.length < 1) {
	    	throw new IllegalArgumentException("Você deve especificar: Subscriber, IP do Broker e IP de serviço");
	    }
	    
	    switch (args[0]) {
	    	case "publisher":
	    		logger.info("Publisher Não Presente." );
	    		break;
	    	case "subscriber":
	    		Subscriber.main(args);
	    		break;
	    	default:
	    		throw new IllegalArgumentException("São sei o que fazer para o parâmetro " + args[0]);
	    }
	}
}

