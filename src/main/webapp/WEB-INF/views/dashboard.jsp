<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Tableau de bord" />
    <jsp:param name="active" value="home" />
</jsp:include>

<div class="row mb-4">
    <div class="col-12">
        <h2 class="fw-bold mb-1">Tableau de Bord</h2>
        <p class="text-muted">Système de gestion des commandes multi-clients (Architecture MVC 1)</p>
    </div>
</div>

<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card bg-primary text-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="text-uppercase mb-1 opacity-75">Clients</h6>
                    <h2 class="mb-0 fw-bold"><c:out value="${totalClients}"/></h2>
                </div>
                <i class="bi bi-people fs-1 opacity-50"></i>
            </div>
            <a href="<c:url value='/clients'/>" class="text-white text-decoration-none mt-3 small">
                Consulter l'espace client <i class="bi bi-arrow-right"></i>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success text-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="text-uppercase mb-1 opacity-75">Produits</h6>
                    <h2 class="mb-0 fw-bold"><c:out value="${totalProduits}"/></h2>
                </div>
                <i class="bi bi-box fs-1 opacity-50"></i>
            </div>
            <a href="<c:url value='/produits'/>" class="text-white text-decoration-none mt-3 small">
                Consulter l'espace produit <i class="bi bi-arrow-right"></i>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning text-dark p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="text-uppercase mb-1 opacity-75">Commandes</h6>
                    <h2 class="mb-0 fw-bold"><c:out value="${totalCommandes}"/></h2>
                </div>
                <i class="bi bi-cart-check fs-1 opacity-50"></i>
            </div>
            <a href="<c:url value='/commandes'/>" class="text-dark text-decoration-none mt-3 small">
                Consulter l'espace commande <i class="bi bi-arrow-right"></i>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-dark text-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="text-uppercase mb-1 opacity-75">Chiffre d'affaires</h6>
                    <h2 class="mb-0 fw-bold">
                        <fmt:formatNumber value="${chiffreAffaires}" type="currency" currencySymbol="DH"/>
                    </h2>
                </div>
                <i class="bi bi-cash-coin fs-1 opacity-50"></i>
            </div>
            <span class="mt-3 small opacity-75">Total des ventes enregistrées</span>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center py-3">
        <span class="fs-5">Dernières Commandes</span>
        <a href="<c:url value='/commandes?action=new'/>" class="btn btn-sm btn-primary">
            <i class="bi bi-plus-circle me-1"></i>Nouvelle Commande
        </a>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>N° Commande</th>
                        <th>Date</th>
                        <th>Client</th>
                        <th>Statut</th>
                        <th>Total</th>
                        <th class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty recentCommandes}">
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    Aucune commande enregistrée pour le moment.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${recentCommandes}" var="cmd">
                                <tr>
                                    <td><strong>#<c:out value="${cmd.idCommande}"/></strong></td>
                                    <td><fmt:formatDate value="${cmd.dateCommande}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td><c:out value="${cmd.client.nomComplet}"/></td>
                                    <td>
                                        <span class="badge ${cmd.statut == 'LIVREE' ? 'bg-success' : (cmd.statut == 'ANNULEE' ? 'bg-danger' : 'bg-warning text-dark')}">
                                            <c:out value="${cmd.statut}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <strong><fmt:formatNumber value="${cmd.total}" type="currency" currencySymbol="DH"/></strong>
                                    </td>
                                    <td class="text-end">
                                        <a href="<c:url value='/commandes?action=details&id=${cmd.idCommande}'/>" class="btn btn-sm btn-outline-info">
                                            <i class="bi bi-eye"></i> Détails
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
