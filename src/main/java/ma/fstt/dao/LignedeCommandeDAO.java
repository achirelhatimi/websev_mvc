package ma.fstt.dao;

import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.LignedeCommandeService;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LignedeCommandeDAO implements LignedeCommandeService {

    @Override
    public boolean create(LignedeCommande ligne) {
        String sql = "INSERT INTO lignedecommande (id_commande, id_produit, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, ligne.getCommande().getIdCommande());
            ps.setLong(2, ligne.getProduit().getIdProduit());
            ps.setInt(3, ligne.getQuantite());
            ps.setDouble(4, ligne.getPrixUnitaire());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        ligne.setIdLigne(rs.getLong(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(LignedeCommande ligne) {
        String sql = "UPDATE lignedecommande SET id_commande = ?, id_produit = ?, quantite = ?, prix_unitaire = ? WHERE id_ligne = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, ligne.getCommande().getIdCommande());
            ps.setLong(2, ligne.getProduit().getIdProduit());
            ps.setInt(3, ligne.getQuantite());
            ps.setDouble(4, ligne.getPrixUnitaire());
            ps.setLong(5, ligne.getIdLigne());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM lignedecommande WHERE id_ligne = ?";
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
    public LignedeCommande getOne(Long id) {
        String sql = "SELECT l.*, p.nom AS prod_nom, p.description AS prod_desc, p.prix AS prod_prix, p.stock AS prod_stock " +
                "FROM lignedecommande l " +
                "JOIN produit p ON l.id_produit = p.id_produit " +
                "WHERE l.id_ligne = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLigne(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LignedeCommande> getAll() {
        List<LignedeCommande> list = new ArrayList<>();
        String sql = "SELECT l.*, p.nom AS prod_nom, p.description AS prod_desc, p.prix AS prod_prix, p.stock AS prod_stock " +
                "FROM lignedecommande l " +
                "JOIN produit p ON l.id_produit = p.id_produit " +
                "ORDER BY l.id_ligne DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToLigne(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<LignedeCommande> getByCommandeId(Long idCommande) {
        List<LignedeCommande> list = new ArrayList<>();
        String sql = "SELECT l.*, p.nom AS prod_nom, p.description AS prod_desc, p.prix AS prod_prix, p.stock AS prod_stock " +
                "FROM lignedecommande l " +
                "JOIN produit p ON l.id_produit = p.id_produit " +
                "WHERE l.id_commande = ? " +
                "ORDER BY l.id_ligne ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLigne(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private LignedeCommande mapResultSetToLigne(ResultSet rs) throws SQLException {
        LignedeCommande ligne = new LignedeCommande();
        ligne.setIdLigne(rs.getLong("id_ligne"));
        ligne.setQuantite(rs.getInt("quantite"));
        ligne.setPrixUnitaire(rs.getDouble("prix_unitaire"));

        Commande c = new Commande();
        c.setIdCommande(rs.getLong("id_commande"));
        ligne.setCommande(c);

        Produit p = new Produit();
        p.setIdProduit(rs.getLong("id_produit"));
        p.setNom(rs.getString("prod_nom"));
        p.setDescription(rs.getString("prod_desc"));
        p.setPrix(rs.getDouble("prod_prix"));
        p.setStock(rs.getInt("prod_stock"));
        ligne.setProduit(p);

        return ligne;
    }
}
