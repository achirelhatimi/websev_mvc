package ma.fstt.web;

import ma.fstt.dao.ClientDAO;
import ma.fstt.dao.CommandeDAO;
import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Produit;
import ma.fstt.service.ClientService;
import ma.fstt.service.CommandeService;
import ma.fstt.service.ProduitService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ClientService clientDAO;

    @Inject
    private ProduitService produitDAO;

    @Inject
    private CommandeService commandeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        if (clientDAO == null) {
            clientDAO = new ClientDAO();
        }
        if (produitDAO == null) {
            produitDAO = new ProduitDAO();
        }
        if (commandeDAO == null) {
            commandeDAO = new CommandeDAO();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Client> clients = clientDAO.getAll();
        List<Produit> produits = produitDAO.getAll();
        List<Commande> commandes = commandeDAO.getAll();

        request.setAttribute("totalClients", clients.size());
        request.setAttribute("totalProduits", produits.size());
        request.setAttribute("totalCommandes", commandes.size());

        double chiffreAffaires = 0.0;
        for (Commande c : commandes) {
            chiffreAffaires += c.getTotal();
        }
        request.setAttribute("chiffreAffaires", chiffreAffaires);

        int recentLimit = Math.min(commandes.size(), 5);
        request.setAttribute("recentCommandes", commandes.subList(0, recentLimit));

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
