package br.com.eneeyes.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallBack implements MqttCallback {
		
	final static Logger logger = Logger.getLogger(MqttCallBack.class);

	public String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MqttCallBack(String url) {
		
		this.url = url;
	}
	
	public void connectionLost(Throwable throwable) {
		
		logger.info("Connection to MQTT broker Perdida!");
	}
	
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

		logger.info("Mensagem Recebida:\t - Id: " + s + "| Value: " + new String(mqttMessage.getPayload()));
	    
	    sendToRest(s, mqttMessage.toString());
	}
	
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		
		logger.info("Message Delivered :\t - Id: " + iMqttDeliveryToken.getMessageId() + "|  " + iMqttDeliveryToken.getResponse());
		
	}
	  
	private void sendToRest(String id, String value) {
		  
		try {					
				
			URL url = new URL("http://" + getUrl() + "/api/historic/SaveByPositionUid2/" + id + "/" + value);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Falha : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String output;
			logger.info("Retorno: ");
			while ((output = br.readLine()) != null) {
								
				if(output.toString().equalsIgnoreCase("true")) 
					logger.info("Mensagem Enviada ao E-Gas com SUCESSO");
				else if (output.toString().equalsIgnoreCase("false")) 
					logger.info("Mensagem Enviada ao E-Gas - Dispositivo Não Localizado");			
				else 
					logger.info(output);				
			}
	
			conn.disconnect();
	
		  } catch (MalformedURLException e) {	
			  logger.warn("URL inválida ou parâmetros vazios ou nulos .... \n");	
		  } catch (IOException e) {	
			  logger.warn("Mensagem Não enviada ao Servidor E-Gas. [" + e.getMessage() + "] /n" );	
		  } catch (Exception e) {				
			  logger.warn("Mensagem Não Conhecida. [" + e.getMessage() + "] /n" );	
		 }
	}  
}
