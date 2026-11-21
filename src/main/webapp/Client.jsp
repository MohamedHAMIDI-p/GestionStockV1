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

    <title>GestionStock - Client</title>

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
                        <h3>Liste des Clients</h3>
                        <p>Consultation des Clients</p>
                    </div>

                    <span class="compteur">
                        <c:out value="${totalClients}" /> client(s)
                        &middot;
                        Page <c:out value="${pageCourante}" />
                        / <c:out value="${nbPages}" />
                    </span>

                </div>

                <c:choose>

                    <c:when test="${empty client}">
                        <p class="empty-row">Aucun article à afficher.</p>
                    </c:when>

                    <c:otherwise>

                        <div class="table-container">

                            <table class="table-liste">

                                <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th>Nom</th>
                                        <th>Prenom</th>
                                        <th>Telephone</th>
                                        <th>Adresse</th>
                                        <th>DAteCreation</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    <c:forEach items="${client}" var="c">

                                        <c:url var="urlDetail" value="/client">
                                            <c:param name="codeClient"
                                                     value="${c.codeClient}" />
                                        </c:url>

                                        <tr>
                                            <td>
                                                <c:out value="${c.codeClient}" />
                                            </td>

                                            <td>
                                                <c:out value="${c.nomClient}" />
                                            </td>

                                            <td>
                                                <c:out value="${c.prenomClient}" />
                                            </td>

                                            <td>
                                                <c:out value="${c.telephoneClient}" />
                                            </td>

                                            <td>
                                                <c:out value="${c.adresseClient}" />
                                            </td>
                                            <td>
                                                <c:out value="${c.dateCreation}" />
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

                                        <c:url var="urlPrec" value="/client">
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

                                            <c:url var="urlPage" value="/client">
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

                                        <c:url var="urlSuiv" value="/client">
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