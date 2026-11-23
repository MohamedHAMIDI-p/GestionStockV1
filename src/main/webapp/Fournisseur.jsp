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

    <title>GestionStock - Fournisseur</title>

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
                        <h3>Liste des Fournisseurs</h3>
                        <p>Consultation des Fournisseurs</p>
                    </div>

                    <span class="compteur">
                        <c:out value="${totalFournisseur}" /> fournisseur(s)
                        &middot;
                        Page <c:out value="${pageCourante}" />
                        / <c:out value="${nbPages}" />
                    </span>

                </div>

                <c:choose>

                    <c:when test="${empty fournisseur}">
                        <p class="empty-row">Aucun Fournisseur à afficher.</p>
                    </c:when>

                    <c:otherwise>

                        <div class="table-container">

                            <table class="table-liste">

                                <thead>
                                    <tr>
                                        <th>Code Fournisseur</th>
                                        <th>Nom Socienté</th>
                                        <th>Nom prenom</th>
                                        <th>telephone</th>
                                        <th>codePostal</th>
                                        <th>ville</th>
                                        <th>Mail</th>
                                        <th>Numero rib</th>
                                        <th>date Creation</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    <c:forEach items="${fournisseur}" var="f">

                                        <c:url var="urlDetail" value="/fournisseurs">
                                            <c:param name="cnuf"
                                                     value="${f.cnuf}" />
                                        </c:url>

                                        <tr>
                                            <td>
                                                <c:out value="${f.cnuf}" />
                                            </td>

                                            <td>
                                                <c:out value="${f.nomFournisseur}" />
                                            </td>

                                            <td>
                                                <c:out value="${f.prenomFournisseur}" />
                                            </td>

                                            <td>
                                                <c:out value="${f.telephoneFournisseur}" />
                                            </td>

                                            <td>
                                                <c:out value="${f.codePostal}" />
                                            </td>
                                            <td>
                                                <c:out value="${f.villeFournisseur}" />
                                            </td>
                                                                                        <td>
                                                <c:out value="${f.mailFournisseur}" />
                                            </td>
                                                                                        <td>
                                                <c:out value="${f.RIB}" />
                                            </td>
                                                                                        <td>
                                                <c:out value="${f.dateCreation}" />
                                            </td>
                                        </tr>

                                    </c:forEach>

                                </tbody>

                            </table>

                        </div>

                        <c:if test="${nbPages > 1}">

                            <div class="pagination">

                                <%-- Page précédente --%>

                                <c:choose>

                                    <c:when test="${pageCourante > 1}">

                                        <c:url var="urlPrec" value="/fournisseurs">
                                            <c:param name="page"
                                                     value="${pageCourante - 1}" />
                                        </c:url>

                                        <a href="${urlPrec}">
                                            &laquo; Précédent
                                        </a>

                                    </c:when>

                                    <c:otherwise>
                                        <span class="desactive">
                                            &laquo; Précédent
                                        </span>
                                    </c:otherwise>

                                </c:choose>

                                <%-- Numéros des pages --%>

                                <c:forEach begin="1" end="${nbPages}" var="p">

                                    <c:choose>

                                        <c:when test="${p == pageCourante}">
                                            <span class="actif">
                                                <c:out value="${p}" />
                                            </span>
                                        </c:when>

                                        <c:otherwise>

                                            <c:url var="urlPage" value="/fournisseurs">
                                                <c:param name="page" value="${p}" />
                                            </c:url>

                                            <a href="${urlPage}">
                                                <c:out value="${p}" />
                                            </a>

                                        </c:otherwise>

                                    </c:choose>

                                </c:forEach>

                                <%-- Page suivante --%>

                                <c:choose>

                                    <c:when test="${pageCourante < nbPages}">

                                        <c:url var="urlSuiv" value="/fournisseurs">
                                            <c:param name="page"
                                                     value="${pageCourante + 1}" />
                                        </c:url>

                                        <a href="${urlSuiv}">
                                            Suivant &raquo;
                                        </a>

                                    </c:when>

                                    <c:otherwise>
                                        <span class="desactive">
                                            Suivant &raquo;
                                        </span>
                                    </c:otherwise>

                                </c:choose>

                            </div>

                        </c:if>

                    </c:otherwise>

                </c:choose>

            </div>

            <%@ include file="/WEB-INF/includes/footer.jspf" %>

        </section>

    </main>

</div>
</body>
</html>