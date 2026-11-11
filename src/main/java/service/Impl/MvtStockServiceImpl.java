package service.Impl;

import java.sql.SQLException;
import java.util.List;

import dao.ArticleDao;
import dao.Impl.ArticleDaoImpl;
import dto.ArticleDetailDto;
import dto.mvtStockDto;
import exception.EntityNotFoundException;
import exception.ErreurCodes;
import exception.InvalidEntityException;
import exception.MotifCorrection;
import exception.TypeMvtStock;
import model.Article;
import model.mvtStock;
import service.ArticleService;
import service.MvtStockService;

public class MvtStockServiceImpl  implements MvtStockService{

	private static final int LONGUEUR_MAX_REFERENCE = 50;
	private static final int LONGUEUR_MAX_COMMENTAIRE = 255;
 
	private final ArticleDao articleDao;
	private final dao.mvtStockDao mvtStockDao;
	private  ArticleDetailDto article=new ArticleDetailDto();

	
	public MvtStockServiceImpl(ArticleDao articleDao, dao.mvtStockDao mvtStockDao) {
		this.articleDao = articleDao;
		this.mvtStockDao = mvtStockDao;
	}
	
	@Override
	public mvtStock enregistrerMvtStock(String codeArticle, TypeMvtStock type, int quantite, MotifCorrection motif,
			Integer idFournisseur, String reference, String commentaire) throws SQLException {
 
		//  Contrôler la saisie
		if (type == null) {
			throw invalide("Le type de mouvement est obligatoire");
		}
		if (quantite <= 0) {
			throw invalide("La quantité doit être supérieure à 0");
		}
 
		String ref = nettoyer(reference);
		String com = nettoyer(commentaire);
 
		if (ref != null && ref.length() > LONGUEUR_MAX_REFERENCE) {
			throw invalide("La référence ne doit pas dépasser " + LONGUEUR_MAX_REFERENCE + " caractères");
		}
		if (com != null && com.length() > LONGUEUR_MAX_COMMENTAIRE) {
			throw invalide("Le commentaire ne doit pas dépasser " + LONGUEUR_MAX_COMMENTAIRE + " caractères");
		}
 
		//  L'article existe ?
		ArticleService articleService=new ArticleServiceImpl(articleDao);
		Article article = articleService.trouverArticle(codeArticle);
		
		if (article == null) {
			throw new EntityNotFoundException("Article introuvable :" + codeArticle,
					ErreurCodes.ARTICLE_NOT_FOUND);
		}
 
		// Règles propres à chaque type de mouvement
		if (type.isCorrection()) {
			if (motif == null) {
				throw invalide("Le motif de la correction est obligatoire");
			}
			if (motif.estCompatible(type)) {
				throw invalide("Le motif « " + motif.getLibelle() + " » ne convient pas à : " + type.getLibelle());
			}
			if (com == null) {
				throw invalide("Un commentaire de justification est obligatoire pour une correction");
			}
		} else if (motif != null) {
			throw invalide("Un motif n'est autorisé que pour une correction");
		}
 
		Integer fournisseur = null;
		if (type.isFournisseurConcerne()) {
			fournisseur = idFournisseur;
			if (fournisseur == null && article.getIdFournisseur() > 0) {
				fournisseur = article.getIdFournisseur();
				//fournisseur = Integer.valueOf(article.getIdFournisseur());   // par défaut : le fournisseur de l'article
			}
			if (fournisseur == null) {
				throw invalide("Le fournisseur est obligatoire pour : " + type.getLibelle());
			}
		}
 
		// Enregistrement , le DAO refuse une sortie qui rendrait le stock négatif
		mvtStock mvt = new mvtStock(article.getId(), type, quantite);
		mvt.setMotif(motif);
		mvt.setIdFournisseur(fournisseur);
		mvt.setReference(ref);
		mvt.setCommentaire(com);
 
		if (!mvtStockDao.AjouterMvtStock(mvt)) {
			throw new InvalidEntityException("Stock insuffisant pour l'article " + article.getCodeArticle()
					+ " : sortie de " + quantite + " refusée (stock disponible : " + article.getStockActuel() + ")",
					ErreurCodes.STOCK_INSUFFISANT);
		}
		return mvt;
	}
 
	@Override
	public List<mvtStockDto> historique(String codeArticle) throws SQLException {
		ArticleDetailDto article = trouverArticle(codeArticle);
		return mvtStockDao.findHistorique(article.getCodeArticle());
	}
 
	// ---------- methodes privées ----------
 
	private ArticleDetailDto trouverArticle(String codeArticle) throws SQLException {
		/*articleDao = new ArticleDaoImpl(); */
		ArticleService articleService=new ArticleServiceImpl(articleDao);
		if (codeArticle == null || codeArticle.trim().isEmpty()) {
			throw invalide("Le code article est obligatoire");
		}
		try {
			//article = articleDao.FindArtDetbyCodeArticle(codeArticle);
			article=articleService.trouverDetailArticle(codeArticle);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		if (article == null) {
			throw new EntityNotFoundException("Article introuvable :" + codeArticle,
					ErreurCodes.ARTICLE_NOT_FOUND);
		}
		return article;
	}
 
	private InvalidEntityException invalide(String message) {
		return new InvalidEntityException(message, ErreurCodes.MOUVEMENT_NOT_VALID);
	}
 
	/** Supprime les espaces et une chaîne vide devient null. */
	private String nettoyer(String valeur) {
		if (valeur == null) {
			return null;
		}
		String v = valeur.trim();
		return v.isEmpty() ? null : v;
	}
}