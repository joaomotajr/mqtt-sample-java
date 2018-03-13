package br.com.eneeyes.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallBack implements MqttCallback {
	
	private Log log = LogFactory.getLog(getClass());

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
		
		log.info("Connection to MQTT broker Perdida!");
	}
	
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

		log.info("Mensagem Recebida:\t - Id: " + s + "| Value: " + new String(mqttMessage.getPayload()));
	    
	    sendToRest(s, mqttMessage.toString());
	}
	
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
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
			log.info("Retorno: ");
			while ((output = br.readLine()) != null) {
								
				if(output.toString().equalsIgnoreCase("true")) 
					log.info("Mensagem Enviada ao E-Gas com SUCESSO");
				else if (output.toString().equalsIgnoreCase("false")) 
					log.info("Mensagem Enviada ao E-Gas - Dispositivo Não Localizado");			
				else 
					log.info(output);					
				
			}
	
			conn.disconnect();
	
		  } catch (MalformedURLException e) {
	
			  log.warn("URL inválida ou parâmetros vazios ou nulos .... \n");
	
		  } catch (IOException e) {
	
			  log.warn("Mensagem Não enviada ao Servidor E-Gas. [" + e.getMessage() + "] /n" );
	
		 }
	}
  
}
