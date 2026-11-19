
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mouvements de stock</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mouvements.css">
</head>

<body>

<div class="app-container">

    <%@ include file="/WEB-INF/includes/sidebar.jspf" %>

    <main class="main-content">

        <%@ include file="/WEB-INF/includes/topbar.jspf" %>

        <section class="dashboard-content">

            <div class="panel">

                <!-- En-tête -->
                <div class="mvt-header">
                    <div>
                        <h2>Mouvements de stock</h2>
                        <p class="mvt-subtitle">Enregistrement et suivi des mouvements de l'article</p>
                    </div>
                    <span class="mvt-badge">Gestion du stock</span>
                </div>

                <!-- Informations de l'article -->
                <div class="article-resume">
                    <div class="article-resume-item">
                        <span class="resume-label">Code article</span>
                        <strong><c:out value="${article.codeArticle}"/></strong>
                    </div>

                    <div class="article-resume-item">
                        <span class="resume-label">Désignation</span>
                        <strong><c:out value="${article.designation}"/></strong>
                    </div>

                    <div class="article-resume-item">
                        <span class="resume-label">Fournisseur</span>
                        <strong><c:out value="${article.nomFournisseur}" default="—"/></strong>
                    </div>

                    <div class="article-resume-item">
                        <span class="resume-label">CNUF fournisseur</span>
                        <strong><c:out value="${article.cnufFournisseur}" default="—"/></strong>
                    </div>

                    <div class="article-resume-item stock-resume">
                        <span class="resume-label">Stock actuel</span>
                        <strong><c:out value="${article.stockActuel}"/></strong>
                    </div>
                </div>

                <!-- Messages -->
                <c:if test="${ok}">
                    <div class="message-ok">
                        <span>✓</span> Mouvement enregistré avec succès.
                    </div>
                </c:if>

                <c:if test="${not empty erreur}">
                    <div class="message-erreur">
                        <c:out value="${erreur}"/>
                    </div>
                </c:if>

                <!-- Formulaire de mouvement -->
                <div class="section-heading">
                    <div>
                        <h3>Nouveau mouvement</h3>
                        <p>Renseignez les informations du mouvement à enregistrer.</p>
                    </div>
                </div>

                <form id="formMvt"
                      class="formulaire-mvt"
                      method="post"
                      action="${pageContext.request.contextPath}/mouvements"
                      data-contexte="${pageContext.request.contextPath}"
                      data-article="${article.codeArticle} - ${article.designation}"
                      data-stock="${article.stockActuel}">

                    <input type="hidden" name="codeArticle"
                           value="<c:out value='${article.codeArticle}'/>">

                    <div class="form-group col-3">
                        <label for="typeMvt">Type de mouvement <span class="required">*</span></label>
                        <select id="typeMvt" name="typeMvt" required>
                            <option value="">— Choisir un type —</option>
                            <c:forEach items="${types}" var="t">
                                <option value="${t}"
                                        data-sens="${t.sens}"
                                        data-client="${t.clientConcerne}"
                                        data-correction="${t.correction}"
                                        <c:if test="${typeChoisi == t}">selected</c:if>>
                                    <c:out value="${t.libelle}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-3">
                        <label for="motif">Motif de correction</label>
                        <select id="motif" name="motif">
                            <option value="">— Aucun motif —</option>
                            <c:forEach items="${motifs}" var="mo">
                                <option value="${mo}"
                                        <c:if test="${motifChoisi == mo}">selected</c:if>>
                                    <c:out value="${mo.libelle}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <small>À renseigner uniquement pour une correction.</small>
                    </div>

                    <div class="form-group col-2">
                        <label for="quantite">Quantité <span class="required">*</span></label>
                        <input id="quantite" type="number" name="quantite"
                               min="1" step="1" required
                               value="<c:out value='${param.quantite}'/>"
                               placeholder="Ex. : 10">
                    </div>

                    <div class="form-group col-2">
                        <label for="reference">Référence</label>
                        <input id="reference" type="text" name="reference"
                               maxlength="50"
                               value="<c:out value='${param.reference}'/>"
                               placeholder="Bon, facture...">
                    </div>

                    <div class="form-group col-2">
                        <label for="codeClient">Code client</label>
                        <input id="codeClient" type="text" name="codeClient"
                               maxlength="20"
                               value="<c:out value='${param.codeClient}'/>"
                               placeholder="Numéro client">
                        <small>Pour les mouvements concernant un client.</small>
                    </div>

                    <div class="form-group col-full">
                        <label for="commentaire">Commentaire</label>
                        <textarea id="commentaire" name="commentaire"
                                  rows="3" maxlength="255"
                                  placeholder="Ajouter un commentaire..."><c:out value="${param.commentaire}"/></textarea>
                        <small>Obligatoire pour une correction de stock.</small>
                    </div>

                    <div class="form-actions col-full">
                        <button type="submit" class="btn-primary-mvt">
                            Enregistrer le mouvement
                        </button>
                    </div>
                </form>

                <!-- Historique -->
                <div class="section-heading history-heading">
                    <div>
                        <h3>Historique des mouvements</h3>
                        <p>Consultez les mouvements enregistrés pour cet article.</p>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${empty mouvements}">
                        <div class="empty-state">
                            <div class="empty-icon">↔</div>
                            <strong>Aucun mouvement enregistré</strong>
                            <p>Il n'existe pas encore de mouvement pour cet article.</p>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="table-wrapper">
                            <table class="table-mouvements">
                                <thead>
                                    <tr>
                                        <th>Numéro</th>
                                        <th>Date</th>
                                        <th>Type</th>
                                        <th class="text-right">Quantité</th>
                                        <th>Motif</th>
                                        <th>Fournisseur</th>
                                        <th>Référence</th>
                                        <th>Commentaire</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${mouvements}" var="m">
                                        <tr>
                                            <td class="nowrap">
                                                <c:out value="${m.id}"/>
                                            </td>

                                            <td class="nowrap">
                                                <fmt:formatDate value="${m.dateMvtStock}"
                                                                pattern="dd/MM/yyyy HH:mm"/>
                                            </td>

                                            <td>
                                                <span class="type-mvt">
                                                    <c:out value="${m.typemvtStock.libelle}"/>
                                                </span>
                                            </td>

                                            <td class="text-right nowrap">
                                                <span class="${m.variation > 0 ? 'qte-plus' : 'qte-moins'}">
                                                    <c:if test="${m.variation > 0}">+</c:if><c:out value="${m.variation}"/>
                                                </span>
                                            </td>

                                            <td>
                                                <c:out value="${m.motif.libelle}" default="—"/>
                                            </td>

                                            <td>
                                                <c:out value="${m.nomFournisseur}" default="—"/>
                                            </td>

                                            <td>
                                                <c:out value="${m.reference}" default="—"/>
                                            </td>

                                            <td class="comment-cell">
                                                <c:out value="${m.commentaire}" default="—"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- Actions de retour -->
                <div class="mvt-bottom-actions">
                    <c:url var="urlArticle" value="/article">
                        <c:param name="codeArticle" value="${article.codeArticle}"/>
                    </c:url>

                    <a class="btn-secondary-mvt" href="${urlArticle}">
                        Retour à l'article
                    </a>

                    <a class="btn-secondary-mvt"
                       href="${pageContext.request.contextPath}/articles">
                        Retour à la liste des articles
                    </a>
                </div>

            </div>

            <%@ include file="/WEB-INF/includes/footer.jspf" %>

        </section>
    </main>
