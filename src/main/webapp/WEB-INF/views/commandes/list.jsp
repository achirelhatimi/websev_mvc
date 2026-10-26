<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Espace Commandes" />
    <jsp:param name="active" value="commandes" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Espace Commandes</h2>
        <p class="text-muted mb-0">Gestion des commandes et suivi des livraisons</p>
    </div>
    <a href="<c:url value='/commandes?action=new'/>" class="btn btn-warning text-dark fw-semibold">
        <i class="bi bi-cart-plus me-1"></i>Passer une commande
    </a>
</div>

<div class="card">
    <div class="card-header py-3">
        <span class="fs-5">Historique des Commandes</span>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>N°</th>
                        <th>Date de commande</th>
                        <th>Client</th>
                        <th>Statut</th>
                        <th>Articles</th>
                        <th>Total</th>
                        <th class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty commandes}">
                            <tr>
                                <td colspan="7" class="text-center py-4 text-muted">
                                    Aucune commande enregistrée.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${commandes}" var="cmd">
                                <tr>
                                    <td><strong>#<c:out value="${cmd.idCommande}"/></strong></td>
                                    <td><fmt:formatDate value="${cmd.dateCommande}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>
                                        <strong><c:out value="${cmd.client.nomComplet}"/></strong><br>
                                        <small class="text-muted"><c:out value="${cmd.client.email}"/></small>
                                    </td>
                                    <td>
                                        <span class="badge ${cmd.statut == 'LIVREE' ? 'bg-success' : (cmd.statut == 'ANNULEE' ? 'bg-danger' : 'bg-warning text-dark')}">
                                            <c:out value="${cmd.statut}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge bg-secondary rounded-pill">
                                            <c:out value="${cmd.lignes != null ? cmd.lignes.size() : 0}"/> ligne(s)
                                        </span>
                                    </td>
                                    <td>
                                        <strong class="text-primary">
                                            <fmt:formatNumber value="${cmd.total}" type="currency" currencySymbol="DH"/>
                                        </strong>
                                    </td>
                                    <td class="text-end">
                                        <a href="<c:url value='/commandes?action=details&id=${cmd.idCommande}'/>" class="btn btn-sm btn-outline-info me-1" title="Détails">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                        <a href="<c:url value='/commandes?action=edit&id=${cmd.idCommande}'/>" class="btn btn-sm btn-outline-warning me-1" title="Modifier statut">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="<c:url value='/commandes?action=delete&id=${cmd.idCommande}'/>" class="btn btn-sm btn-outline-danger" title="Supprimer" onclick="return confirm('Confirmer la suppression de cette commande ?');">
                                            <i class="bi bi-trash"></i>
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

<jsp:include page="../common/footer.jsp" />
