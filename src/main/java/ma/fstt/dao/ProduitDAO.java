package ma.fstt.dao;

import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProduitDAO implements ProduitService {

    @Override
    public boolean create(Produit produit) {
        String sql = "INSERT INTO produit (nom, description, prix, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setInt(4, produit.getStock());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        produit.setIdProduit(rs.getLong(1));
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
    public boolean update(Produit produit) {
        String sql = "UPDATE produit SET nom = ?, description = ?, prix = ?, stock = ? WHERE id_produit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setInt(4, produit.getStock());
            ps.setLong(5, produit.getIdProduit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM produit WHERE id_produit = ?";
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
    public Produit getOne(Long id) {
        String sql = "SELECT * FROM produit WHERE id_produit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduit(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit ORDER BY id_produit DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                produits.add(mapResultSetToProduit(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    @Override
    public boolean updateStock(Long idProduit, int quantite) {
        String sql = "UPDATE produit SET stock = stock - ? WHERE id_produit = ? AND stock >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setLong(2, idProduit);
            ps.setInt(3, quantite);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Produit mapResultSetToProduit(ResultSet rs) throws SQLException {
        Produit produit = new Produit();
        produit.setIdProduit(rs.getLong("id_produit"));
        produit.setNom(rs.getString("nom"));
        produit.setDescription(rs.getString("description"));
        produit.setPrix(rs.getDouble("prix"));
        produit.setStock(rs.getInt("stock"));
        return produit;
    }
}
