package ma.fstt.service;

import ma.fstt.entities.Commande;

import java.util.List;

public interface CommandeService extends IDAO<Commande> {
    List<Commande> getByClientId(Long idClient);
}
