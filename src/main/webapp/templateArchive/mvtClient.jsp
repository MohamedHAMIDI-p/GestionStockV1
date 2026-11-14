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
	<style>
	[hidden] { display: none !important; }          /* fait disparaître un champ masqué de la grille */
	.aide { margin-top: 4px; font-size: 12px; color: #777; }
	</style>
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
<!-- formulaire saisie mouvement-->

<form class="formulaire" method="post" action="${pageContext.request.contextPath}/mouvements">
	<input type="hidden" name="codeArticle" value="<c:out value="${article.codeArticle}" />">

	<!-- ===== Ligne 1 : l'essentiel, toujours visible ===== -->
	<div>
		<label for="typeMvt">Type de mouvement</label>
		<select id="typeMvt" name="typeMvt" required>
			<option value="">— choisir —</option>
			<c:forEach items="${types}" var="t">
				<option value="${t}"
					data-client="${t.clientConcerne}"
					data-correction="${t.correction}"
					<c:if test="${typeChoisi == t}">selected</c:if>><c:out value="${t.libelle}" /></option>
			</c:forEach>
		</select>
	</div>

	<div>
		<label for="quantite">Quantité</label>
		<input id="quantite" type="number" name="quantite" min="1" step="1" required
			value="<c:out value="${param.quantite}" />">
	</div>

	<div>
		<label for="reference">Référence (bon, facture...)</label>
		<input id="reference" type="text" name="reference" maxlength="50"
			value="<c:out value="${param.reference}" />">
	</div>

	<!-- ===== 4e emplacement : un seul des deux blocs s'affiche selon le type ===== -->
	<div id="blocClient" hidden>
		<label for="idClient">Client</label>
		<select id="idClient" name="idClient">
			<option value="">— choisir —</option>
			<c:forEach items="${clients}" var="cl">
				<option value="${cl.id}" <c:if test="${clientChoisi == cl.id}">selected</c:if>>
					<c:out value="${cl.codeClient}" /> - <c:out value="${cl.nomClient}" />
				</option>
			</c:forEach>
		</select>
	</div>

	<div id="blocMotif" hidden>
		<label for="motif">Motif de la correction</label>
		<select id="motif" name="motif">
			<option value="">— choisir —</option>
			<c:forEach items="${motifs}" var="mo">
				<option value="${mo}" <c:if test="${motifChoisi == mo}">selected</c:if>><c:out value="${mo.libelle}" /></option>
			</c:forEach>
		</select>
	</div>

	<!-- ===== Ligne 2 : commentaire sur toute la largeur ===== -->
	<div class="large">
		<label for="commentaire">Commentaire</label>
		<textarea id="commentaire" name="commentaire" rows="2" maxlength="255"><c:out value="${param.commentaire}" /></textarea>
		<div class="aide" id="aideCommentaire">Obligatoire pour une correction (vol, casse...).</div>
	</div>

	<!-- ===== Ligne 3 : validation ===== -->
	<div class="large">
		<button type="submit" class="btn">Enregistrer le mouvement</button>
	</div>
</form>

<script>
	(function () {
		var type = document.getElementById('typeMvt');
		var blocClient = document.getElementById('blocClient');
		var blocMotif = document.getElementById('blocMotif');
		var client = document.getElementById('idClient');
		var motif = document.getElementById('motif');
		var commentaire = document.getElementById('commentaire');

		// Affiche le client pour une vente ou un retour client, le motif pour une correction.
		// Un champ n'est "required" que s'il est visible (sinon le formulaire serait bloqué sans qu'on sache pourquoi).
		// Le serveur refait tous les contrôles : ce script ne sert qu'au confort de saisie.
		function majFormulaire() {
			var option = type.options[type.selectedIndex];
			var avecClient = option.getAttribute('data-client') === 'true';
			var correction = option.getAttribute('data-correction') === 'true';

			blocClient.hidden = !avecClient;
			blocMotif.hidden = !correction;

			client.required = avecClient;
			motif.required = correction;
			commentaire.required = correction;

			if (!avecClient) { client.value = ''; }
			if (!correction) { motif.value = ''; }
		}

		type.addEventListener('change', majFormulaire);
		majFormulaire();     // à l'ouverture de la page, et après une erreur de saisie
	})();
</script>

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
