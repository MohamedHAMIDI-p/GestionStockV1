
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Liste Mouvements de Reception</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/app.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mouvements.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/articles.css">
</head>

<body>

	<div class="app-container">

		<%@ include file="/WEB-INF/includes/sidebar.jspf"%>

		<main class="main-content">

			<%@ include file="/WEB-INF/includes/topbar.jspf"%>

			<section class="dashboard-content">

				<div class="panel">

					<div class="section-heading">
						<div>
							<h3>Recherche mouvement Reception</h3>
							<p>Renseignez les informations du mouvement à Rechercher.</p>
						</div>
					</div>
					<form id="formRechercheMvt" class="formulaire-recherche-mvt"
						method="get"
						action="${pageContext.request.contextPath}/reception">

						<div class="form-group">
							<label for="codeArticle">Code article</label> <input type="text"
								id="codeArticle" name="codeArticle"
								value="<c:out value='${param.codeArticle}'/>"
								placeholder="Ex : ART001">
						</div>

						<div class="form-group">
							<label for="idMouvement">N° mouvement</label> <input
								type="number" id="idMouvement" name="idMouvement" min="1"
								value="<c:out value='${param.idMouvement}'/>"
								placeholder="Ex : 125">
						</div>

						<div class="form-group">
							<label for="cnuf">Code fournisseur (CNUF)</label> <input
								type="text" id="cnuf" name="cnuf"
								value="<c:out value='${param.cnuf}'/>"
								placeholder="Code fournisseur">
						</div>

						<div class="form-group">
							<label for="">Nom du Article/Fournisseur</label> <input
								type="text" id="libelle_A_F" name="libelle_A_F"
								value="<c:out value='${param.designation}'/>">
						</div>
						<div class="form-group">
							<label for="dateMouvement">Date du mouvement</label> <input
								type="date" id="dateMouvement" name="dateMouvement"
								value="<c:out value='${param.dateMouvement}'/>">
						</div>

						<div class="form-actions recherche-actions">
							<button type="submit" class="btn-primary-mvt">
								Rechercher</button>

							<a href="${pageContext.request.contextPath}/reception"
								class="btn-secondary-mvt"> Réinitialiser </a>
						</div>

					</form>

					<!-- Historique -->
					<div class="section-heading history-heading">
						<div>
							<h3>Historique des Receptions</h3>
							<p>Consultez les Receptions enregistrés.</p>
						</div>
					</div>
					<span class="compteur"> <c:out value="${totalreception}" />
						mouvement(s) &middot; Page <c:out value="${pageCourante}" /> / <c:out
							value="${nbPages}" />
					</span>

					<div class="table-wrapper">
						<table class="table-mouvements">
							<thead>
								<tr>
									<th>N° mouvement</th>
									<th>code Article</th>
									<th>Date</th>
									<th>Type</th>
									<th class="text-right">Quantité</th>
									<th>Code Fournisseur</th>
									<th>Fournisseur</th>
									<th>Référence</th>
									<th>Commentaire</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${reception}" var="m">
									<tr>
										<td><c:out value="${m.id}" /></td>
										<td class="nowrap"><c:out value="${m.codeArticle}" /></td>

										<td class="nowrap"><fmt:formatDate
												value="${m.dateMvtStock}" pattern="dd/MM/yyyy HH:mm" /></td>

										<td><span class="type-mvt"> <c:out
													value="${m.typemvtStock.libelle}" />
										</span></td>

										<td class="text-right nowrap"><span
											class="${m.variation > 0 ? 'qte-plus' : 'qte-moins'}">
												<c:if test="${m.variation > 0}">+</c:if>
												<c:out value="${m.variation}" />
										</span></td>
										<td><c:out value="${m.cnuf}" default="—" /></td>
										<td><c:out value="${m.nomFournisseur}" default="—" /></td>
										<td><c:out value="${m.reference}" default="—" /></td>

										<td class="comment-cell"><c:out value="${m.commentaire}"
												default="—" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${not empty erreurRecherche}">
							<div class="message-erreur">
								<c:out value="${erreurRecherche}" />
							</div>
						</c:if>
					</div>

					<c:if test="${nbPages > 1}">

						<div class="pagination">

							<%-- Page précédente --%>

							<c:choose>

								<c:when test="${pageCourante > 1}">

									<c:url var="urlPrec" value="/receptions">
										<c:param name="page" value="${pageCourante - 1}" />
										<c:param name="codeArticle" value="${param.codeArticle}" />
										<c:param name="idMouvement" value="${param.idMouvement}" />
										<c:param name="reference" value="${param.reference}" />
										<c:param name="cnuf" value="${param.cnuf}" />
									</c:url>

									<a href="${urlPrec}"> &laquo; Précédent </a>

								</c:when>

								<c:otherwise>
									<span class="desactive"> &laquo; Précédent </span>
								</c:otherwise>

							</c:choose>

							<%-- Numéros des pages --%>

							<c:forEach begin="1" end="${nbPages}" var="p">

								<c:choose>

									<c:when test="${p == pageCourante}">
										<span class="actif"> <c:out value="${p}" />
										</span>
									</c:when>

									<c:otherwise>

										<c:url var="urlPage" value="/receptions">
											<c:param name="page" value="${p}" />
											<c:param name="codeArticle" value="${param.codeArticle}" />
											<c:param name="idMouvement" value="${param.idMouvement}" />
											<c:param name="reference" value="${param.reference}" />
											<c:param name="cnuf" value="${param.cnuf}" />
										</c:url>

										<a href="${urlPage}"> <c:out value="${p}" />
										</a>

									</c:otherwise>

								</c:choose>

							</c:forEach>

							<%-- Page suivante --%>

							<c:choose>

								<c:when test="${pageCourante < nbPages}">

									<c:url var="urlSuiv" value="/receptions">
										<c:param name="page" value="${pageCourante + 1}" />
										<c:param name="codeArticle" value="${param.codeArticle}" />
										<c:param name="idMouvement" value="${param.idMouvement}" />
										<c:param name="reference" value="${param.reference}" />
										<c:param name="cnuf" value="${param.cnuf}" />
									</c:url>

									<a href="${urlSuiv}"> Suivant &raquo; </a>

								</c:when>

								<c:otherwise>
									<span class="desactive"> Suivant &raquo; </span>
								</c:otherwise>

							</c:choose>

						</div>

					</c:if>

				</div>

				<%@ include file="/WEB-INF/includes/footer.jspf"%>

			</section>
		</main>
	</div>
</body>
</html>
