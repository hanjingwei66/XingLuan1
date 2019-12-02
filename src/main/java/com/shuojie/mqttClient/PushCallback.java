package com.shuojie.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class PushCallback implements MqttCallback {
	private String threadId;
	public PushCallback(String threadId){
		this.threadId = threadId;
	}
	
    public void connectionLost(Throwable cause) {
    	
    }
 
    public void deliveryComplete(IMqttDeliveryToken token) {
//       System.out.println("deliveryComplete---------" + token.isComplete());
    }
 
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	String msg = new String(message.getPayload());
    	System.out.println(threadId + " " + msg);
    }
}