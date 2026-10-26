package ma.fstt.service;

import ma.fstt.entities.LignedeCommande;

import java.util.List;

public interface LignedeCommandeService extends IDAO<LignedeCommande> {
    List<LignedeCommande> getByCommandeId(Long idCommande);
}
