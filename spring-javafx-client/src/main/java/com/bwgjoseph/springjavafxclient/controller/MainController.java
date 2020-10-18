package com.bwgjoseph.springjavafxclient.controller;

import org.json.JSONException;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.entity.User;
import com.bwgjoseph.springjavafxclient.event.GenericCreatedEvent;
import com.bwgjoseph.springjavafxclient.event.UserCreatedEvent;
import com.bwgjoseph.springjavafxclient.event.UserRemovedEvent;
import com.bwgjoseph.springjavafxclient.event.UserPatchedEvent;
import com.bwgjoseph.springjavafxclient.service.AuthenticationService;
import com.bwgjoseph.springjavafxclient.service.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;

@Slf4j
@FxmlView
@Component
public class MainController {
	
	private final Service service;
	private final AuthenticationService authenticationService;
	
	// Using Constructor-Injection is recommended as it allow us to assert that the dependencies are not null
	// We can use Setter-Injection if the dependencies is optional
	
	// Notice that we also inject using Service interface rather than concrete class to allow us to easily swap implementation
	// If you have multiple implementation class of Service interface, then you will need @Qualifier/@Primary to determine which
	// dependency should get injected
	public MainController(Service service, AuthenticationService authenticationService) {
		Assert.notNull(service, "service cannot be null");
		Assert.notNull(authenticationService, "authenticationService cannot be null");
		
		this.service = service;
		this.authenticationService = authenticationService;
	}

    @FXML
    public Label titleLabel;
    
    @FXML
    public TextArea textArea;
    
    @FXML
    public TextField userIdToRemove;

    @FXML
    public void initialize() {
    	titleLabel.setText(titleLabel.getText() + " initialized");
        textArea.setText(textArea.getText() + " test");
    }
    
    @FXML
    public void register() throws JSONException {
    	this.service.create();
    }
    
    @FXML
    public void login() throws JSONException {
    	this.authenticationService.authenticateLocal();
    }
    
    // We can listen to different event such as
    // GenericCreatedEvent<User>, GenericCreatedEvent<Post>
    @EventListener
    public void onGenericCreatedEvent(GenericCreatedEvent<User> event) {
    	log.info("onGenericUserCreatedEvent" + event.getEntity().toString());
    }
    
    @EventListener(UserCreatedEvent.class)
    public void onUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
    	log.info("onUserCreatedEvent: " + userCreatedEvent.getUser().toString());
    	textArea.setText(textArea.getText() + "\n" + userCreatedEvent.getUser().toString());
    }
    
    // Using Spring SpEL to filter event based on specific condition
    // especially useful if its only interested and react to a particular subset of data
    @EventListener(condition = "#userPatchedEvent.email eq 'josephgan@live.com.sg'")
    public void onSpecificUserPatchedEvent(UserPatchedEvent userPatchedEvent) {
    	log.info("onSpecificUserPatchedEvent: " + userPatchedEvent.getUser().toString());
//    	textArea.setText(textArea.getText() + "\n" + userPatchedEvent.getUser().toString());
    }
    
    @EventListener(UserPatchedEvent.class)
    public void onUserPatchedEvent(UserPatchedEvent userPatchedEvent) {
    	log.info("onUserPatchedEvent: " + userPatchedEvent.getUser().toString());
//    	textArea.setText(textArea.getText() + "\n" + userPatchedEvent.getUser().toString());
    }
    
    @EventListener(UserRemovedEvent.class)
    public void onUserRemovedEvent(UserRemovedEvent userRemovedEvent) {
    	log.info("onUserRemovedEvent: " + userRemovedEvent.getUser().toString());
//    	textArea.setText(textArea.getText() + "\n" + userRemovedEvent.getUser().toString());
    }
    
    @FXML
    public void getUsers() {
    	this.service.find().forEach(data -> log.info(data.toString()));
    	log.info("Auth is" + SecurityContextHolder.getContext());
    }
    
    @FXML
    public void removeUser() {
    	this.service.remove(userIdToRemove.getText());
    }
}