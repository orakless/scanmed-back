DROP TABLE IF EXISTS Cities CASCADE;
DROP TABLE IF EXISTS Avatars CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Tokens;
DROP SEQUENCE IF EXISTS user_seq;

CREATE TABLE Cities
(
    id              INTEGER PRIMARY KEY,
    name            VARCHAR(64) NOT NULL,
    zip_code        VARCHAR(5) NOT NULL,
    CONSTRAINT UC_Name_ZipCode UNIQUE (name, zip_code) -- les villes peuvent avoir le meme nom ou meme code postal, mais pas la meme combinaison des deux
);

CREATE TABLE Avatars
(
    id              INTEGER PRIMARY KEY
);

CREATE SEQUENCE user_seq START 1;
CREATE TABLE Users
(
    id              INTEGER PRIMARY KEY,
    username        VARCHAR(31),
    email           VARCHAR(319) NOT NULL UNIQUE, -- voir RFC 3696 pour la taille max des mails (https://datatracker.ietf.org/doc/html/rfc3696#section-3)
    password        VARCHAR(256) NOT NULL,
    accepts_emails  BOOL DEFAULT False,
    is_admin        BOOL DEFAULT False,
    id_avatar       INTEGER NOT NULL,
    CONSTRAINT FK_Avatars_IdAvatar
               FOREIGN KEY (id_avatar) REFERENCES Avatars(id)
);


CREATE TABLE Tokens
(
    token           VARCHAR(64),
    user_id         INTEGER,
    device          VARCHAR(64),
    CONSTRAINT PK_Token
               PRIMARY KEY (token, user_id),
    CONSTRAINT UC_Token_UserId_Device
               UNIQUE (user_id, device),
    CONSTRAINT FK_Users_UserId
               FOREIGN KEY (user_id) REFERENCES Users(id)
);