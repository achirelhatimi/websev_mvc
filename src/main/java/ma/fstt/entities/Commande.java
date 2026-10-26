package ma.fstt.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Commande implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idCommande;
    private Timestamp dateCommande;
    private Client client;
    private String statut;
    private List<LignedeCommande> lignes = new ArrayList<>();

    public Commande() {
    }

    public Commande(Long idCommande, Timestamp dateCommande, Client client, String statut) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.client = client;
        this.statut = statut;
    }

    public Commande(Timestamp dateCommande, Client client, String statut) {
        this.dateCommande = dateCommande;
        this.client = client;
        this.statut = statut;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Timestamp getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Timestamp dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<LignedeCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LignedeCommande> lignes) {
        this.lignes = lignes;
    }

    public void addLigne(LignedeCommande ligne) {
        this.lignes.add(ligne);
        ligne.setCommande(this);
    }

    public double getTotal() {
        double total = 0.0;
        if (lignes != null) {
            for (LignedeCommande ligne : lignes) {
                total += ligne.getSousTotal();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", client=" + (client != null ? client.getNomComplet() : null) +
                ", statut='" + statut + '\'' +
                ", nombreLignes=" + (lignes != null ? lignes.size() : 0) +
                '}';
    }
}
