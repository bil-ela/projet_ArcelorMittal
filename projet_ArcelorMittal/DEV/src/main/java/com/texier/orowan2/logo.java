package com.texier.orowan2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class logo {
    private final Stage stage;
    User u;
    ConnexionBDD connexion = ConnexionBDD.getInstance();

    public logo(Stage stage, User u) {
        this.stage = stage;
        this.u = u;
    }

    public void show() {
        stage.setTitle("Logo1");

        // Créer la scène principale
        VBox layout = new VBox();
        layout.setPadding(new Insets(20));
        layout.setSpacing(10);

        // Ajouter les labels pour chaque base de données
        Label label1 = new Label("OUTPUT");
        Label label2 = new Label("OUTPUT1");
        Label label3 = new Label("OUTPUT2");

        layout.getChildren().addAll(label1, label2, label3);

        // Afficher les données de chaque base de données
        try {
            ResultSet resultSet = connexion.lireBDD("SELECT * FROM OUTPUT");
            while (resultSet.next()) {
                String data = resultSet.getString("FRICTION");
                Label outputLabel = new Label("OUTPUT : " + data);
                layout.getChildren().add(outputLabel);
            }

            resultSet = connexion.lireBDD("SELECT * FROM OUTPUT2");
            while (resultSet.next()) {
                String data = resultSet.getString("FRICTION");
                Label outputLabel = new Label("OUTPUT1 : " + data);
                layout.getChildren().add(outputLabel);
            }

            resultSet = connexion.lireBDD("SELECT * FROM OUTPUT3");
            while (resultSet.next()) {
                String data = resultSet.getString("FRICTION");
                Label outputLabel = new Label("OUTPUT2 : " + data);
                layout.getChildren().add(outputLabel);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Créer la scrollpane pour le layout
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Créer la scène avec la scrollpane
        Scene scene = new Scene(scrollPane, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
