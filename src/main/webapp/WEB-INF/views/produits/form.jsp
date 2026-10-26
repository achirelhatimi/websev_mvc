<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${produit != null ? 'Modifier Produit' : 'Ajouter Produit'}" />
    <jsp:param name="active" value="produits" />
</jsp:include>

<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card">
            <div class="card-header py-3">
                <span class="fs-5">${produit != null ? 'Modifier le produit' : 'Ajouter un nouveau produit'}</span>
            </div>
            <div class="card-body p-4">
                <form action="<c:url value='/produits'/>" method="post">
                    <input type="hidden" name="action" value="${produit != null ? 'update' : 'insert'}" />
                    <c:if test="${produit != null}">
                        <input type="hidden" name="id" value="${produit.idProduit}" />
                    </c:if>

                    <div class="row g-3">
                        <div class="col-12">
                            <label class="form-label fw-semibold">Nom du produit</label>
                            <input type="text" name="nom" class="form-control" value="${produit != null ? produit.nom : ''}" required />
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-semibold">Description</label>
                            <textarea name="description" class="form-control" rows="3">${produit != null ? produit.description : ''}</textarea>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Prix unitaire (DH)</label>
                            <input type="number" step="0.01" min="0" name="prix" class="form-control" value="${produit != null ? produit.prix : ''}" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Quantité en stock</label>
                            <input type="number" min="0" name="stock" class="form-control" value="${produit != null ? produit.stock : ''}" required />
                        </div>
                        <div class="col-12 d-flex justify-content-between mt-4">
                            <a href="<c:url value='/produits'/>" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left me-1"></i>Retour
                            </a>
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-check-circle me-1"></i>${produit != null ? 'Mettre à jour' : 'Enregistrer'}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
