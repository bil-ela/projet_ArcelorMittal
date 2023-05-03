package com.texier.orowan2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bagafoufabrice
 */
public class GraphiqueApp {
    private String option;
    private String table;
    private final Stage stage;

    int n =0;
    int valeur=0;
    double seuil = 30000;
    User u;
    ConnexionBDD a = ConnexionBDD.getInstance();
    XYChart.Series<Number, Number> ser;

    public GraphiqueApp(Stage stage, String option, String table, User u) {
        this.option = option;
        this.u = u;
        this.table = table;
        this.stage = stage;
        this.ser = new XYChart.Series<>();
        import_donner();
    }

    public void show(Stage stage) throws Exception {
        // Créer le graphique
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps");
        yAxis.setLabel("Valeur");

        Label max = new Label("maximum = ");
        Label seuillage = new Label("seuil = ");

        // Ajouter des données au graphique
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Graphique temps réel de "+ option);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Données");
        lineChart.getData().add(series);

        //lecture toute les secondes
        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = 0;
            double maximum = 0;

            @Override
            public void handle(long maintenant) {
                if (dernierTemps == 0) {
                    dernierTemps = maintenant;
                    return;
                }

                // Ajout d'une valeur au graphique toutes les secondes
                if (maintenant - dernierTemps > 1_000_000_000) {
                    valeur= valeur+1;
                    if(valeur == n){
                        return;
                    }
                    if ((double)ser.getData().get(valeur).getYValue()>maximum){
                        maximum = (double) ser.getData().get(valeur).getYValue();
                        System.out.println(maximum);
                        double arrondi = Math.round(maximum * Math.pow(10, 2)) / Math.pow(10, 2);
                        max.setText("maximum = "+String.valueOf(arrondi));
                        if(maximum>seuil){
                            alerte();
                            return;
                        }
                    }
                    XYChart.Data<Number, Number> data = ser.getData().get(valeur);
                    series.getData().add(new XYChart.Data<>(data.getXValue(),data.getYValue()));
                    dernierTemps = maintenant;
                }
            }
        };
        timer.start();


        Button retour = new Button("retour");
        retour.setOnAction(event -> {
            Accueil acc = new Accueil(stage,u);
            acc.show();
        });

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> {
            if (timer != null) {
                timer.stop();
            }
        });


        TextField mini = new TextField();
        mini.setMaxSize(50,10);
        Button set = new Button("set");
        set.setOnAction(event -> {
            double min = Double.parseDouble(mini.getText());
            seuil = min;
            seuillage.setText("seuil = "+seuil);
        });


        // Créer la mise en page
        HBox hbox  = new HBox();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 5));
        grid.setHgap(10);
        grid.setVgap(20);
        grid.add(max,0,0);
        max.setMaxWidth(110);
        grid.add(mini,0,2);
        grid.add(seuillage,0,1);
        grid.add(set,1,2);
        grid.add(retour,0,3);
        grid.add(stopButton,1,3);
        BackgroundFill fill = new BackgroundFill(Color.BURLYWOOD, null, null );
        Background background = new Background(fill);
        grid.setBackground(background);
        grid.setPrefWidth(200);
        hbox.getChildren().addAll(lineChart,grid);

        // Créer la scène
        Scene scene = new Scene(hbox, 700, 600);

        // Afficher la fenêtre
        stage.setTitle("Graphique pour " +table +" : " + option);
        stage.setScene(scene);

    }
    // On importe dans le ser les données de la machine choisie
    public void import_donner(){
        //attention a choisir la bonne option
        String requete = "SELECT "+option+",case_number FROM "+table;
        try {
            ResultSet resultat = a.lireBDD(requete);
            int temps = 0;
            while (resultat.next()) {
                double y=0;
                for(int i=0;i<5;i++){
                    y=y+resultat.getDouble(1);
                    if(!resultat.next()){
                        break;
                    }
                }
                temps++;
                n=n+1;
                // Ajout des valeurs de la colonne à la série de données du graphique
                ser.getData().add(new XYChart.Data<>(temps,y/5));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraphiqueApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GraphiqueApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alerte(){
        // Création d'une alerte rouge
        Label alerte = new Label("ALERTE ROUGE !");
        alerte.setStyle("-fx-font-size: 48; -fx-text-fill: red;");

        // Création d'un layout pour la nouvelle fenêtre
        StackPane layout = new StackPane();
        layout.getChildren().add(alerte);

        Scene scene = new Scene(layout, 800, 200);
        Stage nouvelleFenetre = new Stage();
        // Définir le titre de la fenêtre


        nouvelleFenetre.setTitle("Alerte");
        // Ajouter la scène à la fenêtre et l'afficher
        nouvelleFenetre.setScene(scene);
        nouvelleFenetre.show();

    }
}
