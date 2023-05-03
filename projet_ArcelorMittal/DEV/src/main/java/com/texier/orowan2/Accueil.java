package com.texier.orowan2;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.image.ImageView;


public class Accueil{

    private final Stage stage;
    static User u;
    public Accueil(Stage stage,User u) {
        this.stage = stage;
        this.u = u;
    }

    public void show() {
        stage.setTitle("Page d'accueil");

        // Créer la scène principale
        ComboBox<String> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Sigma_moy", "friction", "Sigma_out"
                )
        );

        ComboBox<String> comboBox_2 = new ComboBox<>(
                FXCollections.observableArrayList(
                        "output", "output2", "output3"
                )
        );

        comboBox_2.setPromptText("Machine");

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Option sélectionnée : " + newValue);
        });

        comboBox.setPromptText("Coefficient");

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(20));
        layout.setHgap(20);
        layout.setVgap(50);

        // Créer la mise en page
        comboBox_2.setPrefSize(200, 50);
        comboBox_2.setStyle("-fx-border-color: red;");
        comboBox.setPrefSize(200, 50);
        comboBox.setStyle("-fx-border-color: red;");
        layout.add(comboBox,18,3);
        layout.add(comboBox_2,18,2);

        Image powerIcon = new Image("C:\\Users\\33695\\Desktop\\bouton_deco.png");
        ImageView imageView = new ImageView(powerIcon);
        imageView.setFitWidth(50); // adjust the width as needed
        imageView.setFitHeight(50); // adjust the height as needed
        Button logoutButton = new Button("Se déconnecter", imageView);
        logoutButton.setOnAction(event -> {
            Connexion connexion = new Connexion(stage,u);
            connexion.show();

        });
        layout.add(logoutButton,1,4);

        Image logo_info = new Image("C:\\Users\\33695\\Desktop\\info.png");
        ImageView imageView2 = new ImageView(logo_info);
        imageView2.setFitWidth(50); // adjust the width as needed
        imageView2.setFitHeight(50); // adjust the height as needed
        Button infoButton = new Button("Info", imageView2);
        infoButton.setOnAction(event -> {
            Info info = new Info(stage,u);
            info.show();
        });
        layout.add(infoButton,1,1);

        Image logo = new Image("C:\\Users\\33695\\Desktop\\ArcelorMittal-logo.png");
        ImageView imageView3 = new ImageView(logo);
        imageView3.setFitWidth(150); // adjust the width as needed
        imageView3.setFitHeight(100); // adjust the height as needed
        Button logoButton = new Button("", imageView3);
        logoButton.setOnAction(event -> {
            logo logo1 = new logo(stage,u);
            logo1.show();
        });
        layout.add(logoButton,1,2);

        if(u.privilege>=1){
            Button Voir = new Button("Visualiser");
            Voir.setPrefSize(200, 100);
            Voir.setStyle("-fx-font-weight: bold;");
            layout.add(Voir,18,4);
            Voir.setOnAction(event -> {
                String selection = comboBox.getValue();
                String table = comboBox_2.getValue();
                if (selection != null) {
                    GraphiqueApp graphique = new GraphiqueApp(stage,selection,table,u);
                    try {
                        graphique.show(stage);
                    } catch (Exception ex) {
                        Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        System.out.println(u.privilege);

        if(u.privilege>=2){
            Button acces = new Button("Gestion des accès");
            acces.setPrefSize(200, 50);
            acces.setStyle("-fx-text-fill: red;");
            layout.add(acces,18,1);
            acces.setOnAction(event -> {
                acces acc = new acces(stage, u);
                try {
                    acc.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Image backgroundImage = new Image("file:///C:/Users/33695/Desktop/accueil.jpg");

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImageObj = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        layout.setBackground(new Background(backgroundImageObj)); // pour la grille
        // Créer la scène
        Scene mainScene = new Scene(layout, 870, 490);

        // Afficher la fenêtre
        stage.setTitle("Choix de la machine");
        stage.setScene(mainScene);
    }
}

