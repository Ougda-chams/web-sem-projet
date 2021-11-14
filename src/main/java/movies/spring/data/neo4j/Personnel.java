package movies.spring.data.neo4j;

import java.io.Serializable;

public class Personnel implements Serializable {
    private String nom;

    private String prenom;

    private  String fonction;

    private  String ville;

    public Personnel() {}
    public Personnel(String nom, String prenom, String fonction, String ville) {
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.ville = ville;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public String getVille() {
        return ville;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public void setVille(String ville) { this.ville = ville; }

}
