<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Espace Produits" />
    <jsp:param name="active" value="produits" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Espace Produits</h2>
        <p class="text-muted mb-0">Gestion du catalogue des articles et des stocks</p>
    </div>
    <a href="<c:url value='/produits?action=new'/>" class="btn btn-success">
        <i class="bi bi-plus-circle me-1"></i>Nouveau Produit
    </a>
</div>

<div class="card">
    <div class="card-header py-3">
        <span class="fs-5">Catalogue des Produits</span>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Désignation</th>
                        <th>Description</th>
                        <th>Prix Unitaire</th>
                        <th>Stock Disponible</th>
                        <th class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty produits}">
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    Aucun produit disponible dans le catalogue.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${produits}" var="prod">
                                <tr>
                                    <td><strong>#<c:out value="${prod.idProduit}"/></strong></td>
                                    <td><strong class="text-dark"><c:out value="${prod.nom}"/></strong></td>
                                    <td><c:out value="${prod.description}"/></td>
                                    <td>
                                        <fmt:formatNumber value="${prod.prix}" type="currency" currencySymbol="DH"/>
                                    </td>
                                    <td>
                                        <span class="badge ${prod.stock > 10 ? 'bg-success' : (prod.stock > 0 ? 'bg-warning text-dark' : 'bg-danger')}">
                                            <c:out value="${prod.stock}"/> en stock
                                        </span>
                                    </td>
                                    <td class="text-end">
                                        <a href="<c:url value='/produits?action=edit&id=${prod.idProduit}'/>" class="btn btn-sm btn-outline-warning me-1">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="<c:url value='/produits?action=delete&id=${prod.idProduit}'/>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Confirmer la suppression de ce produit ?');">
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
