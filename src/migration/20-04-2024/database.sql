DROP TABLE IF EXISTS Ville CASCADE;
DROP TABLE IF EXISTS Utilisateur CASCADE;
DROP TABLE IF EXISTS Jetons;
DROP SEQUENCE IF EXISTS utilisateur_seq;


CREATE TABLE Ville
(
    id            INTEGER PRIMARY KEY,
    nom           VARCHAR(64) NOT NULL,
    code_postal   VARCHAR(5) NOT NULL,
    CONSTRAINT UC_Nom_CodePostal UNIQUE (nom, code_postal) -- les villes peuvent avoir le meme nom ou meme code postal, mais pas la meme combinaison des deux
);

CREATE SEQUENCE utilisateur_seq START 1;
CREATE TABLE Utilisateur
(
    id            INTEGER PRIMARY KEY,
    pseudonyme    VARCHAR(32),
    mail          VARCHAR(320) NOT NULL UNIQUE, -- voir RFC 3696 pour la taille max des mails (https://datatracker.ietf.org/doc/html/rfc3696#section-3)
    mot_de_passe  VARCHAR(97) NOT NULL,
    accepte_mails BOOL DEFAULT False,
    est_admin     BOOL DEFAULT False
);

CREATE TABLE Jetons
(
    jeton           VARCHAR(64),
    id_utilisateur  INTEGER,
    nom_appareil    VARCHAR(64),
    CONSTRAINT PK_Jeton PRIMARY KEY (jeton, id_utilisateur),
    CONSTRAINT UC_Jeton_IdUtilisateur_NomAppareil UNIQUE (id_utilisateur, nom_appareil, jeton),
    CONSTRAINT FK_Utilisateur_Jeton FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id)
);