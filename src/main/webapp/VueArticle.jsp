
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>GestionStock - Détails article</title>

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

            <div class="panel">

                <div class="panel-header">
                    <div>
                        <h3>Détails de l'article</h3>
                        <p>Consultation des informations de l'article</p>
                    </div>
                </div>

                <c:choose>

                    <c:when test="${not empty article}">

                        <div class="table-container">

                            <table class="table-liste table-detail">

                                <tbody>

                                    <tr>
                                        <th>Code article</th>
                                        <td>
                                            <c:out value="${article.codeArticle}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Désignation</th>
                                        <td>
                                            <c:out value="${article.designation}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Stock actuel</th>
                                        <td>
                                            <c:out value="${article.stockActuel}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Prix d'achat</th>
                                        <td>
                                            <fmt:formatNumber
                                                value="${article.prixAchatArticle}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Prix de vente</th>
                                        <td>
                                            <fmt:formatNumber
                                                value="${article.prixVenteArticle}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Prix de revient</th>
                                        <td>
                                            <fmt:formatNumber
                                                value="${article.prixRevient}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>TVA</th>
                                        <td>
                                            <c:out value="${article.tvaArticle}" /> %
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Catégorie</th>
                                        <td>
                                            <c:out value="${article.nomCategory}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Fournisseur</th>
                                        <td>
                                            <c:out value="${article.nomFournisseur}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Cnuf fournisseur</th>
                                        <td>
                                            <c:out value="${article.cnufFournisseur}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Date de création</th>
                                        <td>
                                            <fmt:formatDate
                                                value="${article.dateCreation}"
                                                pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                    </tr>

                                </tbody>

                            </table>

                        </div>

                        <div class="actions">

                            <a class="btn-primary"
                               href="${pageContext.request.contextPath}/articles">
                                Retour aux articles
                            </a>

                            <c:url var="urlMouvements" value="/mouvements">
                                <c:param name="codeArticle"
                                         value="${article.codeArticle}" />
                            </c:url>

                            <a class="btn-primary"
                               href="${urlMouvements}">
                                Mouvements
                            </a>

                            <%-- À adapter lorsque le servlet des commandes sera disponible --%>
                            <a class="btn-primary"
                               href="${pageContext.request.contextPath}/commandes">
                                Commandes
                            </a>

                        </div>

                    </c:when>

                    <c:otherwise>

                        <p class="message-erreur">
                            <c:out value="${erreur}" />
                        </p>

                        <div class="actions">
                            <a class="btn-primary"
                               href="${pageContext.request.contextPath}/articles">
                                Retour aux articles
                            </a>
                        </div>

                    </c:otherwise>

                </c:choose>

            </div>

            <%@ include file="/WEB-INF/includes/footer.jspf" %>

        </section>

    </main>

</div>
</body>
</html>
