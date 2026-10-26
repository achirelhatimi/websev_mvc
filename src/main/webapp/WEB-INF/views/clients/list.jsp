<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Espace Clients" />
    <jsp:param name="active" value="clients" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Espace Clients</h2>
        <p class="text-muted mb-0">Gestion complète des clients enregistrés</p>
    </div>
    <a href="<c:url value='/clients?action=new'/>" class="btn btn-primary">
        <i class="bi bi-person-plus me-1"></i>Nouveau Client
    </a>
</div>

<div class="card">
    <div class="card-header py-3">
        <span class="fs-5">Liste des Clients</span>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Nom & Prénom</th>
                        <th>Email</th>
                        <th>Téléphone</th>
                        <th>Adresse</th>
                        <th class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty clients}">
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    Aucun client trouvé dans la base de données.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${clients}" var="cli">
                                <tr>
                                    <td><strong>#<c:out value="${cli.idClient}"/></strong></td>
                                    <td><c:out value="${cli.nomComplet}"/></td>
                                    <td><c:out value="${cli.email}"/></td>
                                    <td><c:out value="${cli.telephone}"/></td>
                                    <td><c:out value="${cli.adresse}"/></td>
                                    <td class="text-end">
                                        <a href="<c:url value='/clients?action=edit&id=${cli.idClient}'/>" class="btn btn-sm btn-outline-warning me-1">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="<c:url value='/clients?action=delete&id=${cli.idClient}'/>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Confirmer la suppression de ce client ?');">
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
