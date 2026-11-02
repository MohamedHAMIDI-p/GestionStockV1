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