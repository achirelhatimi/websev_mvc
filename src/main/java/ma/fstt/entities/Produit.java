package ma.fstt.entities;

import java.io.Serializable;

public class Produit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idProduit;
    private String nom;
    private String description;
    private double prix;
    private int stock;

    public Produit() {
    }

    public Produit(Long idProduit, String nom, String description, double prix, int stock) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    public Produit(String nom, String description, double prix, int stock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}
