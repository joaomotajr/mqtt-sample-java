package com.mapr.demo.mqtt.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SimpleMqttCallBack implements MqttCallback {

	public String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SimpleMqttCallBack(String url) {
		
		this.url = url;
	}
	
	public void connectionLost(Throwable throwable) {
		System.out.println("Connection to MQTT broker lost!");
	}
	
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
	    
		System.out.println("Mensagem Recebida:\t - Id: " + s + "| Value: " + new String(mqttMessage.getPayload()) );
	    
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
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	
			String output;
			System.out.println("Retorno: ");
			while ((output = br.readLine()) != null) {
								
				if(output.toString().equalsIgnoreCase("true")) 
					System.out.println("Mensagem Enviada ao E-Gas com SUCESSO");
				else if (output.toString().equalsIgnoreCase("false")) 
					System.out.println("Mensagem Enviada ao E-Gas - Dispositivo Não Localizado");			
				else 
					System.out.println(output);					
				
			}
	
			conn.disconnect();
	
		  } catch (MalformedURLException e) {
	
			  System.out.println("URL inválida ou parâmetros vazios ou nulos .... \n");
	
		  } catch (IOException e) {
	
			System.out.println("Mensagem Não enviada ao Servido E-Gas .... \n");
	
		 }
	}
  
}
