package com.texier.orowan2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBDD {

    private static ConnexionBDD instance;

    Connection conn ;
    private ConnexionBDD() {

    }

    public void SeConnecter() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:~/test","username","password");
        System.out.println("connection data : "+ !conn.isClosed());
    }

    public void SeDeconnecter() throws SQLException {
        conn.close();
    }

    public void Inserer_id(String user, String mdp, String privi) throws SQLException{
        String sql = "INSERT INTO CONNEXION (login, password, privilege) VALUES ('"+user+"','"+mdp+"','"+privi+"')";

        // Préparation de la requête SQL avec les valeurs à insérer
        PreparedStatement statement = conn.prepareStatement(sql);
        // Exécution de la requête SQL pour l'insertion des valeurs dans la table
        int rowsInserted = statement.executeUpdate();
        // Vérification du nombre de lignes insérées
        if (rowsInserted > 0) {
            System.out.println("Les valeurs ont été insérées dans la table avec succès !");
        } else {
            System.out.println("Aucune valeur n'a été insérée dans la table.");
        }
    }

    public ResultSet lireBDD(String requete) throws ClassNotFoundException, SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(requete);
        return resultSet ;
    }

    public static ConnexionBDD getInstance() {
        if (instance == null)
            instance = new ConnexionBDD();

        return instance;
    }

    void supprimer(String username_2) throws SQLException {

        String sql = "DELETE FROM connexion WHERE login = '"+username_2+"'";

        // Préparation de la requête SQL avec la condition de suppression
        PreparedStatement statement = conn.prepareStatement(sql);


        // Exécution de la requête SQL pour la suppression de la ligne sous la condition donnée
        int rowsDeleted = statement.executeUpdate();

        // Vérification du nombre de lignes supprimées
        if (rowsDeleted > 0) {
            System.out.println("La ligne a été supprimée de la table avec succès !");
        } else {
            System.out.println("Aucune ligne n'a été supprimée de la table.");
        }
    }

    void update(String username_4, String change, String updated_text) throws SQLException {
        String sql = "UPDATE connexion SET "+change+" = '"+updated_text+"' WHERE login = '"+username_4+"'" ;

        // Préparation de la requête SQL avec les valeurs à mettre à jour
        PreparedStatement statement = conn.prepareStatement(sql);

        // Exécution de la requête SQL pour la mise à jour de la ligne
        int rowsUpdated = statement.executeUpdate();

        // Vérification du nombre de lignes mises à jour
        if (rowsUpdated > 0) {
            System.out.println("La ligne a été mise à jour avec succès !");
        } else {
            System.out.println("Aucune ligne n'a été mise à jour.");
        }
    }
    public boolean check_priv(String user, int privilege) throws SQLException {
        String requete = "SELECT privilege FROM connexion where login = '"+user+ "'";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(requete);
        int a = 0;
        if(resultSet.next()){
            a = resultSet.getInt(1);
        }
        if(a>=privilege){
            return true;
        }
        else{
            return false;
        }
    }

}
