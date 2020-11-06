package com.bwgjoseph.springjavafxclient.application;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.socket.client.Ack;
import io.socket.emitter.Emitter.Listener;

@Component
public class FeathersClient {
	
	private final WebSocketInitializer webSocket;
	
	public FeathersClient(WebSocketInitializer webSocket) {
		Assert.notNull(webSocket, "webSocket cannot be null");
		
		this.webSocket = webSocket;
	}
	
	public void find(String serviceName, Ack callback) {
		if (!this.webSocket.getSocket().connected()) {
			// try to connect, otherwise throw exception
		}
		
		this.webSocket.getSocket().emit("find", serviceName, callback);
	}
	
	public void create(String serviceName, JSONObject obj, Ack callback) {
		if (!this.webSocket.getSocket().connected()) {
			// try to connect, otherwise throw exception
		}
		
		this.webSocket.getSocket().emit("create", serviceName, obj, callback);
	}
	
	public void remove(String serviceName, String id, Ack callback) {
		if (!this.webSocket.getSocket().connected()) {
			// try to connect, otherwise throw exception
		}
		
		this.webSocket.getSocket().emit("remove", serviceName, id, callback);
	}
	
	
	public void listen(String serviceName, String method, Listener callback) {
		if (!this.webSocket.getSocket().connected()) {
			// try to connect, otherwise throw exception
		}
		
		this.webSocket.getSocket().on(serviceName + " " + method, callback);
	}
}
