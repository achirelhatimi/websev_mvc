package ma.fstt.web;

import ma.fstt.dao.ClientDAO;
import ma.fstt.entities.Client;
import ma.fstt.service.ClientService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClientServlet", urlPatterns = {"/clients"})
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ClientService clientDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        if (clientDAO == null) {
            clientDAO = new ClientDAO();
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
                deleteClient(request, response);
                break;
            case "list":
            default:
                listClients(request, response);
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
                insertClient(request, response);
                break;
            case "update":
                updateClient(request, response);
                break;
            default:
                listClients(request, response);
                break;
        }
    }

    private void listClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Client> clients = clientDAO.getAll();
        request.setAttribute("clients", clients);
        request.getRequestDispatcher("/WEB-INF/views/clients/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/clients/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Client existingClient = clientDAO.getOne(id);
        request.setAttribute("client", existingClient);
        request.getRequestDispatcher("/WEB-INF/views/clients/form.jsp").forward(request, response);
    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String adresse = request.getParameter("adresse");

        Client client = new Client(nom, prenom, email, telephone, adresse);
        clientDAO.create(client);
        response.sendRedirect(request.getContextPath() + "/clients?msg=created");
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String adresse = request.getParameter("adresse");

        Client client = new Client(id, nom, prenom, email, telephone, adresse);
        clientDAO.update(client);
        response.sendRedirect(request.getContextPath() + "/clients?msg=updated");
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        clientDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/clients?msg=deleted");
    }
}
