package ma.fstt.service;

import ma.fstt.entities.Produit;

public interface ProduitService extends IDAO<Produit> {
    boolean updateStock(Long idProduit, int quantite);
}
