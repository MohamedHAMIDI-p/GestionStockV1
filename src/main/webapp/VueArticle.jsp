<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Détails article</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page page-etroite">
	<div class="card">

		<div class="card-header">
			<h1>Détails de l'article</h1>
		</div>

		<c:choose>
			<c:when test="${not empty article}">
				<table class="table-detail">
					<tr>
						<th>Code article</th>
						<td><c:out value="${article.codeArticle}" /></td>
					</tr>
					<tr>
						<th>Désignation</th>
						<td><c:out value="${article.designation}" /></td>
					</tr>
										<tr>
						<th>Stock Actuel</th>
						<td><c:out value="${article.stockActuel}" /></td>
					</tr>
					<tr>
						<th>Prix d'achat</th>
						<td><fmt:formatNumber value="${article.prixAchatArticle}" minFractionDigits="2" maxFractionDigits="2" /></td>
					</tr>
					<tr>
						<th>Prix de vente</th>
						<td><fmt:formatNumber value="${article.prixVenteArticle}" minFractionDigits="2" maxFractionDigits="2" /></td>
					</tr>
					<tr>
						<th>Prix de revient</th>
						<td><fmt:formatNumber value="${article.prixRevient}" minFractionDigits="2" maxFractionDigits="2" /></td>
					</tr>
					<tr>
						<th>TVA</th>
						<td><c:out value="${article.tvaArticle}" /> %</td>
					</tr>
						<th>Nom Categorie</th>
						<td><c:out value="${article.nomCategory}"></c:out></td>
					</tr>
					<tr>
						<th>Nom Fournisseur</th>
						<td><c:out value="${article.nomFournisseur}"></c:out></td>
					</tr>
					<tr>
						<th>Cnuf Fournisseur</th>
						<td><c:out value="${article.cnufFournisseur}"></c:out></td>
					</tr>
					<tr>
						<th>Date création</th>
						<td><fmt:formatDate value="${article.dateCreation}" pattern="dd/MM/yyyy HH:mm" /></td>
					</tr>
					<tr>
						<th>Date modification</th>
						<td><fmt:formatDate value="${article.dateModification}" pattern="dd/MM/yyyy HH:mm" /></td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<p class="message-erreur"><c:out value="${erreur}" /></p>
			</c:otherwise>
		</c:choose>

		<div class="actions">
			<a class="retour" href="${pageContext.request.contextPath}/articles">Retour à la liste</a>
		</div>

	</div>
</div>
</body>
</html>