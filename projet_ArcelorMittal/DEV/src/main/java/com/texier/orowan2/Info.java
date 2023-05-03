package com.texier.orowan2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Info {
    private final Stage stage;
    private User u;

    public Info(Stage stage, User u) {
        this.stage = stage;
        this.u = u;
    }

    public void show() {
        stage.setTitle("Informations");

        // Créer la scène principale
        GridPane layout = new GridPane();

        layout.setPadding(new Insets(20));
        layout.setHgap(20);
        layout.setVgap(50);

        // Ajouter le bouton "Retour à l'accueil"
        Image homeIcon = new Image("C:\\Users\\33695\\Desktop\\Home Button.png");
        ImageView imageView = new ImageView(homeIcon);
        imageView.setFitWidth(50); // ajuster la largeur si nécessaire
        imageView.setFitHeight(50); // ajuster la hauteur si nécessaire
        Button homeButton = new Button("Retour à l'accueil", imageView);
        layout.add(homeButton, 1, 1);

        // Définir l'événement pour le bouton "Retour à l'accueil"
        homeButton.setOnAction(event -> {
            Accueil accueil = new Accueil(stage, u);
            accueil.show();
        });

        // Créer les ImageView pour les deux images
        ImageView image1 = new ImageView(new Image("file:C:/Users/33695/Desktop/File format.png"));
        ImageView image2 = new ImageView(new Image("file:C:/Users/33695/Desktop/csv output file.png"));

        // Créer un VBox pour afficher les images l'une au-dessus de l'autre
        VBox vbox = new VBox(image1, image2);
        vbox.setSpacing(10);

        // Créer un ScrollPane pour permettre de faire défiler les images
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);


        // Ajouter le ScrollPane à la grille de la scène
        layout.add(scrollPane, 0, 2);

        // Créer la scène
        Scene mainScene = new Scene(layout, 870, 490);

        // Afficher la fenêtre
        stage.setScene(mainScene);
        stage.show();
    }}


