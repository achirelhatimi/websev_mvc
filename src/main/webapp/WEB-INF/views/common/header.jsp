<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title != null ? param.title : 'Gestion des Commandes'} - FSTT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .navbar-brand {
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        }
        .card-header {
            background-color: transparent;
            border-bottom: 1px solid rgba(0, 0, 0, 0.08);
            font-weight: 600;
        }
        .table > :not(caption) > * > * {
            padding: 0.85rem 1rem;
        }
        .main-content {
            flex: 1;
        }
        footer {
            margin-top: auto;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/'/>">
            <i class="bi bi-box-seam me-2 text-warning"></i>GestionCommandes
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'home' ? 'active' : ''}" href="<c:url value='/home'/>">
                        <i class="bi bi-speedometer2 me-1"></i>Tableau de bord
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'clients' ? 'active' : ''}" href="<c:url value='/clients'/>">
                        <i class="bi bi-people me-1"></i>Espace Clients
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'produits' ? 'active' : ''}" href="<c:url value='/produits'/>">
                        <i class="bi bi-box me-1"></i>Espace Produits
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'commandes' ? 'active' : ''}" href="<c:url value='/commandes'/>">
                        <i class="bi bi-cart3 me-1"></i>Espace Commandes
                    </a>
                </li>
            </ul>
            <span class="navbar-text text-light">
                <small><i class="bi bi-mortarboard me-1"></i>MVC 1 - JEE</small>
            </span>
        </div>
    </div>
</nav>
<div class="container main-content mb-5">
    <c:if test="${not empty param.msg}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <c:choose>
                <c:when test="${param.msg == 'created'}">Opération réalisée avec succès (enregistrement créé).</c:when>
                <c:when test="${param.msg == 'updated'}">Enregistrement mis à jour avec succès.</c:when>
                <c:when test="${param.msg == 'deleted'}">Enregistrement supprimé avec succès.</c:when>
                <c:otherwise><c:out value="${param.msg}"/></c:otherwise>
            </c:choose>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
