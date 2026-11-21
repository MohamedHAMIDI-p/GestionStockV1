
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- pour adapter format de date dans jsp  -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/app.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/accueil.css">

<title>GestionStock - Accueil</title>
</head>

<body>

	<div class="app-container">

		<!-- MENU LATERAL COMMUN -->
		<%@ include file="/WEB-INF/includes/sidebar.jspf"%>

		<!-- CONTENU PRINCIPAL -->
		<main class="main-content">

			<!-- TOPBAR COMMUN -->
			<%@ include file="/WEB-INF/includes/topbar.jspf"%>

			<section class="dashboard-content">

				<!-- BIENVENUE -->
				<div class="welcome-banner">
					<div>
						<h2>Bonjour, HAMIDI ABDELHADI 👋</h2>
						<p>Voici un aperçu de l'activité de votre stock.</p>
					</div>
					<div class="welcome-icon">📦</div>
				</div>

				<!-- STATISTIQUES -->
				<div class="section-heading">
					<h2>Vue générale</h2>
					<span>Indicateurs principaux</span>
				</div>

				<div class="stats-grid">

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon blue">▣</div>
							<a href="${pageContext.request.contextPath}/articles"> <span
								class="stat-label">Articles</span></a>
						</div>
						<h2>${empty totalArticles ? 0 : totalArticles}</h2>
						<p>Total des articles enregistrés</p>
					</div>

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon green">♧</div>
							<a href="${pageContext.request.contextPath}/client"> <span
								class="stat-label">Clients</span></a>
						</div>
						<h2>${empty totalClients ? 0 : totalClients}</h2>
						<p>Clients enregistrés</p>
					</div>

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon purple">♙</div>
							<span class="stat-label">Fournisseurs</span>
						</div>
						<h2>${empty totalFournisseurs ? 0 : totalFournisseurs}</h2>
						<p>Fournisseurs enregistrés</p>
					</div>

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon orange">▤</div>
							<span class="stat-label">Commandes en attente</span>
						</div>
						<h2>${empty commandesEnAttente ? 0 : commandesEnAttente}</h2>
						<p>Commandes à traiter</p>
					</div>

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon red">⚠</div>
							<span class="stat-label">Stock faible</span>
						</div>
						<h2>${empty articlesStockFaible ? 0 : articlesStockFaible}</h2>
						<p>Articles nécessitant un réapprovisionnement</p>
					</div>

					<div class="stat-card">
						<div class="stat-top">
							<div class="stat-icon teal">⇄</div>
							<a href="${pageContext.request.contextPath}/listMouvement"> <span
								class="stat-label">Mouvements</span></a>
						</div>
						<h2>${empty totalMouvements ? 0 : totalMouvements}</h2>
						<p>Total des mouvements enregistrés</p>
					</div>

				</div>

				<!-- GRAPHIQUES ET ALERTES -->
				<div class="charts-grid">

					<!-- ACTIVITE DU STOCK -->
					<div class="panel">

						<div class="panel-header">
							<div>
								<h3>Activité du stock</h3>
								<p>Exemple de visualisation des entrées et sorties</p>
							</div>

							<span class="panel-badge"> Données de démonstration </span>
						</div>

						<div class="chart-legend">
							<span><i class="legend-entry"></i> Entrées</span> <span><i
								class="legend-exit"></i> Sorties</span>
						</div>

						<div class="bar-chart">

							<div class="chart-y-labels">
								<span>100</span> <span>75</span> <span>50</span> <span>25</span>
								<span>0</span>
							</div>

							<div class="chart-area">

								<div class="chart-grid-lines">
									<i></i><i></i><i></i><i></i><i></i>
								</div>

								<div class="chart-columns">

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 75%"></div>
											<div class="bar exit" style="height: 45%"></div>
										</div>
										<span>Jan</span>
									</div>

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 65%"></div>
											<div class="bar exit" style="height: 55%"></div>
										</div>
										<span>Fév</span>
									</div>

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 90%"></div>
											<div class="bar exit" style="height: 60%"></div>
										</div>
										<span>Mar</span>
									</div>

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 60%"></div>
											<div class="bar exit" style="height: 40%"></div>
										</div>
										<span>Avr</span>
									</div>

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 80%"></div>
											<div class="bar exit" style="height: 65%"></div>
										</div>
										<span>Mai</span>
									</div>

									<div class="month-column">
										<div class="bars">
											<div class="bar entry" style="height: 70%"></div>
											<div class="bar exit" style="height: 50%"></div>
										</div>
										<span>Juin</span>
									</div>

								</div>
							</div>
						</div>
					</div>

					<!-- ALERTES -->
					<div class="panel">

						<div class="panel-header">
							<div>
								<h3>Alertes de stock</h3>
								<p>Surveillance des articles</p>
							</div>

							<span class="alert-count"> ${empty articlesStockFaible ? 0 : articlesStockFaible}
							</span>
						</div>

						<div class="alert-item">
							<div class="alert-symbol danger">!</div>
							<div>
								<strong>Stock faible</strong>
								<p>Articles sous le seuil minimum</p>
							</div>
						</div>

						<div class="alert-item">
							<div class="alert-symbol warning">!</div>
							<div>
								<strong>Rupture de stock</strong>
								<p>Articles dont le stock est nul</p>
							</div>
						</div>

						<a class="see-more"
							href="${pageContext.request.contextPath}/articles"> Consulter
							les articles → </a>

					</div>

				</div>

				<!-- DERNIERS MOUVEMENTS -->
				<div class="panel movements-panel">

					<div class="panel-header">
						<div>
							<h3>mouvements de stock du Jour</h3>
							<p>Suivi les mouvemets aujourd'hui</p>
						</div>

						<a href="${pageContext.request.contextPath}/listMouvement"
							class="see-more"> Voir tout → </a>
					</div>

					<div class="table-container">

						<table>
							<thead>
								<tr>
									<th>Fournisseur</th>
									<th>Article</th>
									<th>Type de mouvement</th>
									<th>Quantité</th>
									<th>Date</th>
								</tr>
							</thead>

							<tbody>

								<c:forEach items="${derniersMouvements}" var="m">
									<tr>
										<td><c:out value="${m.cnuf}"/></td>
										<td><c:out value="${m.codeArticle}"/></td>
										<td><span class="movement-type">
												<c:out value="${m.typemvtStock.libelle}"/> </span></td>
										<td><c:out value="${m.variation}"/></td>
										<td>
											<fmt:formatDate value="${m.dateMvtStock}" 
															pattern="dd/MM/yyyy HH:mm"/>
										</td>
									</tr>
								</c:forEach>

								<c:if test="${empty derniersMouvements}">
									<tr>
										<td colspan="5" class="empty-row">Aucun mouvement Pour aujourd'hui.</td>
									</tr>
								</c:if>

							</tbody>
						</table>

					</div>
				</div>

				<!-- FOOTER COMMUN -->
				<%@ include file="/WEB-INF/includes/footer.jspf"%>

			</section>

		</main>
	</div>
</body>
</html>
