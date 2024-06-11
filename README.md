# ScanMed (back-end)
Ce dépôt héberge les sources du back-end du projet **ScanMed**, objet de la SAÉ S4.01 de l'IUT Paris - Rives de Seine pour l'année 2023-2024.  
Cette SAÉ consiste en la création d'une application permettant le signalement de pénuries de médicaments sensibles au sein d'une agglomération, et le suivi de ces signalements.
Elle se décompose en plusieurs axes, allant du développement front-end au développement back-end et en passant par la sécurité et du réseau aussi.

## Technologies utilisées
- Java 17 (liberica-full)
- Spring Boot
- La base de données a été développée pour PostgreSQL mais peut sûrement être adaptée pour d'autres SGDB sans soucis

## Fonctionnalités
### API REST
Le back-end consiste en une API REST complète, permettant de :
- se créer un compte
- se connecter
- accéder à la liste des villes (et pharmacies dans les dites villes)
- création de signalement
- accès au nom de médicament via le renseignement de leur CIP (tant qu'il est dans la base de données)
- partie administrateur permettant de notamment avoir la liste de tous les rapports indépendement de l'utilisateur, ainsi que de modifier l'état des rapports  
### Mailing
Un système de mailing est aussi implémenté, permettant notamment la réinitialisation de mot de passe utilisateurs et la notification en temps réel des changements d'état de leur rapports.
### Authentification
L'authentification est implémentée à partir de clef API aléatoires, couplées aux emails des utilisateurs afin de les reconnaître. Il est possible de faire autant de clef API que nous le voulons tant qu'elles ont un nom d'appareil différent des autres clefs générées par le dit utilisateur. Elles n'ont pas de durée limitée dans le temps mais peuvent tout de même être annulées à souhait.
