<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Mouvements de stock</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleM.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page">
	<div class="card">
		<!-- entete de page  -->
		<div class="card-header">
			<h1>Mouvements de stock</h1>
			<span class="compteur">
				<c:out value="${article.codeArticle}" /> &middot; <c:out value="${article.designation}" />
				 &middot; <c:out value="${article.cnufFournisseur}" />
				 &middot;<c:out value="${article.nomFournisseur}" />
				&middot; stock actuel : <strong><c:out value="${article.stockActuel}" /></strong>
			</span>
		</div>

		<c:if test="${ok}">
			<p class="message-ok">Mouvement enregistré.</p>
		</c:if>
		<c:if test="${not empty erreur}">
			<p class="message-erreur"><c:out value="${erreur}" /></p>
		</c:if>

		<!-- ===== Saisie mouvement Stock ===== -->
		<form class="formulaire" method="post" action="${pageContext.request.contextPath}/mouvements">
			<input type="hidden" name="codeArticle" value="<c:out value="${article.codeArticle}" />">

			<div>
				<label for="typeMvt">Type de mouvement</label>
				<select id="typeMvt" name="typeMvt" required>
					<option value="">— choisir —</option>
					<c:forEach items="${types}" var="t">
						<option value="${t}" <c:if test="${typeChoisi == t}">selected</c:if>><c:out value="${t.libelle}" /></option>
					</c:forEach>
				</select>
			</div>

			<div>
				<label for="quantite">Quantité</label>
				<input id="quantite" type="number" name="quantite" min="1" step="1" required
					value="<c:out value="${param.quantite}" />">
			</div>

			<div>
				<label for="motif">Motif (corrections uniquement)</label>
				<select id="motif" name="motif">
					<option value="">— aucun —</option>
					<c:forEach items="${motifs}" var="mo">
						<option value="${mo}" <c:if test="${motifChoisi == mo}">selected</c:if>><c:out value="${mo.libelle}" /></option>
					</c:forEach>
				</select>
			</div>

			<div>
				<label for="reference">Référence (bon, facture...)</label>
				<input id="reference" type="text" name="reference" maxlength="50"
					value="<c:out value="${param.reference}" />">
			</div>

			<div class="large">
				<label for="commentaire">Commentaire (obligatoire pour une correction)</label>
				<textarea id="commentaire" name="commentaire" rows="2" maxlength="255"><c:out value="${param.commentaire}" /></textarea>
			</div>

			<div class="large">
				<button type="submit" class="btn">Enregistrer le mouvement</button>
			</div>
		</form>
		<!-- ===== Fin Saisie mouvement Stock ===== -->
		
		<!-- ===== recuperation Liste Mouvement pour l'article ===== -->
		<c:choose>
			<c:when test="${empty mouvements}">
				<p>Aucun mouvement pour cet article.</p>
			</c:when>
			<c:otherwise>
				<div class="table-wrapper">
					<table class="table-liste">
						<thead>
							<tr>
								<th>Numero </th>
								<th>Date</th>
								<th>Type</th>
								<th class="num">Quantité</th>
								<th>Motif</th>
								<th>Fournisseur</th>
								<th>Référence</th>
								<th>Commentaire</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mouvements}" var="m">
								<tr>
									<td class="nowrap"><c:out value="${m.id}" /></td>
									<td class="nowrap"><fmt:formatDate value="${m.dateMvtStock}" pattern="dd/MM/yyyy HH:mm" /></td>
									<td class="nowrap"><c:out value="${m.typemvtStock.libelle}" /></td>
									<td class="num ${m.variation > 0 ? 'qte-plus' : 'qte-moins'}">
										<c:if test="${m.variation > 0}">+</c:if><c:out value="${m.variation}" />
									</td>
									<td><c:out value="${m.motif.libelle}" default="—" /></td>
									<td><c:out value="${m.nomFournisseur}" default="—" /></td>
									<td><c:out value="${m.reference}" default="—" /></td>
									<td><c:out value="${m.commentaire}" default="—" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>

		<div class="actions">
			<c:url var="urlArticle" value="/article">
				<c:param name="codeArticle" value="${article.codeArticle}" />
			</c:url>
			<a class="retour" href="${urlArticle}">Retour Article</a>
			<a class="retour" href="${pageContext.request.contextPath}/articles">Retour liste Article</a>
		</div>

	</div>
</div>
</body>
</html>
