-- Released by : Giovanny YORO et Binh Minh NGUYEN
-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 04, 2022 at 11:40 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bibliothequebd`
--

-- --------------------------------------------------------

--
-- Table structure for table `abonne`
--

DROP TABLE IF EXISTS `abonne`;
CREATE TABLE IF NOT EXISTS `abonne` (
  `numUser` int(11) NOT NULL AUTO_INCREMENT,
  `nameUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `loginUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `passwordUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `birthDate` date NOT NULL,
  `etudiant` tinyint(1) NOT NULL DEFAULT 0,
  `abonnementActif` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`numUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `bibliothecaire`
--

DROP TABLE IF EXISTS `bibliothecaire`;
CREATE TABLE IF NOT EXISTS `bibliothecaire` (
  `numUser` int(11) NOT NULL AUTO_INCREMENT,
  `nameUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `loginUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `passwordUser` varchar(50) COLLATE utf8_bin NOT NULL,
  `dateEmbauche` date NOT NULL,
  PRIMARY KEY (`numUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

--Méthode ascendante pour représenter les types de documents
CREATE TABLE IF NOT EXISTS `document`(
    `idDoc` integer not null PRIMARY KEY AUTO_INCREMENT,
    `typeDoc` INTEGER NOT NULL,
    `titre` VARCHAR(100) not null,
    `auteur` VARCHAR(100 not null),
    `pourAdulte` tinyint(1),
    `pourEtudiant` tinyint(1),
    `emprunte` tinyint(1) NOT NULL DEFAULT 0,
    `idAbonne` int(11),
    FOREIGN KEY(`idAbonne`) REFERENCES `abonne`(`numUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `document`
    ADD CONSTRAINT FK_typeDoc FOREIGN KEY (`typeDocument`) REFERENCES `typeDocument`(`numeroType`);

CREATE TABLE IF NOT EXISTS `typeDocument`(
    idType integer not null PRIMARY KEY AUTO_INCREMENT,
    nomType VARCHAR(30) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`idDoc`, `typeDoc`, `titre`, `auteur`, `pourAdulte`, `pourEtudiant`, `emprunte`, `idAbonne`) VALUES
(1, 1, 'Hello', 'Isami Kondo', 0, NULL, 0, NULL),
(2, 1, 'Coucou', 'Roger Federer', 0, NULL, 1, 1),
(3, 1, 'Lolo', 'Sam Dupont', 1, NULL, 0, NULL),
(4, 1, 'Lalala','Michael Moore', 1, NULL, 1, 2),
(5, 2, 'Sapien','Victo Hugo', NULL, 1,1,3),
(6, 2, 'Elon Musk', 'Donald Trump', NULL,0,0, NULL),
(7, 2, 'Etranger', 'Denis Depardieu', NULL, 1, 1, 4),
(8, 2, 'Think rich grow rich', 'Harry Windsor', NULL, 0, 1, 4),
(9, 3, 'Lalaland', 'Marilyn Monroe',NULL, NULL, 0, NULL),
(10, 3, 'Music', 'Tupac Shakur',NULL, NULL, 1, 3);


INSERT INTO `typeDoc`(`idType`, `nomType`) VALUES
(1, 'DVD'),
(2, 'Livre'),
(3, 'CD');

--- La vue utilisateur

DROP VIEW V_UTILISATEUR;
CREATE VIEW V_UTILISATEUR (DTYPE, numUser, nameUser, loginUser, passwordUser, birthDate,etudiant, abonnementActif, dateEmbauche)
AS 
SELECT 'ABONNE', numUser, nameUser ,loginUser, passwordUser, birthDate , etudiant, abonnementActif, NULL
FROM `abonne`
UNION ALL
SELECT 'BIBLIOTHECAIRE', numUser, nameUser ,loginUser, passwordUser, NULL , NULL, NULL, dateEmbauche
FROM `bibliothecaire`
/

CREATE OR REPLACE TRIGGER T_V_UTILISATEUR
INSTEAD OF INSERT ON V_UTILISATEUR
BEGIN
    IF :NEW.DTYPE = 'ABONNE' THEN
    INSERT INTO `abonne`(numUser, loginUser, nameUser ,passwordUser, birthDate , etudiant, abonnementActif) VALUES
    (:NEW.numUser, :NEW.loginUser, :NEW.passwordUser, :NEW.birthDate , :NEW.etudiant, :NEW.abonnementActif);
    ELSE
    INSERT INTO `bibliothecaire`(numUser, nameUser ,loginUser, passwordUser, dateEmbauche) VALUES 
    (:NEW.numUser, :NEW.loginUser, :NEW.passwordUser, :NEW.dateEmbauche);
    END IF;
END;
/

--- Les triggers

CREATE OR REPLACE TRIGGER T_PARTITION_ABONNE
BEFORE INSERT ON ABONNE
FOR EACH ROW
DECLARE
ErreurPartition EXCEPTION;
var INT;
BEGIN
SELECT numUser INTO var FROM bibliothecaire WHERE numUser = :NEW.numUser;
RAISE ErreurPartition;
EXCEPTION
WHEN NO_DATA_FOUND THEN
NULL;
WHEN ErreurPartition THEN
RAISE_APPLICATION_ERROR(-20001, 'Erreur : ce numero existe deja dans la table BIBLIOTHECAIRE');
END;
/

CREATE OR REPLACE TRIGGER T_PARTITION_BIBLIOTHECAIRE
BEFORE INSERT ON BIBLIOTHECAIRE
FOR EACH ROW
DECLARE
ErreurPartition EXCEPTION;
var INT;
BEGIN
SELECT numUser INTO var FROM abonne WHERE numUser = :NEW.numUser;
RAISE ErreurPartition;
EXCEPTION
WHEN NO_DATA_FOUND THEN
NULL;
WHEN ErreurPartition THEN
RAISE_APPLICATION_ERROR(-20001, 'Erreur : ce numero existe deja dans la table ABONNE');
END;
/

--
-- Dumping data for table `abonne`
--
INSERT INTO V_UTILISATEUR (DTYPE, numUser, nameUser, loginUser, passwordUser, birthDate, etudiant, abonnementActif)
VALUES 
('ABONNE', NULL,'Terese McGarden','tagada52', 'zouzoubzoub', '2001-11-13', 0, 0),
('ABONNE', NULL, 'Harry Peter','haribo875', 'superG95', '2002-05-13', 0, 1),
('ABONNE', NULL, 'John Robert','avengersForever', 'GreatMaxN40RE', '1999-11-16', 1, 0),
('ABONNE', NULL, 'Scarlett Johnson' ,'Terminator3000', 'zouzoubzoub', '1998-07-03', 1, 1);

--
-- Dumping data for table `bibliothecaire`
--

INSERT INTO V_UTILISATEUR (DTYPE, numUser, nameUser ,loginUser, passwordUser, dateEmbauche) VALUES 
('BIBLIOTHECAIRE', NULL , 'Patrick Bernard',  'BahAlors', 'elleESTpasBelleLaVIe', '2016-05-08'),
('BIBLIOTHECAIRE', NULL , 'Angélique Pierson', 'BiblioTutrice' , 'hacktrackspy7000', '2022-01-17');