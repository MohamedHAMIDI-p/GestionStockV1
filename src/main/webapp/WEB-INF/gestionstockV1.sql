CREATE DATABASE IF NOT EXISTS gestionstockV1  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use gestionstockv1;
commit;
CREATE TABLE category (  idCategory   INT AUTO_INCREMENT PRIMARY KEY,  nomCategory  VARCHAR(100) NOT NULL) ENGINE=InnoDB;
commit;
CREATE TABLE fournisseur (  idFournisseur   INT AUTO_INCREMENT PRIMARY KEY,  nomFournisseur  VARCHAR(100) NOT NULL) ENGINE=InnoDB;
commit;
CREATE TABLE article (  idArticle      INT AUTO_INCREMENT PRIMARY KEY,  codeArticle    VARCHAR(50)  NOT NULL UNIQUE,  designation    VARCHAR(200) NOT NULL,  idCategory     INT NOT NULL,  idFournisseur  INT NOT NULL,  CONSTRAINT fk_article_category    FOREIGN KEY (idCategory)    REFERENCES category(idCategory),  CONSTRAINT fk_article_fournisseur    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(idFournisseur)) ENGINE=InnoDB;
commit;
INSERT INTO category (nomCategory) VALUES ('Boissons'), ('Alimentaire'), ('Hygiène');
commit;
INSERT INTO fournisseur (nomFournisseur) VALUES ('Fournisseur A'), ('Fournisseur B');
commit;
INSERT INTO article (codeArticle, designation, idCategory, idFournisseur) VALUES  ('ART001', 'Eau minérale 1.5L', 1, 1),  ('ART002', 'Jus d''orange 1L',  1, 2),  ('ART003', 'Savon liquide',     3, 1);
commit;
select * from article;
select * from fournisseur;
select * from category;

USE gestionstockV1;
DESCRIBE fournisseur;
ALTER TABLE fournisseur ADD COLUMN prenomFournisseur    VARCHAR(100) NULL;
ALTER TABLE fournisseur ADD COLUMN cnuf    VARCHAR(20) NULL;
 
 
ALTER TABLE fournisseur
  ADD COLUMN telephoneFournisseur VARCHAR(30)  NULL,
  ADD COLUMN codePostal           VARCHAR(10)  NULL,
  ADD COLUMN villeFournisseur     VARCHAR(100) NULL,
  ADD COLUMN mailFournisseur      VARCHAR(150) NULL,
  ADD COLUMN rib                  VARCHAR(34)  NULL,
  ADD COLUMN dateCreation         DATETIME     NULL,
  ADD COLUMN dateModification     DATETIME     NULL;
  
  
  ALTER TABLE article
  ADD COLUMN prixAchatArticle DECIMAL(12,2) NOT NULL DEFAULT 0,
  ADD COLUMN prixVenteArticle DECIMAL(12,2) NOT NULL DEFAULT 0,
  ADD COLUMN prixRevient      DECIMAL(12,2) NOT NULL DEFAULT 0,
  ADD COLUMN tvaArticle       TINYINT       NOT NULL DEFAULT 20;
  
  
  
  USE gestionstockV1;
  
  --    Cette colonne n'est modifiée que par l'enregistrement d'un mouvement.
  ALTER TABLE article
  ADD COLUMN stockActuel INT NOT NULL DEFAULT 0;
  
  
  
  CREATE TABLE mouvement (
  id             INT AUTO_INCREMENT PRIMARY KEY,
  idArticle      INT          NOT NULL,
  typeMouvement  VARCHAR(30)  NOT NULL,      -- RECEPTION, VENTE, RETOUR_ENTREE, RETOUR_SORTIE, CORRECTION_PLUS, CORRECTION_MOINS
  variation      INT          NOT NULL,      -- signée : +entrée, -sortie
  motif          VARCHAR(30)  NULL,          -- corrections seulement : VOL, CASSE, PEREMPTION...
  idFournisseur  INT          NULL,          -- réception et retour fournisseur seulement
  reference      VARCHAR(50)  NULL,          -- n° de bon de réception, de facture, de retour...
  commentaire    VARCHAR(255) NULL,          -- obligatoire pour les corrections
  dateMouvement  DATETIME     NOT NULL,
  CONSTRAINT fk_mouvement_article     FOREIGN KEY (idArticle)     REFERENCES article(id),
  CONSTRAINT fk_mouvement_fournisseur FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id),
  INDEX idx_mouvement_article_date (idArticle, dateMouvement)
) ENGINE=InnoDB;

--Contrôle de cohérence : doit ne renvoyer aucune ligne.


SELECT a.id, a.codeArticle, a.stockActuel, COALESCE(SUM(m.variation), 0) AS stockCalcule
FROM article a
LEFT JOIN mouvement m ON m.idArticle = a.id
GROUP BY a.id, a.codeArticle, a.stockActuel
HAVING a.stockActuel <> COALESCE(SUM(m.variation), 0);
  
  