<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Modifier Commande" />
    <jsp:param name="active" value="commandes" />
</jsp:include>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header py-3">
                <span class="fs-5">Modifier le statut de la commande #<c:out value="${commande.idCommande}"/></span>
            </div>
            <div class="card-body p-4">
                <form action="<c:url value='/commandes'/>" method="post">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="id" value="${commande.idCommande}" />

                    <div class="mb-3">
                        <label class="form-label text-muted">Client</label>
                        <input type="text" class="form-control" value="${commande.client.nomComplet}" readonly />
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Statut de la commande</label>
                        <select name="statut" class="form-select">
                            <option value="EN_COURS" ${commande.statut == 'EN_COURS' ? 'selected' : ''}>EN_COURS</option>
                            <option value="EXPEDIEE" ${commande.statut == 'EXPEDIEE' ? 'selected' : ''}>EXPEDIEE</option>
                            <option value="LIVREE" ${commande.statut == 'LIVREE' ? 'selected' : ''}>LIVREE</option>
                            <option value="ANNULEE" ${commande.statut == 'ANNULEE' ? 'selected' : ''}>ANNULEE</option>
                        </select>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="<c:url value='/commandes'/>" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left me-1"></i>Retour
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-circle me-1"></i>Mettre à jour
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
