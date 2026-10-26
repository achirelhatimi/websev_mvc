package ma.fstt.web;

import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProduitServlet", urlPatterns = {"/produits"})
public class ProduitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ProduitService produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
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
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteProduit(request, response);
                break;
            case "list":
            default:
                listProduits(request, response);
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
                insertProduit(request, response);
                break;
            case "update":
                updateProduit(request, response);
                break;
            default:
                listProduits(request, response);
                break;
        }
    }

    private void listProduits(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produit> produits = produitDAO.getAll();
        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/produits/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/produits/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Produit existingProduit = produitDAO.getOne(id);
        request.setAttribute("produit", existingProduit);
        request.getRequestDispatcher("/WEB-INF/views/produits/form.jsp").forward(request, response);
    }

    private void insertProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Produit produit = new Produit(nom, description, prix, stock);
        produitDAO.create(produit);
        response.sendRedirect(request.getContextPath() + "/produits?msg=created");
    }

    private void updateProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Produit produit = new Produit(id, nom, description, prix, stock);
        produitDAO.update(produit);
        response.sendRedirect(request.getContextPath() + "/produits?msg=updated");
    }

    private void deleteProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        produitDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/produits?msg=deleted");
    }
}
