package ma.fstt.web;

import ma.fstt.dao.ClientDAO;
import ma.fstt.dao.CommandeDAO;
import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
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
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "CommandeServlet", urlPatterns = {"/commandes"})
public class CommandeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private CommandeService commandeDAO;

    @Inject
    private ClientService clientDAO;

    @Inject
    private ProduitService produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        if (commandeDAO == null) {
            commandeDAO = new CommandeDAO();
        }
        if (clientDAO == null) {
            clientDAO = new ClientDAO();
        }
        if (produitDAO == null) {
            produitDAO = new ProduitDAO();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "details":
                showDetails(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCommande(request, response);
                break;
            case "list":
            default:
                listCommandes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "insert":
                insertCommande(request, response);
                break;
            case "update":
                updateCommande(request, response);
                break;
            default:
                listCommandes(request, response);
                break;
        }
    }

    private void listCommandes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Commande> commandes = commandeDAO.getAll();
        request.setAttribute("commandes", commandes);
        request.getRequestDispatcher("/WEB-INF/views/commandes/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Client> clients = clientDAO.getAll();
        List<Produit> produits = produitDAO.getAll();
        request.setAttribute("clients", clients);
        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/commandes/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Commande commande = commandeDAO.getOne(id);
        request.setAttribute("commande", commande);
        request.getRequestDispatcher("/WEB-INF/views/commandes/edit.jsp").forward(request, response);
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Commande commande = commandeDAO.getOne(id);
        request.setAttribute("commande", commande);
        request.getRequestDispatcher("/WEB-INF/views/commandes/details.jsp").forward(request, response);
    }

    private void insertCommande(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        String statut = request.getParameter("statut");
        if (statut == null || statut.trim().isEmpty()) {
            statut = "EN_COURS";
        }

        Client client = clientDAO.getOne(clientId);
        Commande commande = new Commande(new Timestamp(System.currentTimeMillis()), client, statut);

        String[] produitIds = request.getParameterValues("produitId[]");
        String[] quantites = request.getParameterValues("quantite[]");

        if (produitIds != null && quantites != null) {
            for (int i = 0; i < produitIds.length; i++) {
                if (produitIds[i] != null && !produitIds[i].isEmpty() && quantites[i] != null && !quantites[i].isEmpty()) {
                    Long pId = Long.parseLong(produitIds[i]);
                    int qte = Integer.parseInt(quantites[i]);
                    if (qte > 0) {
                        Produit p = produitDAO.getOne(pId);
                        if (p != null) {
                            LignedeCommande ligne = new LignedeCommande(commande, p, qte, p.getPrix());
                            commande.addLigne(ligne);
                        }
                    }
                }
            }
        }

        commandeDAO.create(commande);
        response.sendRedirect(request.getContextPath() + "/commandes?msg=created");
    }

    private void updateCommande(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String statut = request.getParameter("statut");

        Commande commande = new Commande();
        commande.setIdCommande(id);
        commande.setStatut(statut);

        commandeDAO.update(commande);
        response.sendRedirect(request.getContextPath() + "/commandes?msg=updated");
    }

    private void deleteCommande(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        commandeDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/commandes?msg=deleted");
    }
}
