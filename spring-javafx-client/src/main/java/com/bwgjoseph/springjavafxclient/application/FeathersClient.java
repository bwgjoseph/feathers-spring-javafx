package com.bwgjoseph.springjavafxclient.application;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	
	public void findWithParams(String serviceName, Ack callback) throws JSONException {
		if (!this.webSocket.getSocket().connected()) {
			// try to connect, otherwise throw exception
		}
		
		// https://www.baeldung.com/queries-in-spring-data-mongodb
		// https://www.appsdeveloperblog.com/spring-boot-and-mongotemplate-tutorial-with-mongodb/
		// http://www.querydsl.com/
		// https://stackoverflow.com/questions/57103383/convert-mongodb-shell-command-to-java-code
		// https://www.appsdeveloperblog.com/spring-boot-and-mongotemplate-tutorial-with-mongodb/
		// http://jongo.org/#querying
		Query query = new Query();
		query
			.addCriteria(Criteria.where("email").is("venago2680@insertswork.com"))
			.with(Sort.by(Sort.Direction.ASC, "email"));
		
		JSONObject queryObject = new JSONObject(query.getQueryObject().toJson());
		
		System.out.println(query.getQueryObject());
		System.out.println(query.getQueryObject().toJson());
		System.out.println(query.getSortObject().toJson());
		System.out.println(query.toString());
		
//		BasicQuery x = new BasicQuery("{ email: \"venago2680@insertswork.com\" }");
//		System.out.println(x.getQueryObject().toJson());
//		System.out.println(new JSONObject(x.getQueryObject().toJson()));
		
		this.webSocket.getSocket().emit("find", serviceName, queryObject, callback);
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
