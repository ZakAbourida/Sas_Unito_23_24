-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Giu 10, 2024 alle 01:07
-- Versione del server: 5.7.24
-- Versione PHP: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `catering`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `assignment`
--

CREATE TABLE `assignment` (
  `id` int(11) NOT NULL,
  `sheet` int(11) NOT NULL,
  `turn` int(11) NOT NULL,
  `cook` int(11) NOT NULL,
  `recipe` int(11) NOT NULL,
  `time` int(11) DEFAULT NULL,
  `portion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL,
  `organizer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `events`
--

INSERT INTO `events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`) VALUES
(1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2),
(2, 'Compleanno di Manuela', '2020-08-13', '2020-08-13', 25, 2),
(3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `extratask`
--

CREATE TABLE `extratask` (
  `id` int(11) NOT NULL,
  `sheet` int(11) NOT NULL,
  `task` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `menufeatures`
--

CREATE TABLE `menufeatures` (
  `menu_id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menufeatures`
--

INSERT INTO `menufeatures` (`menu_id`, `name`, `value`) VALUES
(1, 'Richiede cuoco', 1),
(1, 'Buffet', 0),
(1, 'Richiede cucina', 1),
(1, 'Finger food', 0),
(1, 'Piatti caldi', 1),
(2, 'Richiede cuoco', 0),
(2, 'Buffet', 1),
(2, 'Richiede cucina', 0),
(2, 'Finger food', 1),
(2, 'Piatti caldi', 1),
(3, 'Richiede cuoco', 1),
(3, 'Buffet', 0),
(3, 'Richiede cucina', 0),
(3, 'Finger food', 1),
(3, 'Piatti caldi', 0),
(4, 'Richiede cuoco', 0),
(4, 'Buffet', 1),
(4, 'Richiede cucina', 0),
(4, 'Finger food', 1),
(4, 'Piatti caldi', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `menuitems`
--

CREATE TABLE `menuitems` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `description` tinytext,
  `recipe_id` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menuitems`
--

INSERT INTO `menuitems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`) VALUES
(1, 1, 1, 'Vitello Tonnato', 1, 1),
(2, 1, 1, 'Carpaccio di Spada', 2, 2),
(3, 1, 2, 'Alici Marinate', 3, 1),
(4, 1, 2, 'Insalata di Riso', 4, 2),
(5, 1, 2, 'Pasta alla Carbonara', 10, 3),
(6, 1, 3, 'Panna al Guscio di Baccalà', 5, 1),
(7, 1, 3, 'Pollo al Curry', 11, 2),
(8, 1, 3, 'Filetto di Manzo', 16, 3),
(9, 1, 4, 'Tiramisù', 9, 1),
(10, 1, 4, 'Mousse al Cioccolato', 13, 2),
(11, 1, 5, 'Caffè', 19, 1),
(12, 1, 5, 'Tè', 20, 2),
(13, 1, 5, 'Vino Rosso', 23, 3),
(14, 1, 5, 'Vino Bianco', 24, 4),
(15, 2, 6, 'Bruschette al Pomodoro', 18, 1),
(16, 2, 6, 'Insalata Caprese', 14, 2),
(17, 2, 7, 'Lasagna', 12, 1),
(18, 2, 7, 'Gnocchi alla Sorrentina', 15, 2),
(19, 2, 8, 'Pappa al Pomodoro', 6, 1),
(20, 2, 8, 'Hamburger con Bacon e Cipolla Caramellata', 7, 2),
(21, 2, 8, 'Salmone al Forno', 8, 3),
(22, 2, 8, 'Zuppa di Pesce', 17, 4),
(23, 2, 9, 'Tiramisù', 9, 1),
(24, 2, 9, 'Mousse al Cioccolato', 13, 2),
(25, 2, 10, 'Vino Rosso', 23, 1),
(26, 2, 10, 'Vino Bianco', 24, 2),
(27, 2, 10, 'Spremuta di Limone', 25, 3),
(28, 2, 10, 'Cocktail Analcolico', 26, 4),
(29, 3, 12, 'Vitello Tonnato', 1, 1),
(30, 3, 12, 'Insalata di Riso', 4, 2),
(31, 3, 12, 'Pasta alla Carbonara', 10, 3),
(32, 3, 12, 'Lasagna', 12, 4),
(33, 3, 13, 'Tiramisù', 9, 1),
(34, 3, 13, 'Mousse al Cioccolato', 13, 2),
(35, 3, 11, 'Caffè', 19, 1),
(36, 3, 11, 'Tè', 20, 2),
(37, 3, 11, 'Succo d\'arancia', 21, 3),
(38, 3, 11, 'Acqua Minerale', 22, 4),
(41, 4, 15, 'Bruschette al Pomodoro', 18, 1),
(42, 4, 15, 'Insalata Caprese', 14, 2),
(43, 4, 14, 'Caffè', 19, 3),
(44, 4, 14, 'Tè', 20, 4),
(45, 4, 14, 'Succo d\'arancia', 21, 5),
(46, 4, 14, 'Cocktail Analcolico', 26, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `menus`
--

CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `title` tinytext,
  `owner_id` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menus`
--

INSERT INTO `menus` (`id`, `title`, `owner_id`, `published`) VALUES
(1, 'Pranzo di Lavoro', 2, 1),
(2, 'Cena Sociale', 2, 1),
(3, 'Colazione di Lavoro', 2, 1),
(4, 'Coffee Break', 2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `menusections`
--

CREATE TABLE `menusections` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `name` tinytext,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menusections`
--

INSERT INTO `menusections` (`id`, `menu_id`, `name`, `position`) VALUES
(1, 1, 'Antipasti', NULL),
(2, 1, 'Primi Piatti', NULL),
(3, 1, 'Secondi Piatti', NULL),
(4, 1, 'Dessert', NULL),
(5, 1, 'Bevande', NULL),
(6, 2, 'Antipasti', NULL),
(7, 2, 'Primi Piatti', NULL),
(8, 2, 'Secondi Piatti', NULL),
(9, 2, 'Dessert', NULL),
(10, 2, 'Bevande', NULL),
(11, 3, 'Bevande', NULL),
(12, 3, 'Colazioni', NULL),
(13, 3, 'Dolci', NULL),
(14, 4, 'Bevande', NULL),
(15, 4, 'Snack', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `recipes`
--

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL,
  `name` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `recipes`
--

INSERT INTO `recipes` (`id`, `name`) VALUES
(1, 'Vitello tonnato'),
(2, 'Carpaccio di spada'),
(3, 'Alici marinate'),
(4, 'Insalata di riso'),
(5, 'Penne al sugo di baccalà'),
(6, 'Pappa al pomodoro'),
(7, 'Hamburger con bacon e cipolla caramellata'),
(8, 'Salmone al forno'),
(9, 'Tiramisù'),
(10, 'Pasta alla Carbonara'),
(11, 'Pollo al Curry'),
(12, 'Lasagna'),
(13, 'Mousse al Cioccolato'),
(14, 'Insalata Caprese'),
(15, 'Gnocchi alla Sorrentina'),
(16, 'Filetto di Manzo'),
(17, 'Zuppa di Pesce'),
(18, 'Bruschette al Pomodoro'),
(19, 'Caffè'),
(20, 'Tè'),
(21, 'Succo d\'arancia'),
(22, 'Acqua Minerale'),
(23, 'Vino Rosso'),
(24, 'Vino Bianco'),
(25, 'Spremuta di Limone'),
(26, 'Cocktail Analcolico');

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
('c', 'cuoco'),
('h', 'chef'),
('o', 'organizzatore'),
('s', 'servizio');

-- --------------------------------------------------------

--
-- Struttura della tabella `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int(11) NOT NULL DEFAULT '0',
  `approved_menu_id` int(11) DEFAULT '0',
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `services`
--

INSERT INTO `services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`) VALUES
(1, 2, 'Cena', 2, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
(2, 1, 'Coffee break mattino', 4, 0, '2020-09-25', '10:30:00', '11:30:00', 100),
(3, 1, 'Colazione di lavoro', 3, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
(4, 1, 'Coffee break pomeriggio', 4, 0, '2020-09-25', '16:00:00', '16:30:00', 100),
(5, 1, 'Cena sociale', 2, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
(6, 3, 'Pranzo giorno 1', 1, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
(7, 3, 'Pranzo giorno 2', 1, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
(8, 3, 'Pranzo giorno 3', 1, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- --------------------------------------------------------

--
-- Struttura della tabella `summarysheet`
--

CREATE TABLE `summarysheet` (
  `id` int(11) NOT NULL,
  `owner` int(11) NOT NULL,
  `menu` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `turn`
--

CREATE TABLE `turn` (
  `id` int(11) NOT NULL,
  `service` int(11) NOT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `location` varchar(45) DEFAULT 'SEDE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `turn`
--

INSERT INTO `turn` (`id`, `service`, `date`, `start`, `end`, `location`) VALUES
(1, 1, '2020-09-25', '20:00:00', '22:00:00', 'SEDE'),
(2, 1, '2020-09-25', '22:00:00', '00:00:00', 'SEDE'),
(3, 1, '2020-09-25', '00:00:00', '02:00:00', 'SEDE'),
(4, 2, '2020-09-25', '10:00:00', '12:00:00', 'SEDE'),
(5, 2, '2020-09-25', '12:00:00', '14:00:00', 'SEDE'),
(6, 2, '2020-09-25', '14:00:00', '16:00:00', 'SEDE'),
(7, 3, '2020-09-25', '13:00:00', '15:00:00', 'SEDE'),
(8, 3, '2020-09-25', '15:00:00', '17:00:00', 'SEDE'),
(9, 3, '2020-09-25', '17:00:00', '19:00:00', 'SEDE'),
(10, 4, '2020-09-25', '16:00:00', '18:00:00', 'SEDE'),
(11, 4, '2020-09-25', '18:00:00', '20:00:00', 'SEDE'),
(12, 4, '2020-09-25', '20:00:00', '22:00:00', 'SEDE'),
(13, 5, '2020-10-02', '22:00:00', '00:00:00', 'SEDE'),
(14, 5, '2020-10-02', '00:00:00', '02:00:00', 'SEDE'),
(15, 5, '2020-10-02', '02:00:00', '04:00:00', 'SEDE'),
(16, 6, '2020-10-03', '12:00:00', '14:00:00', 'SEDE'),
(17, 6, '2020-10-03', '14:00:00', '16:00:00', 'SEDE'),
(18, 6, '2020-10-03', '16:00:00', '18:00:00', 'SEDE'),
(19, 7, '2020-10-04', '12:00:00', '14:00:00', 'SEDE'),
(20, 7, '2020-10-04', '14:00:00', '16:00:00', 'SEDE'),
(21, 7, '2020-10-04', '16:00:00', '18:00:00', 'SEDE');

-- --------------------------------------------------------

--
-- Struttura della tabella `userroles`
--

CREATE TABLE `userroles` (
  `user_id` int(11) NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `userroles`
--

INSERT INTO `userroles` (`user_id`, `role_id`) VALUES
(1, 'o'),
(2, 'o'),
(2, 'h'),
(3, 'h'),
(4, 'h'),
(4, 'c'),
(5, 'c'),
(6, 'c'),
(7, 'c'),
(8, 's'),
(9, 's'),
(10, 's'),
(7, 's');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(128) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`id`, `username`) VALUES
(1, 'Carlin'),
(2, 'Lidia'),
(3, 'Tony'),
(4, 'Marinella'),
(5, 'Guido'),
(6, 'Antonietta'),
(7, 'Paola'),
(8, 'Silvia'),
(9, 'Marco'),
(10, 'Piergiorgio');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `assignment_sheet_fk` (`sheet`),
  ADD KEY `assignment_turn_fk` (`turn`),
  ADD KEY `assignment_cook_fk` (`cook`),
  ADD KEY `assignment_recipe_fk` (`recipe`);

--
-- Indici per le tabelle `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `extratask`
--
ALTER TABLE `extratask`
  ADD PRIMARY KEY (`id`),
  ADD KEY `extratask_sheet_fk` (`sheet`),
  ADD KEY `extratask_task_fk` (`task`);

--
-- Indici per le tabelle `menuitems`
--
ALTER TABLE `menuitems`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `menusections`
--
ALTER TABLE `menusections`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `summarysheet`
--
ALTER TABLE `summarysheet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `summarysheet_user_fk` (`owner`),
  ADD KEY `summarysheet_menu_fk` (`menu`),
  ADD KEY `summarysheet_service_fk` (`service_id`);

--
-- Indici per le tabelle `turn`
--
ALTER TABLE `turn`
  ADD PRIMARY KEY (`id`),
  ADD KEY `turn_service_fk` (`service`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `assignment`
--
ALTER TABLE `assignment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `extratask`
--
ALTER TABLE `extratask`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `menuitems`
--
ALTER TABLE `menuitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT per la tabella `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT per la tabella `menusections`
--
ALTER TABLE `menusections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT per la tabella `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT per la tabella `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `summarysheet`
--
ALTER TABLE `summarysheet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `turn`
--
ALTER TABLE `turn`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `assignment_cook_fk` FOREIGN KEY (`cook`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `assignment_recipe_fk` FOREIGN KEY (`recipe`) REFERENCES `recipes` (`id`),
  ADD CONSTRAINT `assignment_sheet_fk` FOREIGN KEY (`sheet`) REFERENCES `summarysheet` (`id`),
  ADD CONSTRAINT `assignment_turn_fk` FOREIGN KEY (`turn`) REFERENCES `turn` (`id`);

--
-- Limiti per la tabella `extratask`
--
ALTER TABLE `extratask`
  ADD CONSTRAINT `extratask_sheet_fk` FOREIGN KEY (`sheet`) REFERENCES `summarysheet` (`id`),
  ADD CONSTRAINT `extratask_task_fk` FOREIGN KEY (`task`) REFERENCES `recipes` (`id`);

--
-- Limiti per la tabella `summarysheet`
--
ALTER TABLE `summarysheet`
  ADD CONSTRAINT `summarysheet_menu_fk` FOREIGN KEY (`menu`) REFERENCES `menus` (`id`),
  ADD CONSTRAINT `summarysheet_service_fk` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `summarysheet_user_fk` FOREIGN KEY (`owner`) REFERENCES `users` (`id`);

--
-- Limiti per la tabella `turn`
--
ALTER TABLE `turn`
  ADD CONSTRAINT `turn_service_fk` FOREIGN KEY (`service`) REFERENCES `services` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
