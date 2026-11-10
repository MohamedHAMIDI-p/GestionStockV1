package test;


import java.util.Date;
import java.util.List;

import dao.ArticleDao;
import dao.FournisseurDao;
import dao.mvtStockDao;
import dao.Impl.ArticleDaoImpl;
import dao.Impl.FournisseurDaoImpl;
import dao.Impl.mvtStockImpl;
import dto.ArticleDetailDto;
import dto.ArticleDto;
import dto.mvtStockDto;
import exception.MotifCorrection;
import exception.TvaArticle;
import exception.TypeMvtStock;
import model.Article;
import model.Fournisseur;
import service.ArticleService;
import service.FournisseurService;
import service.MvtStockService;
import service.Impl.ArticleServiceImpl;
import service.Impl.FournisseurServiceImpl;
import service.Impl.MvtStockServiceImpl;
import util.JsonUtil;

public class TestArticle {

	public static void main(String[] args) throws Exception {
		
		ArticleDao dao = new ArticleDaoImpl();
		MvtStockService ms = new MvtStockServiceImpl(dao, new mvtStockImpl());
		FournisseurDao daoFour= new FournisseurDaoImpl();
		FournisseurService serviceFour=new FournisseurServiceImpl(daoFour);
		ArticleService service=new ArticleServiceImpl(dao);
		List<Article> lisArt=service.findListArticle();
		List<ArticleDto> listArtDto=service.findListArticleDto();
		ArticleDetailDto article;
		try {
			System.out.println("===============Ajouter Fournisseur==================");
			Fournisseur f=new Fournisseur("F008", "Fournisseur SAvon", "Hamza","066633167","120", "ERRACHIDIA","abdelhadiHamidi@test.example","32323133243525", new Date(),new Date());
			//serviceFour.ajouterFournisseur(f);
			serviceFour.trouverFournisseur("F008");
			System.out.println("=============Afficher Fournisseur====================");	
			//System.out.println(JsonUtil.toPrettyJson(f));
			System.out.println("================================================");
			System.out.println("===============Ajouter Article==================");
			Article a= new Article("ART014","BLOC WC",2,3,new Date(),new Date());
			a.setPrixAchatArticle(300);
			a.setPrixVenteArticle(200);
			a.setPrixRevient(10);
			a.setTvaArticle(TvaArticle.fromTaux(7));
			a.setIdCategory(3);
			a.setIdFournisseur(1);
			//service.ajouterArticle(a);
			System.out.println("================================================");
			System.out.println("=============Afficher Article====================");	
			article = service.trouverDetailArticle("ART013");
			System.out.println("=============Article JSON====================");
			System.out.println(JsonUtil.toPrettyJson(article));
			System.out.println("=============Article normal====================");
			//System.out.println(a);
			System.out.println("=============Article ToSting====================");
			//System.out.println(a.toString());
			System.out.println("================================================");
			System.out.println("===============Afficher Liste Article====================");
			System.out.println("Nombre Article Trouves : "+lisArt.size());
			//System.out.println(JsonUtil.toPrettyJson(lisArt));
			System.out.println("================================================");
			System.out.println("===================Mouvements====================");
			 article =service.trouverDetailArticle("ART002");
			 System.out.println(JsonUtil.toPrettyJson(article));
			ms.enregistrerMvtStock("ART002", TypeMvtStock.reception, 100, null, null, "BR-0002", null);
			ms.enregistrerMvtStock("ART002", TypeMvtStock.retourEntre, 30, null, null, "FAC-0002", null);
			ms.enregistrerMvtStock("ART002", TypeMvtStock.CORRECTION_INVENTAIRE_PLUS,20, MotifCorrection.PEREMPTION, null, null,
					"Ajuster le stock");
			// ms1 = new MvtStockServiceImpl(new ArticleDaoImpl(), new mvtStockImpl());
			List<mvtStockDto> msList=ms.historique("ART002");
			System.out.println("Nombre movement of list : "+msList.size());
			for(mvtStockDto mvt:msList) {
				System.out.println(mvt);
			}
			System.out.println();
			System.out.println("================================================");
			System.out.println("================Liste Article DTO================");
			System.out.println("Nombre Article of list : "+listArtDto.size());
			for(ArticleDto dto:listArtDto) {
				System.out.println(util.JsonUtil.toPrettyJson(dto));
			}
			
			for(ArticleDto dtoo:listArtDto) {
				//System.out.println(dtoo);
			}
			//System.out.println(JsonUtil.toPrettyJson(listArtDto));
			System.out.println("================================================");
			}catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
	}
}
