<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Mouvements de Stock</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleM.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<style>
	/* ===== Disposition lignes colonnes =====*/
	.formulaire { grid-template-columns: repeat(6, 1fr); }
	.formulaire .c3 { grid-column: span 3; }
	.formulaire .c2 { grid-column: span 2; }
	.formulaire .large { grid-column: 1 / -1; }
	.formulaire select:disabled,
	.formulaire input:disabled { background: #f4f6f9; color: #999; cursor: not-allowed; }
	@media (max-width: 700px) {
		.formulaire .c3, .formulaire .c2 { grid-column: 1 / -1; }
	}

	[hidden] { display: none !important; }

	/* ===== style Boîtes de dialogue ===== */
	.dlg { width: calc(100% - 32px); max-width: 520px; padding: 22px; border: 0; border-radius: 8px;
	       box-shadow: 0 10px 30px rgba(0, 0, 0, .25); }
	.dlg::backdrop { background: rgba(0, 0, 0, .4); }
	.dlg h2 { margin: 0 0 14px; font-size: 20px; color: #2c3e50; }
	.dlg dl { display: grid; grid-template-columns: 110px 1fr; gap: 6px 12px; margin: 0; }
	.dlg dt { color: #666; }
	.dlg dd { margin: 0; font-weight: bold; }
	.dlg-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 18px; }
	.btn-sec { background: #7f8c8d; }
	.btn-sec:hover { background: #626d6e; }
	.btn:disabled { background: #b0b7bd; cursor: not-allowed; }
	.dlg .champ { margin-bottom: 10px; }
	.dlg label { display: block; margin-bottom: 4px; font-size: 13px; color: #555; }
	.dlg input { width: 100%; padding: 8px 10px; border: 1px solid #d0d7de; border-radius: 4px; font: inherit; }
</style>
</head>
<body>
<div class="page">
	<div class="card">
		<!-- entete de page  -->
		<div class="card-header">
			<h1>Mouvements de Stock</h1>
			<span class="compteur">
				<c:out value="${article.codeArticle}" /> &middot; <c:out value="${article.designation}" />
				 &middot; <c:out value="${article.cnufFournisseur}" />
				 &middot;<c:out value="${article.nomFournisseur}" />
				&middot; stock actuel : <strong><c:out value="${article.stockActuel}" /></strong>
			</span>
		</div>

		<c:if test="${ok}">
			<p class="message-ok">Mouvement enregistre.</p>
		</c:if>
		<c:if test="${not empty erreur}">
			<p class="message-erreur"><c:out value="${erreur}" /></p>
		</c:if>

		<!-- ===== Saisie mouvement Stock ===== -->

<form id="formMvt" class="formulaire" method="post"
	action="${pageContext.request.contextPath}/mouvements"
	data-contexte="${pageContext.request.contextPath}"
	data-article="<c:out value="${article.codeArticle} - ${article.designation}" />"
	data-stock="${article.stockActuel}">

	<input type="hidden" name="codeArticle" value="<c:out value="${article.codeArticle}" />">

	<!-- =====Type de mouvement + Motif ===== -->
	<div class="c3">
		<label for="typeMvt">Type de mouvement</label>
		<select id="typeMvt" name="typeMvt" required>
			<option value="">— choisir —</option>
			<c:forEach items="${types}" var="t">
				<option value="${t}"
					data-sens="${t.sens}"
					data-client="${t.clientConcerne}"
					data-correction="${t.correction}"
					<c:if test="${typeChoisi == t}">selected</c:if>><c:out value="${t.libelle}" /></option>
			</c:forEach>
		</select>
	</div>

	<div class="c3">
		<label for="motif">Motif (corrections uniquement)</label>
		<select id="motif" name="motif">
			<option value="">— aucun —</option>
			<c:forEach items="${motifs}" var="mo">
				<option value="${mo}" <c:if test="${motifChoisi == mo}">selected</c:if>><c:out value="${mo.libelle}" /></option>
			</c:forEach>
		</select>
	</div>

	<!-- ===== Quantité + Référence + Client ===== -->
	<div class="c2">
		<label for="quantite">Quantité</label>
		<input id="quantite" type="number" name="quantite" min="1" step="1" required
			value="<c:out value="${param.quantite}" />">
	</div>

	<div class="c2">
		<label for="reference">Référence (bon, facture...)</label>
		<input id="reference" type="text" name="reference" maxlength="50"
			value="<c:out value="${param.reference}" />">
	</div>

	<div class="c2">
		<label for="codeClient">Client (Numéro Client)</label>
		<input id="codeClient" type="text" name="codeClient" maxlength="20"
			value="<c:out value="${param.codeClient}" />">
	</div>

	<!-- =====Commentaire ===== -->
	<div class="large">
		<label for="commentaire">Commentaire (obligatoire pour une correction)</label>
		<textarea id="commentaire" name="commentaire" rows="2" maxlength="255"><c:out value="${param.commentaire}" /></textarea>
	</div>

	<div class="large">
		<button type="submit" class="btn">Enregistrer le mouvement</button>
	</div>
</form>

<!-- ===== Boîte 1 : confirmation avant l'enregistrement ===== -->
<dialog id="dlgConfirm" class="dlg">
	<h2>Confirmer le mouvement</h2>
	<dl id="resume"></dl>
	<p id="alerteStock" class="message-erreur" hidden>Stock insuffisant : ce mouvement rendrait le stock négatif.</p>
	<div class="dlg-actions">
		<button type="button" id="btnAnnuler" class="btn btn-sec">Annuler</button>
		<button type="button" id="btnConfirmer" class="btn">Confirmer</button>
	</div>
</dialog>

<!-- ===== Boîte 2 : client introuvable, proposition de le créer ===== -->
<dialog id="dlgClient" class="dlg">
	<h2>Client introuvable</h2>
	<p>Le client <strong id="codeInconnu"></strong> n'existe pas. Voulez-vous le créer maintenant ?</p>
	<form id="formClient" onsubmit="return false;">
		<div class="champ">
			<label for="nc_code">Code client</label>
			<input id="nc_code" name="codeClient" type="text" maxlength="20" required>
		</div>
		<div class="champ">
			<label for="nc_nom">Nom</label>
			<input id="nc_nom" name="nomClient" type="text" maxlength="100" required>
		</div>
		<div class="champ">
			<label for="nc_prenom">Prénom</label>
			<input id="nc_prenom" name="prenomClient" type="text" maxlength="100">
		</div>
		<div class="champ">
			<label for="nc_tel">Téléphone</label>
			<input id="nc_tel" name="telephoneClient" type="text" maxlength="30">
		</div>
		<div class="champ">
			<label for="nc_adresse">Adresse</label>
			<input id="nc_adresse" name="adresseClient" type="text" maxlength="255">
		</div>
	</form>
	<p id="erreurClient" class="message-erreur" hidden></p>
	<div class="dlg-actions">
		<button type="button" id="btnAnnulerClient" class="btn btn-sec">Annuler</button>
		<button type="button" id="btnCreerClient" class="btn">Créer le client</button>
	</div>
</dialog>

<script>
	(function () {
		var form = document.getElementById('formMvt');
		var contexte = form.getAttribute('data-contexte');
		var stock = parseInt(form.getAttribute('data-stock'), 10) || 0;

		var type = document.getElementById('typeMvt');
		var motif = document.getElementById('motif');
		var quantite = document.getElementById('quantite');
		var reference = document.getElementById('reference');
		var codeClient = document.getElementById('codeClient');
		var commentaire = document.getElementById('commentaire');

		var dlgConfirm = document.getElementById('dlgConfirm');
		var dlgClient = document.getElementById('dlgClient');

		function option() { return type.options[type.selectedIndex]; }
		function drapeau(nom) { return option().getAttribute(nom) === 'true'; }

		function echapper(texte) {
			return String(texte).replace(/&/g, '&amp;').replace(/</g, '&lt;')
				.replace(/>/g, '&gt;').replace(/"/g, '&quot;');
		}

		// 1. Seuls les champs qui concernent le type choisi sont actifs (tous restent visibles).
		//    Un champ désactivé n'est pas envoyé : le serveur reçoit "pas de motif" ou "pas de client".
		function majChamps() {
			var correction = drapeau('data-correction');
			var avecClient = drapeau('data-client');

			motif.disabled = !correction;
			motif.required = correction;
			commentaire.required = correction;
			codeClient.disabled = !avecClient;
			codeClient.required = avecClient;

			if (!correction) { motif.value = ''; }
			if (!avecClient) { codeClient.value = ''; }
		}

		// 2. Boîte de confirmation : résumé du mouvement et nouveau stock
		function ligne(libelle, valeur) {
			return '<dt>' + libelle + '</dt><dd>' + echapper(valeur) + '</dd>';
		}

		function ouvrirConfirmation(nomClient) {
			var qte = parseInt(quantite.value, 10) || 0;
			var sens = parseInt(option().getAttribute('data-sens'), 10) || 0;
			var nouveauStock = stock + sens * qte;

			var html = ligne('Article', form.getAttribute('data-article'))
				+ ligne('Type', option().text)
				+ ligne('Quantité', (sens > 0 ? '+' : '-') + qte)
				+ ligne('Stock', stock + '  →  ' + nouveauStock);
			if (!codeClient.disabled) {
				html += ligne('Client', codeClient.value.trim() + (nomClient ? ' - ' + nomClient : ''));
			}
			if (!motif.disabled && motif.value) {
				html += ligne('Motif', motif.options[motif.selectedIndex].text);
			}
			if (reference.value.trim()) { html += ligne('Référence', reference.value.trim()); }
			if (commentaire.value.trim()) { html += ligne('Commentaire', commentaire.value.trim()); }

			document.getElementById('resume').innerHTML = html;
			document.getElementById('alerteStock').hidden = nouveauStock >= 0;
			document.getElementById('btnConfirmer').disabled = nouveauStock < 0;
			dlgConfirm.showModal();
		}

		// 3. Le client existe-t-il ? Sinon, proposer de le créer
		function verifierClient() {
			if (codeClient.disabled) { ouvrirConfirmation(''); return; }

			var code = codeClient.value.trim();
			fetch(contexte + '/clients?codeClient=' + encodeURIComponent(code), { headers: { 'Accept': 'application/json' } })
				.then(function (reponse) { return reponse.json(); })
				.then(function (rep) {
					if (rep.exists == true) {
						var nomComplet = rep.nomClient + ' ' + rep.prenomClient;
						ouvrirConfirmation(nomComplet);
					} else {
						ouvrirCreationClient(code);
					}
				})
				.catch(function (erreur) {  
					console.error('Erreur vérification client :', erreur);
		        alert('Impossible de vérifier le client. Réessayez.'); });
		}

		function ouvrirCreationClient(code) {
			document.getElementById('codeInconnu').textContent = code;
			document.getElementById('nc_code').value = code;
			document.getElementById('erreurClient').hidden = true;
			dlgClient.showModal();
		}

		function creerClient() {
			var formClient = document.getElementById('formClient');
			if (!formClient.checkValidity()) { formClient.reportValidity(); return; }

			fetch(contexte + '/clients', {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
				body: new URLSearchParams(new FormData(formClient)).toString()
			})
				.then(function (reponse) { 
					 return reponse.json().then(function (data) {
					        if (!reponse.ok) {
					            throw new Error(data.erreur || data.message || 'Erreur serveur');
					        }
					        return data;
					    });
				})
				.then(function (rep) {
					if (rep.ok) {
						codeClient.value = document.getElementById('nc_code').value.trim();
						dlgClient.close();
						ouvrirConfirmation(rep.nomComplet);
					} else {
						var zone = document.getElementById('erreurClient');
						zone.textContent = rep.erreur;
						zone.hidden = false;
					}
				})
				.catch(function () {
					console.error('Erreur création client :', erreur);

				    var zone = document.getElementById('erreurClient');
				    zone.textContent = erreur.message;
				    zone.hidden = false;
					});
		}

		// 4. Branchement
		type.addEventListener('change', majChamps);

		form.addEventListener('submit', function (e) {
			// navigateur sans <dialog> : le formulaire part tel quel, le serveur contrôle tout
			if (typeof dlgConfirm.showModal !== 'function') { return; }
			e.preventDefault();               // la validation HTML (champs requis) a déjà eu lieu
			verifierClient();
		});

		document.getElementById('btnConfirmer').addEventListener('click', function () {
			dlgConfirm.close();
			form.submit();                    // n'appelle pas à nouveau l'écouteur "submit"
		});
		document.getElementById('btnAnnuler').addEventListener('click', function () { dlgConfirm.close(); });
		document.getElementById('btnAnnulerClient').addEventListener('click', function () { dlgClient.close(); });
		document.getElementById('btnCreerClient').addEventListener('click', creerClient);

		majChamps();                          // à l'ouverture de la page, et après une erreur de saisie
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
