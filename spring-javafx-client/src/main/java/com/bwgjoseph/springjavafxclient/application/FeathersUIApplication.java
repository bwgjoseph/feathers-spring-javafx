package com.bwgjoseph.springjavafxclient.application;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.bwgjoseph.springjavafxclient.SpringJavafxClientApplication;
import com.bwgjoseph.springjavafxclient.event.StageReadyEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class FeathersUIApplication extends Application {

    private ConfigurableApplicationContext context;
    
    @Override
    public void init() throws Exception {
        this.context = new SpringApplicationBuilder() 
                .sources(SpringJavafxClientApplication.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.publishEvent(new StageReadyEvent(primaryStage)); 
    }

    @Override
    public void stop() throws Exception { 
        this.context.close();
        Platform.exit();
    }
}
