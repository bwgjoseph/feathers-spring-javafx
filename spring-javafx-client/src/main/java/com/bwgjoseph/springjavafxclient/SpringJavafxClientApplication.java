package com.bwgjoseph.springjavafxclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bwgjoseph.springjavafxclient.application.FeathersUIApplication;

import javafx.application.Application;

@SpringBootApplication
public class SpringJavafxClientApplication {

	public static void main(String[] args) {
		Application.launch(FeathersUIApplication.class, args);
	}
}
