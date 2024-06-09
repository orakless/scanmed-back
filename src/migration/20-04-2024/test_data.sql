INSERT INTO Avatars (id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11);

INSERT INTO Cities (id, name, zip_code)
VALUES (NEXTVAL('city_seq'), 'Paris - 16ème arrondissement', '75016');

INSERT INTO Pharmacies (id, city_id, name, number, street)
VALUES (NEXTVAL('pharmacy_seq'), CURRVAL('city_seq'), 'Pharmacie du Bien Etre', '36', 'Rue de la Pompe');
INSERT INTO Pharmacies (id, city_id, name, number, street)
VALUES (NEXTVAL('pharmacy_seq'), CURRVAL('city_seq'), 'Pharmacie de la Croix Bleue - I Pharm', '43', 'Rue d''Auteuil');

INSERT INTO Cities (id, name, zip_code)
VALUES (NEXTVAL('city_seq'), 'Paris - 7ème arrondissement', '75007');

INSERT INTO Pharmacies (id, city_id, name, number, street)
VALUES (NEXTVAL('pharmacy_seq'), CURRVAL('city_seq'), 'La Pharmacie parisienne', '104', 'Rue Saint-Dominique');
INSERT INTO Pharmacies (id, city_id, name, number, street)
VALUES (NEXTVAL('pharmacy_seq'), CURRVAL('city_seq'), 'Pharmacie d''Orsay', '6', 'Rue de Bellechasse');

INSERT INTO Medecines (cip, name) VALUES ('3400936381865', 'BIOGARAN PARACÉTAMOL 1g Cpr Plg/8');
INSERT INTO Medecines (cip, name) VALUES ('3400935955838', 'DOLIPRANE 1000 mg Cpr Plq/8');
INSERT INTO Medecines (cip, name) VALUES ('3400932779727', 'Hexaspray, collutoire en flacon pressurisé');
INSERT INTO Medecines (cip, name) VALUES ('3400930495674', 'HEXOMEDINE TRANSCUTANEE 1,5 POUR MILLE');
INSERT INTO Medecines (cip, name) VALUES ('3400932812585', 'CUTACNYL 10 POUR CENT');
INSERT INTO Medecines (cip, name) VALUES ('3400931762775', 'EFFEDERM 0,05 %');
