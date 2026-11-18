<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Liste des articles</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/app.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/articles.css">

</head>

<body>

<div class="app-container">

    <%@ include file="/WEB-INF/includes/sidebar.jspf" %>

    <main class="main-content">

        <%@ include file="/WEB-INF/includes/topbar.jspf" %>

        <section class="dashboard-content">

            <div class="section-heading">
                <div>
                    <h2>Gestion des articles</h2>
                    <span>Consultez et gérez votre catalogue d'articles</span>
                </div>

                <a href="${pageContext.request.contextPath}/article"
                   class="btn-primary">
                    + Nouvel article
                </a>
            </div>

            <div class="panel">

                <div class="panel-header">
                    <h3>Liste des articles</h3>

                    <span class="panel-badge">
                        ${totalArticles} article(s)
                    </span>
                </div>

                <!-- Conserver ici ton tableau JSTL existant -->

                <div class="table-container">

                    <table class="table-liste">
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Désignation</th>
                                <th>Catégorie</th>
                                <th>Fournisseur</th>
                                <th>Prix de vente</th>
                                <th>TVA</th>
                                <th>Stock actuel</th>
                                <th>Détails</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach items="${articles}" var="a">
                                <tr>
                                    <td>
                                        <c:out value="${a.codeArticle}"/>
                                    </td>
                                    <td>
                                        <c:out value="${a.designation}"/>
                                    </td>
                                    <td>
                                        <c:out value="${a.nomCategory}"/>
                                    </td>
                                    <td>
                                        <c:out value="${a.nomFournisseur}"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber
                                            value="${a.prixVente}"
                                            minFractionDigits="2"
                                            maxFractionDigits="2"/>
                                    </td>
                                    <td>
                                        <c:out value="${a.tvaArt.taux}"/>%
                                    </td>
                                    <td>
                                        <c:out value="${a.stockActuel}"/>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/article?codeArticle=${a.codeArticle}">
                                            Détails
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>

                <!-- Tu peux replacer ici ton code de pagination actuel -->

            </div>

        </section>

    </main>

</div>

<script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>
</html>