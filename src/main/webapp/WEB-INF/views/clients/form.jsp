<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${client != null ? 'Modifier Client' : 'Ajouter Client'}" />
    <jsp:param name="active" value="clients" />
</jsp:include>

<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card">
            <div class="card-header py-3">
                <span class="fs-5">${client != null ? 'Modifier les coordonnées du client' : 'Créer un nouveau client'}</span>
            </div>
            <div class="card-body p-4">
                <form action="<c:url value='/clients'/>" method="post">
                    <input type="hidden" name="action" value="${client != null ? 'update' : 'insert'}" />
                    <c:if test="${client != null}">
                        <input type="hidden" name="id" value="${client.idClient}" />
                    </c:if>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Nom</label>
                            <input type="text" name="nom" class="form-control" value="${client != null ? client.nom : ''}" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Prénom</label>
                            <input type="text" name="prenom" class="form-control" value="${client != null ? client.prenom : ''}" required />
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-semibold">Adresse Email</label>
                            <input type="email" name="email" class="form-control" value="${client != null ? client.email : ''}" required />
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-semibold">Téléphone</label>
                            <input type="text" name="telephone" class="form-control" value="${client != null ? client.telephone : ''}" />
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-semibold">Adresse postale</label>
                            <textarea name="adresse" class="form-control" rows="3">${client != null ? client.adresse : ''}</textarea>
                        </div>
                        <div class="col-12 d-flex justify-content-between mt-4">
                            <a href="<c:url value='/clients'/>" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left me-1"></i>Retour
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle me-1"></i>${client != null ? 'Mettre à jour' : 'Enregistrer'}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
