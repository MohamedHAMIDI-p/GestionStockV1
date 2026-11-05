<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Liste des articles</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page">
	<div class="card">

		<div class="card-header">
			<h1>Liste des articles</h1>
			<span class="compteur">
				<c:out value="${totalArticles}" /> article(s) &middot;
				page <c:out value="${pageCourante}" /> / <c:out value="${nbPages}" />
			</span>
		</div>

		<c:choose>
			<c:when test="${empty articles}">
				<p>Aucun article.</p>
			</c:when>
			<c:otherwise>

				<div class="table-wrapper">
					<table class="table-liste">
						<thead>
							<tr>
								<th>Code</th>
								<th>Désignation</th>
								<th>Catégorie</th>
								<th>Fournisseur</th>
								<th class="num">Prix de vente</th>
								<th>TVA </th>
								<th>Stock Actuel</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${articles}" var="a">
								<c:url var="urlDetail" value="/article">
									<c:param name="codeArticle" value="${a.codeArticle}" />
								</c:url>
								<tr>
									<td><c:out value="${a.codeArticle}" /></td>
									<td><c:out value="${a.designation}" /></td>
									<td><c:out value="${a.nomCategory}" /></td>
									<td><c:out value="${a.nomFournisseur}" /></td>
									<td class="num"><fmt:formatNumber value="${a.prixVente}" minFractionDigits="2" maxFractionDigits="2" /></td>
									<td><c:out value="${a.tvaArt.taux}" />%</td>
									<td><c:out value="${a.stockActuel}" /></td>
									<td><a href="${urlDetail}">Détails</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<c:if test="${nbPages > 1}">
					<div class="pagination">

						<c:choose>
							<c:when test="${pageCourante > 1}">
								<c:url var="urlPrec" value="/articles">
									<c:param name="page" value="${pageCourante - 1}" />
								</c:url>
								<a href="${urlPrec}">&laquo; Précédent</a>
							</c:when>
							<c:otherwise>
								<span class="desactive">&laquo; Précédent</span>
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${nbPages}" var="p">
							<c:choose>
								<c:when test="${p == pageCourante}">
									<span class="actif"><c:out value="${p}" /></span>
								</c:when>
								<c:otherwise>
									<c:url var="urlPage" value="/articles">
										<c:param name="page" value="${p}" />
									</c:url>
									<a href="${urlPage}"><c:out value="${p}" /></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:choose>
							<c:when test="${pageCourante < nbPages}">
								<c:url var="urlSuiv" value="/articles">
									<c:param name="page" value="${pageCourante + 1}" />
								</c:url>
								<a href="${urlSuiv}">Suivant &raquo;</a>
							</c:when>
							<c:otherwise>
								<span class="desactive">Suivant &raquo;</span>
							</c:otherwise>
						</c:choose>

					</div>
				</c:if>

			</c:otherwise>
		</c:choose>

	</div>
</div>
</body>
</html>