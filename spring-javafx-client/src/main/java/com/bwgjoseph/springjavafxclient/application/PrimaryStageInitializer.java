package com.bwgjoseph.springjavafxclient.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.controller.MainController;
import com.bwgjoseph.springjavafxclient.event.StageReadyEvent;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) { 
    	Assert.notNull(fxWeaver, "fxWeaver cannot be null");
    	
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) { 
        Stage stage = event.stage;
        Scene scene = new Scene(fxWeaver.loadView(MainController.class), 400, 300); 
        stage.setScene(scene);
        stage.show();
    }
}