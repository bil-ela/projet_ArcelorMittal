package com.texier.orowan2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemplirBDD {

    public void execute() throws ClassNotFoundException, SQLException, IOException {

        // Création d'une instance de la classe ConnexionBDD
        ConnexionBDD connexionBDD = ConnexionBDD.getInstance();
        // Connexion à la base de données
        connexionBDD.SeConnecter();

        // Chemin vers le fichier CSV contenant les données à insérer
        String csvFile = "C:\\Users\\33695\\Desktop\\output3.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            // Lecture de la première ligne (entête)
            line = br.readLine();

            // Lecture des lignes suivantes (données)
            while ((line = br.readLine()) != null) {

                // Séparation des valeurs en utilisant la virgule comme délimiteur
                String[] values = line.split(cvsSplitBy);

                // Création de la requête SQL pour insérer les valeurs dans la table OUTPUT2
                String sql = "INSERT INTO OUTPUT3 (CASE_NUMBER, ERRORS, OFFSETYIELD, FRICTION, ROLLING_TORQUE, SIGMA_MOY, SIGMA_INI, SIGMA_OUT, SIGMA_MAX, FORCE_ERROR, SLIP_ERROR, HAS_CONVERGED) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Préparation de la requête SQL avec les valeurs à insérer
                PreparedStatement statement = connexionBDD.conn.prepareStatement(sql);
                // Attribution des valeurs aux paramètres de la requête SQL
                statement.setInt(1, Integer.parseInt(values[0]));
                statement.setString(2, values[1]);
                statement.setFloat(3, Float.parseFloat(values[2]));
                statement.setFloat(4, Float.parseFloat(values[3]));
                statement.setFloat(5, Float.parseFloat(values[4]));
                statement.setFloat(6, Float.parseFloat(values[5]));
                statement.setFloat(7, Float.parseFloat(values[6]));
                statement.setFloat(8, Float.parseFloat(values[7]));
                statement.setFloat(9, Float.parseFloat(values[8]));
                statement.setFloat(10, Float.parseFloat(values[9]));
                statement.setFloat(11, Float.parseFloat(values[10]));
                statement.setBoolean(12, Boolean.parseBoolean(values[11]));
                // Exécution de la requête SQL pour l'insertion des valeurs dans la table
                int rowsInserted = statement.executeUpdate();
                // Vérification du nombre de lignes insérées
                if (rowsInserted > 0) {
                    System.out.println("Les valeurs ont été insérées dans la table avec succès !");
                } else {
                    System.out.println("Aucune valeur n'a été insérée dans la table.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Déconnexion de la base de données
            connexionBDD.SeDeconnecter();
        }
    }
}

