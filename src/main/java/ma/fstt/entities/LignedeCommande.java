package ma.fstt.entities;

import java.io.Serializable;

public class LignedeCommande implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idLigne;
    private Commande commande;
    private Produit produit;
    private int quantite;
    private double prixUnitaire;

    public LignedeCommande() {
    }

    public LignedeCommande(Long idLigne, Commande commande, Produit produit, int quantite, double prixUnitaire) {
        this.idLigne = idLigne;
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public LignedeCommande(Commande commande, Produit produit, int quantite, double prixUnitaire) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Long idLigne) {
        this.idLigne = idLigne;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getSousTotal() {
        return quantite * prixUnitaire;
    }

    @Override
    public String toString() {
        return "LignedeCommande{" +
                "idLigne=" + idLigne +
                ", commande=" + (commande != null ? commande.getIdCommande() : null) +
                ", produit=" + (produit != null ? produit.getNom() : null) +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                '}';
    }
}
