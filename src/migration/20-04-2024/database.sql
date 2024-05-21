DROP SEQUENCE IF EXISTS city_seq;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS pharmacy_seq;
DROP SEQUENCE IF EXISTS medecine_seq;
DROP SEQUENCE IF EXISTS report_seq;
DROP TABLE IF EXISTS Cities CASCADE;
DROP TABLE IF EXISTS Avatars CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Tokens;
DROP TABLE IF EXISTS Pharmacies CASCADE;
DROP TABLE IF EXISTS Medecines CASCADE;
DROP TABLE IF EXISTS Reports CASCADE;
DROP TABLE IF EXISTS ReportsStateChanges;
DROP TYPE IF EXISTS report_state CASCADE;


CREATE SEQUENCE city_seq START 1;
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
    password        VARCHAR(97) NOT NULL,
    accepts_emails  BOOL DEFAULT False,
    is_admin        BOOL DEFAULT False,
    avatar_id       INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT FK_Avatars_IdAvatar
               FOREIGN KEY (avatar_id) REFERENCES Avatars(id)
);


CREATE TABLE Tokens
(
    token           VARCHAR(64),
    user_id         INTEGER,
    device          VARCHAR(64),
    CONSTRAINT PK_Token
               PRIMARY KEY (token, user_id),
    CONSTRAINT UC_Tokens_UserId_Device
               UNIQUE (user_id, device),
    CONSTRAINT FK_Users_UserId
               FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE SEQUENCE pharmacy_seq START 1;
CREATE TABLE Pharmacies
(
    id             INTEGER PRIMARY KEY,
    city_id        INTEGER,
    number         VARCHAR(8),
    street         VARCHAR(128)
);

CREATE SEQUENCE medecine_seq START 1;
CREATE TABLE Medecines
(
    CIP VARCHAR(13) PRIMARY KEY,
    name VARCHAR(256)
);

CREATE SEQUENCE report_seq START 1;
CREATE TABLE Reports
(
    id              INTEGER PRIMARY KEY,
    user_id         INTEGER,
    pharmacy_id     INTEGER,
    CIP             INTEGER,
    submission_date TIMESTAMP
);

CREATE TYPE report_state AS ENUM ('submitted', 'rejected', 'resupplying', 'resupplied');
CREATE TABLE ReportsStateChanges (
    report_id   INTEGER,
    action_date TIMESTAMP,
    old_state   report_state,
    new_state   report_state,
    CONSTRAINT PK_ReportsStateChanges
               PRIMARY KEY (report_id, action_date),
    CONSTRAINT FK_Reports_ReportId
               FOREIGN KEY (report_id) REFERENCES Reports(id)
)