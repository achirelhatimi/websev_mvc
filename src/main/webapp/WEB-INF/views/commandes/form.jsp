<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Nouvelle Commande" />
    <jsp:param name="active" value="commandes" />
</jsp:include>

<div class="row justify-content-center">
    <div class="col-lg-10">
        <div class="card">
            <div class="card-header py-3">
                <span class="fs-5">Créer une nouvelle commande</span>
            </div>
            <div class="card-body p-4">
                <form action="<c:url value='/commandes'/>" method="post" id="commandeForm">
                    <input type="hidden" name="action" value="insert" />

                    <div class="row g-3 mb-4">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Client</label>
                            <select name="clientId" class="form-select" required>
                                <option value="">-- Sélectionner un client --</option>
                                <c:forEach items="${clients}" var="cli">
                                    <option value="${cli.idClient}">
                                        <c:out value="${cli.nomComplet}"/> (<c:out value="${cli.email}"/>)
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Statut initial</label>
                            <select name="statut" class="form-select">
                                <option value="EN_COURS" selected>EN_COURS</option>
                                <option value="EXPEDIEE">EXPEDIEE</option>
                                <option value="LIVREE">LIVREE</option>
                                <option value="ANNULEE">ANNULEE</option>
                            </select>
                        </div>
                    </div>

                    <h5 class="fw-bold mb-3">Lignes de la commande</h5>
                    <div id="lignesContainer">
                        <div class="row g-2 align-items-center mb-3 ligne-item">
                            <div class="col-md-7">
                                <label class="form-label small fw-semibold">Produit</label>
                                <select name="produitId[]" class="form-select produit-select" required>
                                    <option value="">-- Choisir un produit --</option>
                                    <c:forEach items="${produits}" var="prod">
                                        <option value="${prod.idProduit}" data-prix="${prod.prix}" data-stock="${prod.stock}">
                                            <c:out value="${prod.nom}"/> - <c:out value="${prod.prix}"/> DH (Stock: <c:out value="${prod.stock}"/>)
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-semibold">Quantité</label>
                                <input type="number" name="quantite[]" class="form-control" min="1" value="1" required />
                            </div>
                            <div class="col-md-2 d-flex align-items-end">
                                <button type="button" class="btn btn-outline-danger w-100 btn-remove-ligne" style="margin-top: 1.8rem;">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="mb-4">
                        <button type="button" class="btn btn-sm btn-outline-primary" id="btnAddLigne">
                            <i class="bi bi-plus-lg me-1"></i>Ajouter un autre article
                        </button>
                    </div>

                    <div class="d-flex justify-content-between pt-3 border-top">
                        <a href="<c:url value='/commandes'/>" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left me-1"></i>Retour
                        </a>
                        <button type="submit" class="btn btn-warning text-dark fw-semibold">
                            <i class="bi bi-check-circle me-1"></i>Valider la commande
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('btnAddLigne').addEventListener('click', function() {
        const container = document.getElementById('lignesContainer');
        const firstItem = container.querySelector('.ligne-item');
        const clone = firstItem.cloneNode(true);
        clone.querySelector('select').value = '';
        clone.querySelector('input[type="number"]').value = '1';
        container.appendChild(clone);
        attachRemoveEvents();
    });

    function attachRemoveEvents() {
        document.querySelectorAll('.btn-remove-ligne').forEach(function(btn) {
            btn.onclick = function() {
                const items = document.querySelectorAll('.ligne-item');
                if (items.length > 1) {
                    this.closest('.ligne-item').remove();
                } else {
                    alert('Une commande doit comporter au moins un article.');
                }
            };
        });
    }
    attachRemoveEvents();
</script>

<jsp:include page="../common/footer.jsp" />
