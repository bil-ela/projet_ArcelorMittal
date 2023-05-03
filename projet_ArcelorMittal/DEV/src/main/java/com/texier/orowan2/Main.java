package com.texier.orowan2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    User u = new User() ;
    ConnexionBDD a = ConnexionBDD.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Connexion loginView = new Connexion(primaryStage,u);

        loginView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

