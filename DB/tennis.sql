-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 05 avr. 2023 à 08:07
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tennis`
--

-- --------------------------------------------------------

--
-- Structure de la table `connexion`
--

DROP TABLE IF EXISTS `connexion`;
CREATE TABLE IF NOT EXISTS `connexion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `profil` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `connexion_id_idx` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `connexion`
--

INSERT INTO `connexion` (`id`, `login`, `password`, `profil`) VALUES
(2, 'admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 1),
(3, 'user', '8d23cf6c86e834a7aa6eded54c26ce2bb2e74903538c61bdd5d2197997ab2f72', 2);

-- --------------------------------------------------------

--
-- Structure de la table `epreuve`
--

DROP TABLE IF EXISTS `epreuve`;
CREATE TABLE IF NOT EXISTS `epreuve` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `ANNEE` smallint NOT NULL,
  `TYPE_EPREUVE` char(1) NOT NULL,
  `ID_TOURNOI` bigint NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_TOURNOI` (`ID_TOURNOI`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `epreuve`
--

INSERT INTO `epreuve` (`ID`, `ANNEE`, `TYPE_EPREUVE`, `ID_TOURNOI`) VALUES
(1, 2019, 'H', 1),
(2, 2018, 'H', 1),
(3, 2017, 'H', 1),
(4, 2016, 'H', 1),
(5, 2015, 'H', 1),
(6, 2014, 'H', 1),
(7, 2013, 'H', 1),
(8, 2012, 'H', 1),
(9, 2011, 'H', 1),
(10, 2010, 'H', 1),
(11, 2018, 'H', 2),
(12, 2017, 'H', 2),
(13, 2016, 'H', 2),
(14, 2015, 'H', 2),
(15, 2014, 'H', 2),
(16, 2013, 'H', 2),
(17, 2012, 'H', 2),
(18, 2011, 'H', 2),
(19, 2010, 'H', 2),
(20, 2018, 'H', 3),
(21, 2017, 'H', 3),
(22, 2016, 'H', 3),
(23, 2015, 'H', 3),
(24, 2014, 'H', 3),
(25, 2013, 'H', 3),
(26, 2012, 'H', 3),
(27, 2011, 'H', 3),
(28, 2010, 'H', 3),
(29, 2018, 'H', 4),
(30, 2017, 'H', 4),
(31, 2016, 'H', 4),
(32, 2015, 'H', 4),
(33, 2014, 'H', 4),
(34, 2013, 'H', 4),
(35, 2012, 'H', 4),
(36, 2011, 'H', 4),
(37, 2010, 'H', 4),
(38, 2018, 'F', 1),
(39, 2017, 'F', 1),
(40, 2016, 'F', 1),
(41, 2015, 'F', 1),
(42, 2014, 'F', 1),
(43, 2013, 'F', 1),
(44, 2012, 'F', 1),
(45, 2011, 'F', 1),
(46, 2010, 'F', 1),
(47, 2018, 'F', 2),
(48, 2017, 'F', 2),
(49, 2016, 'F', 2),
(50, 2015, 'F', 2),
(51, 2014, 'F', 2),
(52, 2013, 'F', 2),
(53, 2012, 'F', 2),
(54, 2011, 'F', 2),
(55, 2010, 'F', 2),
(56, 2018, 'F', 3),
(57, 2017, 'F', 3),
(58, 2016, 'F', 3),
(59, 2015, 'F', 3),
(60, 2014, 'F', 3),
(61, 2013, 'F', 3),
(62, 2012, 'F', 3),
(63, 2011, 'F', 3),
(64, 2010, 'F', 3),
(65, 2018, 'F', 4),
(66, 2017, 'F', 4),
(67, 2016, 'F', 4),
(68, 2015, 'F', 4),
(69, 2014, 'F', 4),
(70, 2013, 'F', 4),
(71, 2012, 'F', 4),
(72, 2011, 'F', 4),
(73, 2010, 'F', 4);

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

DROP TABLE IF EXISTS `joueur`;
CREATE TABLE IF NOT EXISTS `joueur` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NOM` varchar(20) NOT NULL,
  `PRENOM` varchar(20) DEFAULT NULL,
  `SEXE` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `joueur`
--

INSERT INTO `joueur` (`ID`, `NOM`, `PRENOM`, `SEXE`) VALUES
(1, 'Wozniacki', 'Caroline', 'F'),
(2, 'Halep', 'Simona', 'F'),
(3, 'Williams', 'Serena', 'F'),
(4, 'Williams', 'Venus', 'F'),
(5, 'Kerber', 'Angelique', 'F'),
(6, 'Sharapova', 'Maria', 'F'),
(7, 'Li', 'Na', 'F'),
(8, 'Cibulková', 'Dominika', 'F'),
(9, 'Azarenka', 'Victoria', 'F'),
(10, 'Clijsters', 'Kim', 'F'),
(11, 'Henin', 'Justine', 'F'),
(12, 'Stephens', 'Sloane', 'F'),
(13, 'Keys', 'Madison', 'F'),
(14, 'Muguruza', 'Garbiñe', 'F'),
(15, 'Ostapenko', 'Jelena', 'F'),
(16, 'Plíšková', 'Karolína', 'F'),
(17, 'Pennetta', 'Flavia', 'F'),
(18, 'Vinci', 'Roberta', 'F'),
(19, 'Šafářová', 'Lucie', 'F'),
(20, 'Bouchard', 'Eugenie', 'F'),
(21, 'Bartoli', 'Marion', 'F'),
(22, 'Lisicki', 'Sabine', 'F'),
(23, 'Radwańska', 'Agnieszka', 'F'),
(24, 'Errani', 'Sarah', 'F'),
(25, 'Stosur', 'Samantha', 'F'),
(26, 'Schiavone', 'Francesca', 'F'),
(27, 'Zvonareva', 'Vera', 'F'),
(28, 'Kvitova', 'Petra', 'F'),
(29, 'Osaka', 'Naomi', 'F'),
(30, 'Djokovic', 'Novak', 'H'),
(31, 'Nadal', 'Rafael', 'H'),
(32, 'Federer', 'Roger', 'H'),
(33, 'Čilić', 'Marin', 'H'),
(34, 'Murray', 'Andy', 'H'),
(35, 'Wawrinka', 'Stan', 'H'),
(36, 'Del Potro', 'Juan Martín', 'H'),
(37, 'Thiem', 'Dominic', 'H'),
(39, 'Raonic', 'Milos', 'H'),
(40, 'Nishikori', 'Kei', 'H'),
(41, 'Ferrer', 'David', 'H'),
(42, 'Berdych', 'Tomáš', 'H'),
(43, 'Söderling', 'Robin', 'H'),
(99, 'Toto', 'Titi', 'H'),
(102, 'Ttttt', 'Fwsdfhbsf', 'H');

-- --------------------------------------------------------

--
-- Structure de la table `match_tennis`
--

DROP TABLE IF EXISTS `match_tennis`;
CREATE TABLE IF NOT EXISTS `match_tennis` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `ID_EPREUVE` bigint DEFAULT NULL,
  `ID_VAINQUEUR` bigint DEFAULT NULL,
  `ID_FINALISTE` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_VAINQUEUR` (`ID_VAINQUEUR`),
  KEY `ID_FINALISTE` (`ID_FINALISTE`),
  KEY `ID_EPREUVE` (`ID_EPREUVE`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `match_tennis`
--

INSERT INTO `match_tennis` (`ID`, `ID_EPREUVE`, `ID_VAINQUEUR`, `ID_FINALISTE`) VALUES
(1, 19, 31, 43),
(2, 28, 31, 42),
(3, 37, 31, 30),
(4, 9, 30, 34),
(5, 18, 31, 32),
(6, 27, 30, 31),
(7, 36, 30, 31),
(8, 8, 30, 31),
(9, 17, 31, 30),
(10, 26, 32, 34),
(11, 35, 34, 30),
(12, 7, 30, 34),
(13, 16, 31, 41),
(14, 25, 34, 30),
(15, 34, 31, 30),
(16, 6, 35, 31),
(17, 15, 31, 30),
(18, 24, 30, 32),
(19, 33, 33, 40),
(20, 5, 30, 34),
(21, 14, 35, 30),
(22, 23, 30, 32),
(23, 32, 30, 32),
(24, 4, 30, 34),
(25, 13, 30, 34),
(26, 22, 34, 39),
(27, 31, 35, 30),
(28, 3, 32, 31),
(29, 12, 31, 35),
(30, 21, 32, 33),
(32, 2, 32, 33),
(33, 11, 31, 37),
(35, 29, 30, 36),
(36, 46, 3, 11),
(37, 55, 26, 25),
(38, 64, 3, 27),
(39, 73, 10, 27),
(40, 45, 10, 7),
(41, 54, 7, 26),
(42, 63, 28, 6),
(43, 72, 25, 3),
(44, 44, 9, 6),
(45, 53, 6, 24),
(46, 62, 3, 23),
(47, 71, 3, 9),
(48, 43, 9, 7),
(49, 52, 3, 6),
(50, 61, 21, 22),
(51, 70, 3, 9),
(52, 42, 7, 8),
(53, 51, 6, 2),
(54, 60, 28, 20),
(55, 69, 3, 1),
(56, 41, 3, 6),
(57, 50, 3, 19),
(58, 59, 3, 14),
(59, 68, 17, 18),
(60, 40, 5, 3),
(61, 49, 14, 3),
(62, 58, 3, 5),
(63, 67, 5, 16),
(64, 39, 3, 4),
(65, 48, 15, 2),
(66, 57, 14, 4),
(67, 66, 12, 13),
(68, 38, 1, 2),
(69, 47, 2, 12),
(70, 56, 5, 3),
(71, 65, 29, 3);

-- --------------------------------------------------------

--
-- Structure de la table `score_vainqueur`
--

DROP TABLE IF EXISTS `score_vainqueur`;
CREATE TABLE IF NOT EXISTS `score_vainqueur` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `ID_MATCH` bigint NOT NULL,
  `SET_1` tinyint DEFAULT NULL,
  `SET_2` tinyint DEFAULT NULL,
  `SET_3` tinyint DEFAULT NULL,
  `SET_4` tinyint DEFAULT NULL,
  `SET_5` tinyint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_MATCH` (`ID_MATCH`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `score_vainqueur`
--

INSERT INTO `score_vainqueur` (`ID`, `ID_MATCH`, `SET_1`, `SET_2`, `SET_3`, `SET_4`, `SET_5`) VALUES
(1, 1, 4, 2, 4, NULL, NULL),
(2, 2, 3, 5, 4, NULL, NULL),
(3, 3, 4, -5, 4, 2, NULL),
(4, 4, 4, 2, 3, NULL, NULL),
(5, 5, 5, 6, -5, 1, NULL),
(6, 6, 4, 1, -1, 3, NULL),
(7, 7, 2, 4, -6, 1, NULL),
(8, 8, -5, 4, 2, -6, 5),
(9, 9, 4, 3, -2, 5, NULL),
(10, 10, -4, 5, 3, 4, NULL),
(11, 11, 6, 5, -2, -3, 2),
(12, 12, -6, 6, 3, 2, NULL),
(13, 13, 3, 2, 3, NULL, NULL),
(14, 14, 4, 5, 4, NULL, NULL),
(15, 15, 2, -3, 4, 1, NULL),
(16, 16, 3, 2, -3, 3, NULL),
(17, 17, -3, 5, 2, 4, NULL),
(18, 18, -6, 4, 6, -5, 4),
(19, 19, 3, 3, 3, NULL, NULL),
(20, 20, 6, -6, 3, 0, NULL),
(21, 21, -4, 4, 3, 4, NULL),
(22, 22, 6, -6, 4, 3, NULL),
(23, 23, 4, -5, 4, 4, NULL),
(24, 24, 1, 5, 6, NULL, NULL),
(25, 25, -3, 1, 2, 4, NULL),
(26, 26, 4, 6, 6, NULL, NULL),
(27, 27, -6, 4, 5, 3, NULL),
(28, 28, 4, -3, 1, -3, 3),
(29, 29, 2, 3, 1, NULL, NULL),
(30, 30, 3, 1, 4, NULL, NULL),
(32, 32, 2, -6, 3, -3, 1),
(33, 33, 4, 3, 2, NULL, NULL),
(35, 35, 3, 2, 3, NULL, NULL),
(36, 36, 4, -3, 2, NULL, NULL),
(37, 37, 4, 6, NULL, NULL, NULL),
(38, 38, 3, 2, NULL, NULL, NULL),
(39, 39, 2, 1, NULL, NULL, NULL),
(40, 40, -3, 3, 3, NULL, NULL),
(41, 41, 4, 6, NULL, NULL, NULL),
(42, 42, 3, 4, NULL, NULL, NULL),
(43, 43, 2, 3, NULL, NULL, NULL),
(44, 44, 3, 0, NULL, NULL, NULL),
(45, 45, 3, 2, NULL, NULL, NULL),
(46, 46, 1, -5, 2, NULL, NULL),
(47, 47, 2, -2, 5, NULL, NULL),
(48, 48, -4, 4, 3, NULL, NULL),
(49, 49, 4, 4, NULL, NULL, NULL),
(50, 50, 1, 4, NULL, NULL, NULL),
(51, 51, 5, -6, 1, NULL, NULL),
(52, 52, 6, 0, NULL, NULL, NULL),
(53, 53, 4, -6, 4, NULL, NULL),
(54, 54, 3, 0, NULL, NULL, NULL),
(55, 55, 3, 3, NULL, NULL, NULL),
(56, 56, 3, 6, NULL, NULL, NULL),
(57, 57, 3, -6, 2, NULL, NULL),
(58, 58, 4, 4, NULL, NULL, NULL),
(59, 59, 6, 2, NULL, NULL, NULL),
(60, 60, 4, -3, 4, NULL, NULL),
(61, 61, 5, 4, NULL, NULL, NULL),
(62, 62, 5, 3, NULL, NULL, NULL),
(63, 63, 3, -4, 4, NULL, NULL),
(64, 64, 4, 4, NULL, NULL, NULL),
(65, 65, -4, 4, 3, NULL, NULL),
(66, 66, 5, 0, NULL, NULL, NULL),
(67, 67, 3, 0, NULL, NULL, NULL),
(68, 68, 6, -3, 4, NULL, NULL),
(69, 69, -3, 4, 1, NULL, NULL),
(70, 70, 3, 3, NULL, NULL, NULL),
(71, 71, 2, 4, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `tournoi`
--

DROP TABLE IF EXISTS `tournoi`;
CREATE TABLE IF NOT EXISTS `tournoi` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NOM` varchar(20) NOT NULL,
  `CODE` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `tournoi`
--

INSERT INTO `tournoi` (`ID`, `NOM`, `CODE`) VALUES
(1, 'Australian Open', 'AO'),
(2, 'Roland Garros', 'RG'),
(3, 'Wimbledon', 'WI'),
(4, 'US Open', 'US'),
(12, 'Moselle Open', 'MO');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `epreuve`
--
ALTER TABLE `epreuve`
  ADD CONSTRAINT `epreuve_ibfk_1` FOREIGN KEY (`ID_TOURNOI`) REFERENCES `tournoi` (`ID`);

--
-- Contraintes pour la table `match_tennis`
--
ALTER TABLE `match_tennis`
  ADD CONSTRAINT `match_tennis_ibfk_1` FOREIGN KEY (`ID_VAINQUEUR`) REFERENCES `joueur` (`ID`),
  ADD CONSTRAINT `match_tennis_ibfk_2` FOREIGN KEY (`ID_FINALISTE`) REFERENCES `joueur` (`ID`),
  ADD CONSTRAINT `match_tennis_ibfk_3` FOREIGN KEY (`ID_EPREUVE`) REFERENCES `epreuve` (`ID`);

--
-- Contraintes pour la table `score_vainqueur`
--
ALTER TABLE `score_vainqueur`
  ADD CONSTRAINT `score_vainqueur_ibfk_1` FOREIGN KEY (`ID_MATCH`) REFERENCES `match_tennis` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
