# 🏦 Analyse des Transactions Bancaires et Détection des Anomalies - Java Console

## 📌 Description du projet
Ce projet est une application console en **Java** qui permet de gérer des **clients, comptes bancaires et transactions**, tout en offrant des outils d’analyse pour **détecter des anomalies** (transactions suspectes, comptes inactifs, comportements inhabituels).  

L’objectif est de pratiquer la **programmation orientée objet (POO)** avec Java, la gestion des exceptions, l’utilisation des **DAO / Services**, ainsi que l’exploitation des **Streams API** pour les analyses et statistiques.  

---

## 🏗 Structure du projet
```bash
Analyse des transactions bancaires/
├── src/
│   ├── ui/
│   │   └── ConsoleUI.java
│   ├── dao/
│   │   ├── ClientDAO.java
│   │   ├── CompteDAO.java
│   │   └── TransactionDAO.java
│   │   ├── ClientDAOImpl.java
│   │   ├── CompteDAOImpl.java
│   │   └── TransactionDAOImpl.java
│   ├── entity/
│   │   ├── Client.java
│   │   ├── Compte.java        # sealed class
│   │   ├── CompteCourant.java
│   │   ├── CompteEpargne.java
│   │   └── Transaction.java
│   ├── enums/
│   │   └── TypeTransaction.java
│   ├── service/
│   │   ├── ClientService.java
│   │   ├── CompteService.java
│   │   ├── TransactionService.java
│   │   └── RapportService.java
│   │   ├── ClientServiceImpl.java
│   │   ├── CompteServiceImpl.java
│   │   ├── TransactionServiceImpl.java
│   │   └── RapportServiceImpl.java
│   └── util/
│       ├── DateUtil.java
│       └── ValidationUtil.java
└── Main.java
```
# ⚙️ Fonctionnalités
## Gestion des clients

Ajouter, modifier, supprimer un client.

Rechercher un client par ID ou nom.

Lister tous les clients.

Générer des statistiques clients :

Nombre total de clients.

Solde total détenu.

## Gestion des comptes

Créer un compte courant (avec découvert autorisé).

Créer un compte épargne (avec taux d’intérêt).

Rechercher les comptes d’un client.

Mettre à jour les soldes et paramètres.

Trouver le compte avec le solde max/min.

## Gestion des transactions

Enregistrer une transaction (versement, retrait, virement).

Consulter l’historique des transactions par compte.

Filtrer les transactions par date, montant, type ou lieu.

Regrouper les transactions par type ou période.

Calculer les totaux et moyennes par client ou compte.

Détecter des transactions suspectes :

Montant > 10 000 €.

Lieu inhabituel.

Fréquence excessive en moins d’une minute.

## Rapports et analyses

Top 5 des clients par solde.

Rapport mensuel des transactions (nombre + volume).

Détection des comptes inactifs depuis une période.

Génération d’alertes :

Solde bas.

Inactivité prolongée.

Transactions suspectes.

#🛠 Technologies utilisées

Java 17 (Records & Sealed classes)

DAO / Service

JDBC avec PostgreSQL ou MySQL

Collections & Streams API

LocalDateTime pour la gestion des dates

Scanner (CLI) pour l’interaction utilisateur
