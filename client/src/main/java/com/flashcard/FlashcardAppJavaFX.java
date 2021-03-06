package com.flashcard;

import com.flashcard.event.ShowViewEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class FlashcardAppJavaFX extends Application {

    private ConfigurableApplicationContext context;


    @Override
    public void init() {

        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> FlashcardAppJavaFX.this);
                    ac.registerBean(Parameters.class, this::getParameters);
                    ac.registerBean(HostServices.class, this::getHostServices);
                };

        this.context = new SpringApplicationBuilder()
                .sources(FlashcardAppSpring.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        this.context.publishEvent(new ShowViewEvent(primaryStage,"/com/flashcard/view/menuView.fxml"));

    }
    @Override
    public void stop() {
        this.context.stop();
        Platform.exit();
    }



}
