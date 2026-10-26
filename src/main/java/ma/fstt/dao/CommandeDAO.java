package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.CommandeService;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommandeDAO implements CommandeService {

    @Override
    public boolean create(Commande commande) {
        String sqlCommande = "INSERT INTO commande (date_commande, id_client, statut) VALUES (?, ?, ?)";
        String sqlLigne = "INSERT INTO lignedecommande (id_commande, id_produit, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE produit SET stock = stock - ? WHERE id_produit = ? AND stock >= ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            long commandeId;
            try (PreparedStatement psCmd = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS)) {
                Timestamp ts = commande.getDateCommande() != null ? commande.getDateCommande() : new Timestamp(System.currentTimeMillis());
                psCmd.setTimestamp(1, ts);
                psCmd.setLong(2, commande.getClient().getIdClient());
                psCmd.setString(3, commande.getStatut() != null ? commande.getStatut() : "EN_COURS");

                int affected = psCmd.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false;
                }

                try (ResultSet rs = psCmd.getGeneratedKeys()) {
                    if (rs.next()) {
                        commandeId = rs.getLong(1);
                        commande.setIdCommande(commandeId);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            if (commande.getLignes() != null && !commande.getLignes().isEmpty()) {
                try (PreparedStatement psLigne = conn.prepareStatement(sqlLigne, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement psStock = conn.prepareStatement(sqlStock)) {

                    for (LignedeCommande ligne : commande.getLignes()) {
                        psLigne.setLong(1, commandeId);
                        psLigne.setLong(2, ligne.getProduit().getIdProduit());
                        psLigne.setInt(3, ligne.getQuantite());
                        psLigne.setDouble(4, ligne.getPrixUnitaire());
                        psLigne.executeUpdate();

                        try (ResultSet rsLigne = psLigne.getGeneratedKeys()) {
                            if (rsLigne.next()) {
                                ligne.setIdLigne(rsLigne.getLong(1));
                            }
                        }

                        psStock.setInt(1, ligne.getQuantite());
                        psStock.setLong(2, ligne.getProduit().getIdProduit());
                        psStock.setInt(3, ligne.getQuantite());
                        int stockUpdated = psStock.executeUpdate();
                        if (stockUpdated == 0) {
                            conn.rollback();
                            return false;
                        }
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean update(Commande commande) {
        String sql = "UPDATE commande SET statut = ? WHERE id_commande = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, commande.getStatut());
            ps.setLong(2, commande.getIdCommande());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM commande WHERE id_commande = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Commande getOne(Long id) {
        String sql = "SELECT c.*, cl.nom AS cli_nom, cl.prenom AS cli_prenom, cl.email AS cli_email, " +
                "cl.telephone AS cli_tel, cl.adresse AS cli_adr " +
                "FROM commande c " +
                "JOIN client cl ON c.id_client = cl.id_client " +
                "WHERE c.id_commande = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande cmd = mapResultSetToCommande(rs);
                    cmd.setLignes(fetchLignes(conn, cmd.getIdCommande()));
                    return cmd;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Commande> getAll() {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT c.*, cl.nom AS cli_nom, cl.prenom AS cli_prenom, cl.email AS cli_email, " +
                "cl.telephone AS cli_tel, cl.adresse AS cli_adr " +
                "FROM commande c " +
                "JOIN client cl ON c.id_client = cl.id_client " +
                "ORDER BY c.id_commande DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Commande cmd = mapResultSetToCommande(rs);
                cmd.setLignes(fetchLignes(conn, cmd.getIdCommande()));
                list.add(cmd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Commande> getByClientId(Long idClient) {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT c.*, cl.nom AS cli_nom, cl.prenom AS cli_prenom, cl.email AS cli_email, " +
                "cl.telephone AS cli_tel, cl.adresse AS cli_adr " +
                "FROM commande c " +
                "JOIN client cl ON c.id_client = cl.id_client " +
                "WHERE c.id_client = ? " +
                "ORDER BY c.id_commande DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idClient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Commande cmd = mapResultSetToCommande(rs);
                    cmd.setLignes(fetchLignes(conn, cmd.getIdCommande()));
                    list.add(cmd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Commande mapResultSetToCommande(ResultSet rs) throws SQLException {
        Commande cmd = new Commande();
        cmd.setIdCommande(rs.getLong("id_commande"));
        cmd.setDateCommande(rs.getTimestamp("date_commande"));
        cmd.setStatut(rs.getString("statut"));

        Client client = new Client();
        client.setIdClient(rs.getLong("id_client"));
        client.setNom(rs.getString("cli_nom"));
        client.setPrenom(rs.getString("cli_prenom"));
        client.setEmail(rs.getString("cli_email"));
        client.setTelephone(rs.getString("cli_tel"));
        client.setAdresse(rs.getString("cli_adr"));
        cmd.setClient(client);

        return cmd;
    }

    private List<LignedeCommande> fetchLignes(Connection conn, Long idCommande) throws SQLException {
        List<LignedeCommande> lignes = new ArrayList<>();
        String sql = "SELECT l.*, p.nom AS prod_nom, p.description AS prod_desc, p.prix AS prod_prix, p.stock AS prod_stock " +
                "FROM lignedecommande l " +
                "JOIN produit p ON l.id_produit = p.id_produit " +
                "WHERE l.id_commande = ? " +
                "ORDER BY l.id_ligne ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LignedeCommande ligne = new LignedeCommande();
                    ligne.setIdLigne(rs.getLong("id_ligne"));
                    ligne.setQuantite(rs.getInt("quantite"));
                    ligne.setPrixUnitaire(rs.getDouble("prix_unitaire"));

                    Produit prod = new Produit();
                    prod.setIdProduit(rs.getLong("id_produit"));
                    prod.setNom(rs.getString("prod_nom"));
                    prod.setDescription(rs.getString("prod_desc"));
                    prod.setPrix(rs.getDouble("prod_prix"));
                    prod.setStock(rs.getInt("prod_stock"));
                    ligne.setProduit(prod);

                    lignes.add(ligne);
                }
            }
        }
        return lignes;
    }
}
