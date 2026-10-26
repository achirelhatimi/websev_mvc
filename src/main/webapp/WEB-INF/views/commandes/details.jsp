<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Détails de la Commande" />
    <jsp:param name="active" value="commandes" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Commande #<c:out value="${commande.idCommande}"/></h2>
        <p class="text-muted mb-0">
            Passée le <fmt:formatDate value="${commande.dateCommande}" pattern="dd/MM/yyyy à HH:mm"/>
        </p>
    </div>
    <div>
        <a href="<c:url value='/commandes'/>" class="btn btn-outline-secondary me-2">
            <i class="bi bi-arrow-left me-1"></i>Retour à la liste
        </a>
        <a href="<c:url value='/commandes?action=edit&id=${commande.idCommande}'/>" class="btn btn-warning text-dark">
            <i class="bi bi-pencil me-1"></i>Modifier le statut
        </a>
    </div>
</div>

<div class="row g-4 mb-4">
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header py-3">
                <span class="fs-6">Informations Client</span>
            </div>
            <div class="card-body">
                <table class="table table-borderless mb-0">
                    <tr>
                        <th class="text-muted" style="width: 35%;">Nom complet :</th>
                        <td><strong><c:out value="${commande.client.nomComplet}"/></strong></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Email :</th>
                        <td><c:out value="${commande.client.email}"/></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Téléphone :</th>
                        <td><c:out value="${commande.client.telephone}"/></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Adresse de livraison :</th>
                        <td><c:out value="${commande.client.adresse}"/></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header py-3">
                <span class="fs-6">État de la commande</span>
            </div>
            <div class="card-body d-flex flex-column justify-content-center">
                <div class="mb-3">
                    <span class="text-muted d-block mb-1">Statut actuel :</span>
                    <span class="badge fs-6 ${commande.statut == 'LIVREE' ? 'bg-success' : (commande.statut == 'ANNULEE' ? 'bg-danger' : 'bg-warning text-dark')}">
                        <c:out value="${commande.statut}"/>
                    </span>
                </div>
                <div>
                    <span class="text-muted d-block mb-1">Montant total TTC :</span>
                    <h3 class="fw-bold text-primary mb-0">
                        <fmt:formatNumber value="${commande.total}" type="currency" currencySymbol="DH"/>
                    </h3>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header py-3">
        <span class="fs-5">Articles commandés</span>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>N° Ligne</th>
                        <th>Désignation du produit</th>
                        <th>Description</th>
                        <th class="text-end">Prix Unitaire</th>
                        <th class="text-center">Quantité</th>
                        <th class="text-end">Sous-Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty commande.lignes}">
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    Aucun article dans cette commande.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${commande.lignes}" var="ligne" varStatus="status">
                                <tr>
                                    <td><c:out value="${status.count}"/></td>
                                    <td><strong><c:out value="${ligne.produit.nom}"/></strong></td>
                                    <td><c:out value="${ligne.produit.description}"/></td>
                                    <td class="text-end">
                                        <fmt:formatNumber value="${ligne.prixUnitaire}" type="currency" currencySymbol="DH"/>
                                    </td>
                                    <td class="text-center"><c:out value="${ligne.quantite}"/></td>
                                    <td class="text-end">
                                        <strong><fmt:formatNumber value="${ligne.sousTotal}" type="currency" currencySymbol="DH"/></strong>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
                <tfoot class="table-light">
                    <tr>
                        <th colspan="5" class="text-end fs-5">Total :</th>
                        <th class="text-end fs-5 text-primary">
                            <fmt:formatNumber value="${commande.total}" type="currency" currencySymbol="DH"/>
                        </th>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
