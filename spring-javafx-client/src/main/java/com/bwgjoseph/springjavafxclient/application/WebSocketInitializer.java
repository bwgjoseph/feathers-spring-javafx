package com.bwgjoseph.springjavafxclient.application;

import java.net.URISyntaxException;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.configuration.FeathersConfig;
import com.bwgjoseph.springjavafxclient.event.publisher.WebSocketEventPublisher;

import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketInitializer implements SmartInitializingSingleton, ApplicationListener<ApplicationReadyEvent> {
	private Socket socket;
	private final FeathersConfig feathersConfig;
	private final WebSocketEventPublisher webSocketEventPublisher;
	
	public WebSocketInitializer(FeathersConfig feathersConfig, WebSocketEventPublisher webSocketEventPublisher) {
		Assert.notNull(feathersConfig, "feathersConfig cannot be null");
		Assert.notNull(webSocketEventPublisher, "webSocketEventPublisher cannot be null");
		
		this.feathersConfig = feathersConfig;
		this.webSocketEventPublisher = webSocketEventPublisher;
	}
	
	@Override
	public void afterSingletonsInstantiated() {
		log.info("afterSingletonsInstantiated");
		IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        
	    try {
			socket = IO.socket(this.feathersConfig.getWebSocketUri(), opts);
			
			socket.on(Socket.EVENT_CONNECT, objects -> {
	            // when socket is connected
				log.info("Socket of user {} connected", socket.id());
				webSocketEventPublisher.publishWebSocketEvent(true);
	        })
			.on(Socket.EVENT_CONNECTING, objects -> log.info("Trying to connect to {}", this.feathersConfig.getWebSocketUri()))
			// reconnection is automatically done by the socket API, just print that disconnected
			.on(Socket.EVENT_DISCONNECT, objects -> log.info("Socket of user[" + socket.id() + "] disconnected"))
			.on(Socket.EVENT_RECONNECT_ATTEMPT, objects -> log.info("Attempt to reconnect socket of user {}", socket.id()))
			.on(Socket.EVENT_RECONNECT_ERROR, objects -> log.info("Reconnection error for socket of user {} because {}", socket.id(), objects.length > 0 ? objects[0] : 1))
			.on(Socket.EVENT_RECONNECT_FAILED, objects -> log.info("Reconnection failed for socket of user {} because {}", socket.id(), objects.length > 0 ? objects[0] : 1))
			.on(Socket.EVENT_RECONNECTING, objects -> log.info("Reconnection event for user {}", socket.id()))
			.on(Socket.EVENT_RECONNECT, objects -> log.info("Socket of user {} reconnected!", socket.id()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Connecting to socket after ApplicationReadyEvent");
		this.socket.connect();
	}
	
	public Socket getSocket() {
		return this.socket;
	}
}
