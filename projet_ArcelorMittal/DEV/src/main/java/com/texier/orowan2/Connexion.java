package com.texier.orowan2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Connexion {
    ConnexionBDD a = ConnexionBDD.getInstance();
    private final Stage stage;
    private User u;
    public Connexion(Stage stage,User u) {
        this.stage = stage;
        this.u = u;
        try {
            a.SeConnecter();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void show() {
        stage.setTitle("Connexion");

        // Créer la scène de connexion
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(85, 25, 25, 25));

        Label usernameLabel = new Label("Nom d'utilisateur :");
        TextField usernameField = new TextField();
        loginGrid.add(usernameLabel, 0, 1);
        loginGrid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();
        loginGrid.add(passwordLabel, 0, 2);
        loginGrid.add(passwordField, 1, 2);

        Label connecter = new Label("");
        loginGrid.add(connecter, 2, 2);


        Button loginButton = new Button("Se connecter");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                if (check(username,password)) {
                    showMainView();
                }else{
                    connecter.setText("couple Id et mdp incorrectes");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        loginGrid.add(loginButton, 1, 3);
        Scene loginScene = new Scene(loginGrid, 500, 500);

        ImageView background = new ImageView(new Image("C:\\Users\\33695\\Desktop\\fond_ecran_accueil.jpg"));


        loginGrid.setBackground(new Background(new BackgroundImage(background.getImage(),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));

        stage.setScene(loginScene);
        stage.show();
    }


    private void showMainView() {
        Accueil mainView = new Accueil(stage,u);
        mainView.show();
    }

    private boolean check(String username, String password) throws ClassNotFoundException, SQLException {
        String requete = "select * from connexion WHERE login = '"+username+"' and password ='"+password+"'";
        ResultSet result = a.lireBDD(requete);
        if(result.next()) {

            u.privilege = result.getInt("privilege") ;
            System.out.println(u.privilege);
            return true;
        }
        return false;
    }


}