</div>


<!-- Dialogue de confirmation -->
<dialog id="dlgConfirm" class="dlg-mvt">
    <div class="dialog-header">
        <h2>Confirmer le mouvement</h2>
        <p>Vérifiez les informations avant l'enregistrement.</p>
    </div>

    <dl id="resume" class="dialog-resume"></dl>

    <p id="alerteStock" class="message-erreur" hidden>
        Stock insuffisant : ce mouvement rendrait le stock négatif.
    </p>

    <div class="dialog-actions">
        <button type="button" id="btnAnnuler" class="btn-dialog-secondary">
            Annuler
        </button>
        <button type="button" id="btnConfirmer" class="btn-primary-mvt">
            Confirmer
        </button>
    </div>
</dialog>


<!-- Dialogue de création du client -->
<dialog id="dlgClient" class="dlg-mvt">
    <div class="dialog-header">
        <h2>Client introuvable</h2>
        <p>
            Le client <strong id="codeInconnu"></strong> n'existe pas.
            Voulez-vous le créer maintenant ?
        </p>
    </div>

    <form id="formClient" onsubmit="return false;">

        <div class="form-group">
            <label for="nc_code">Code client</label>
            <input id="nc_code" name="codeClient" type="text"
                   maxlength="20" required>
        </div>

        <div class="form-group">
            <label for="nc_nom">Nom</label>
            <input id="nc_nom" name="nomClient" type="text"
                   maxlength="100" required>
        </div>

        <div class="form-group">
            <label for="nc_prenom">Prénom</label>
            <input id="nc_prenom" name="prenomClient" type="text"
                   maxlength="100">
        </div>

        <div class="form-group">
            <label for="nc_tel">Téléphone</label>
            <input id="nc_tel" name="telephoneClient" type="text"
                   maxlength="30">
        </div>

        <div class="form-group">
            <label for="nc_adresse">Adresse</label>
            <input id="nc_adresse" name="adresseClient" type="text"
                   maxlength="255">
        </div>
    </form>

    <p id="erreurClient" class="message-erreur" hidden></p>

    <div class="dialog-actions">
        <button type="button" id="btnAnnulerClient"
                class="btn-dialog-secondary">
            Annuler
        </button>

        <button type="button" id="btnCreerClient" class="btn-primary-mvt">
            Créer le client
        </button>
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

    function option() {
        return type.options[type.selectedIndex];
    }

    function drapeau(nom) {
        return option().getAttribute(nom) === 'true';
    }

    function echapper(texte) {
        return String(texte == null ? '' : texte)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    // Activation des champs selon le type de mouvement.
    function majChamps() {
        var correction = type.value !== '' && drapeau('data-correction');
        var avecClient = type.value !== '' && drapeau('data-client');

        motif.disabled = !correction;
        motif.required = correction;

        commentaire.required = correction;

        codeClient.disabled = !avecClient;
        codeClient.required = avecClient;

        if (!correction) {
            motif.value = '';
            commentaire.required = false;
        }

        if (!avecClient) {
            codeClient.value = '';
        }
    }

    function ligne(libelle, valeur) {
        return '<dt>' + echapper(libelle) + '</dt><dd>'
            + echapper(valeur) + '</dd>';
    }

    // Résumé avant enregistrement.
    function ouvrirConfirmation(nomClient) {
        var qte = parseInt(quantite.value, 10) || 0;
        var sens = parseInt(option().getAttribute('data-sens'), 10) || 0;
        var nouveauStock = stock + sens * qte;

        var html = ligne('Article', form.getAttribute('data-article'))
            + ligne('Type', option().text.trim())
            + ligne('Quantité', (sens > 0 ? '+' : sens < 0 ? '-' : '') + qte)
            + ligne('Stock actuel', stock)
            + ligne('Nouveau stock', nouveauStock);

        if (!codeClient.disabled) {
            html += ligne('Client',
                codeClient.value.trim() + (nomClient ? ' - ' + nomClient : ''));
        }

        if (!motif.disabled && motif.value) {
            html += ligne('Motif', motif.options[motif.selectedIndex].text);
        }

        if (reference.value.trim()) {
            html += ligne('Référence', reference.value.trim());
        }

        if (commentaire.value.trim()) {
            html += ligne('Commentaire', commentaire.value.trim());
        }

        document.getElementById('resume').innerHTML = html;

        document.getElementById('alerteStock').hidden = nouveauStock >= 0;
        document.getElementById('btnConfirmer').disabled = nouveauStock < 0;

        dlgConfirm.showModal();
    }

    // Vérification du client.
    function verifierClient() {
        if (codeClient.disabled) {
            ouvrirConfirmation('');
            return;
        }

        var code = codeClient.value.trim();

        fetch(contexte + '/clients?codeClient=' + encodeURIComponent(code), {
            headers: { 'Accept': 'application/json' }
        })
        .then(function (reponse) {
            if (!reponse.ok) {
                throw new Error('Erreur lors de la vérification du client.');
            }
            return reponse.json();
        })
        .then(function (rep) {
            // Le Servlet Clients doit renvoyer "existe" et "nomComplet".
            if (rep.existe === true) {
                ouvrirConfirmation(rep.nomComplet || '');
            } else {
                ouvrirCreationClient(code);
            }
        })
        .catch(function (erreur) {
            console.error('Erreur vérification client :', erreur);
            alert('Impossible de vérifier le client. Réessayez.');
        });
    }

    function ouvrirCreationClient(code) {
        document.getElementById('codeInconnu').textContent = code;
        document.getElementById('nc_code').value = code;
        document.getElementById('erreurClient').hidden = true;
        dlgClient.showModal();
    }

    // Création du client.
    function creerClient() {
        var formClient = document.getElementById('formClient');

        if (!formClient.checkValidity()) {
            formClient.reportValidity();
            return;
        }

        var bouton = document.getElementById('btnCreerClient');
        bouton.disabled = true;

        fetch(contexte + '/clients', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'Accept': 'application/json'
            },
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
                ouvrirConfirmation(rep.nomComplet || '');
            } else {
                throw new Error(rep.erreur || 'La création du client a échoué.');
            }
        })
        .catch(function (erreur) {
            console.error('Erreur création client :', erreur);

            var zone = document.getElementById('erreurClient');
            zone.textContent = erreur.message;
            zone.hidden = false;
        })
        .finally(function () {
            bouton.disabled = false;
        });
    }

    // Événements.
    type.addEventListener('change', majChamps);

    form.addEventListener('submit', function (e) {
        if (typeof dlgConfirm.showModal !== 'function') {
            return;
        }

        e.preventDefault();

        if (!type.value) {
            type.reportValidity();
            return;
        }

        verifierClient();
    });

    document.getElementById('btnConfirmer').addEventListener('click', function () {
        dlgConfirm.close();
        form.submit();
    });

    document.getElementById('btnAnnuler').addEventListener('click', function () {
        dlgConfirm.close();
    });

    document.getElementById('btnAnnulerClient').addEventListener('click', function () {
        dlgClient.close();
    });

    document.getElementById('btnCreerClient').addEventListener('click', creerClient);

    majChamps();
})();
</script>

</body>
</html>
