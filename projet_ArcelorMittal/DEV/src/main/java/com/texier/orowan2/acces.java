package com.texier.orowan2;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

public class acces {
    private final Stage stage;
    ConnexionBDD a = ConnexionBDD.getInstance();
    User u;

    acces(Stage stage,User u) {
        this.stage = stage;
        this.u = u;
    }

    public void show() throws SQLException, ClassNotFoundException {
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();


        hbox1.setSpacing(24);
        hbox2.setSpacing(9);

        //hbox3
        Label annonce;
        annonce = new Label("");
        Button retour = new Button("retour");
        retour.setOnAction(event -> {
            Accueil mainView = new Accueil(stage,u);
            mainView.show();

        });


        hbox3.getChildren().addAll(retour,annonce);
        hbox3.setSpacing(100);


        //hbox1
        TextField user_1 = new TextField("");
        user_1.setPromptText("id");

        TextField pass = new TextField("");
        pass.setPromptText("mot de passe");
        TextField priv = new TextField("");
        priv.setPromptText("priv");
        priv.setMaxWidth(30);
        Button ajouter = new Button("ajouter");
        ajouter.setOnAction(event -> {
            String username = user_1.getText();
            String password = pass.getText();
            String privi = priv.getText();
            try {
                ajouter(username,password,privi);
                annonce.setText("id ajouté");
            } catch (SQLException ex) {
                Logger.getLogger(acces.class.getName()).log(Level.SEVERE, null, ex);
            }
            user_1.setText("");
            pass.setText("");
            priv.setText("");
            try {
                refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        // Ajouter des éléments à la première HBox
        hbox1.getChildren().addAll(new Label("Ajouter "),user_1, pass,priv,ajouter);


        //hbox2
        TextField user_2 = new TextField("");
        user_2.setPromptText("id");
        Button supprimer = new Button("supprimer");
        supprimer.setOnAction(event -> {
            String username_2 = user_2.getText();
            try {
                supprimer(username_2);
                annonce.setText("id supprimé");
                refresh();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(acces.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // Ajouter des éléments à la deuxième HBox
        hbox2.getChildren().addAll(new Label("Supprimer"),user_2,supprimer);


        //hbox4
        Label ud = new Label("Modifier");
        TextField id_4 = new TextField();
        id_4.setPromptText("id");

        ComboBox<String> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        "login", "password", "privilege"
                )
        );

        comboBox.setPromptText("Choisir une option");
        Label to = new Label(" pour ");
        TextField updated = new TextField();
        updated.setPromptText("nouveau");
        Button update_4 = new Button("changer");
        update_4.setOnAction(event -> {
            String username_4 = id_4.getText();
            String change = comboBox.getValue();
            String updated_text = updated.getText();
            try {
                upa(username_4,change,updated_text);
                annonce.setText("changé !" );
                refresh();
            } catch (SQLException ex) {
                Logger.getLogger(acces.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        hbox4.getChildren().addAll(ud,id_4,comboBox,to,updated,update_4);
        hbox4.setSpacing(24);
        String sql = "SELECT * FROM connexion";
        ResultSet resultat = a.lireBDD(sql);
        TableView<Data> tableView = new TableView<Data>();
        ObservableList<Data> data = FXCollections.observableArrayList();


        // Ajout des données au tableau
        while (resultat.next()) {
            data.add(new Data(resultat.getString(3), resultat.getString(4), resultat.getInt(2)));
        }

        TableColumn<Data, String> column1 = new TableColumn<Data, String>("login");
        column1.setCellValueFactory(new PropertyValueFactory<Data, String>("column1"));

        TableColumn<Data, String> column2 = new TableColumn<Data, String>("password");
        column2.setCellValueFactory(new PropertyValueFactory<Data, String>("column2"));

        TableColumn<Data, Integer> column3 = new TableColumn<Data, Integer>("privilege");
        column3.setCellValueFactory(new PropertyValueFactory<Data, Integer>("column3"));
        TableColumn<Data, String> column4 = new TableColumn<>("email");
        column4.setCellValueFactory(cellData -> {
            String email = cellData.getValue().getColumn1() + "@arcellor-mittal.com";
            return new SimpleStringProperty(email);
        });
        tableView.getColumns().addAll(column1, column2, column3, column4);


        // Ajout des données dans le TableView
        tableView.setItems(data);

        StackPane layout = new StackPane();
        layout.getChildren().add(tableView);
        StackPane.setAlignment(tableView, Pos.CENTER);
        tableView.setPrefWidth(100);
        layout.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(hbox1, hbox2, hbox4, hbox3, layout);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));
        // Créer la scène avec les deux HBox
        Scene scene = new Scene(vbox, 800, 500);

        // Définir le titre de la fenêtre


        stage.setTitle("gestion des accès");
        // Ajouter la scène à la fenêtre et l'afficher
        stage.setScene(scene);

    }


    private void ajouter(String username, String password, String privi) throws SQLException {
        if(Integer.parseInt(privi) < u.privilege){
            a.Inserer_id(username, password, privi);
        }else{
        }
    }

    private void supprimer(String username_2) throws SQLException {
        if(check_privi(username_2)){
        }
        else{
            a.supprimer(username_2);
        }
    }

    private void upa(String username_4, String change, String updated_text) throws SQLException {
        if(check_privi(username_4)){
        }else{
            a.update(username_4,change,updated_text);
        }
    }

    private void refresh() throws SQLException, ClassNotFoundException {
        acces acc = new acces(stage,u);
        try {
            acc.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean check_privi(String user) throws SQLException {
        return a.check_priv(user,u.privilege);
    }
}


