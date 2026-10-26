CREATE TABLE IF NOT EXISTS client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    telephone VARCHAR(50),
    adresse VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS produit (
    id_produit INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    description TEXT,
    prix DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS commande (
    id_commande INT AUTO_INCREMENT PRIMARY KEY,
    date_commande DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_client INT NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_COURS',
    CONSTRAINT fk_commande_client FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lignedecommande (
    id_ligne INT AUTO_INCREMENT PRIMARY KEY,
    id_commande INT NOT NULL,
    id_produit INT NOT NULL,
    quantite INT NOT NULL,
    prix_unitaire DOUBLE NOT NULL,
    CONSTRAINT fk_ligne_commande FOREIGN KEY (id_commande) REFERENCES commande(id_commande) ON DELETE CASCADE,
    CONSTRAINT fk_ligne_produit FOREIGN KEY (id_produit) REFERENCES produit(id_produit) ON DELETE RESTRICT
);
