-- MariaDB dump 10.17  Distrib 10.4.13-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: fwdb
-- ------------------------------------------------------
-- Server version	10.4.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `alert_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `notification` text NOT NULL,
  PRIMARY KEY (`alert_id`),
  KEY `FK_alert_systemuser` (`username`),
  CONSTRAINT `FK_alert_systemuser` FOREIGN KEY (`username`) REFERENCES `systemuser` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `association_representative`
--

DROP TABLE IF EXISTS `association_representative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `association_representative` (
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK__user_roles_AR` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `association_representative`
--

LOCK TABLES `association_representative` WRITE;
/*!40000 ALTER TABLE `association_representative` DISABLE KEYS */;
/*!40000 ALTER TABLE `association_representative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coach`
--

DROP TABLE IF EXISTS `coach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coach` (
  `username` varchar(50) NOT NULL,
  `qualification` enum('MAIN_COACH','SECOND_COACH','JUNIOR_COACH') NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK_coach_user_roles` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coach`
--

LOCK TABLES `coach` WRITE;
/*!40000 ALTER TABLE `coach` DISABLE KEYS */;
/*!40000 ALTER TABLE `coach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coach_in_team`
--

DROP TABLE IF EXISTS `coach_in_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coach_in_team` (
  `username` varchar(50) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `team_job` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`,`team_name`),
  KEY `FK_coach_in_team_team` (`team_name`),
  CONSTRAINT `FK_coach_in_team_coach` FOREIGN KEY (`username`) REFERENCES `coach` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_coach_in_team_team` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coach_in_team`
--

LOCK TABLES `coach_in_team` WRITE;
/*!40000 ALTER TABLE `coach_in_team` DISABLE KEYS */;
/*!40000 ALTER TABLE `coach_in_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_card`
--

DROP TABLE IF EXISTS `event_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_card` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `offender_name` varchar(50) NOT NULL,
  `type` enum('RED','YELLOW') NOT NULL,
  `minute` int(11) NOT NULL,
  PRIMARY KEY (`event_id`) USING BTREE,
  KEY `FK__player_card` (`offender_name`),
  KEY `FK_event_card_game` (`game_id`),
  CONSTRAINT `FK__player_card` FOREIGN KEY (`offender_name`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_card_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_card`
--

LOCK TABLES `event_card` WRITE;
/*!40000 ALTER TABLE `event_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_goal`
--

DROP TABLE IF EXISTS `event_goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_goal` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `scored_team` varchar(50) NOT NULL,
  `scored_on_team` varchar(50) NOT NULL,
  `scorer_name` varchar(50) NOT NULL DEFAULT '',
  `minute` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_scored_team_team` (`scored_team`),
  KEY `FK__team_scored_on` (`scored_on_team`),
  KEY `FK_event_goal_player` (`scorer_name`),
  KEY `FK_event_goal_game` (`game_id`),
  CONSTRAINT `FK__team_scored_on` FOREIGN KEY (`scored_on_team`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_goal_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_goal_player` FOREIGN KEY (`scorer_name`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_scored_team_team` FOREIGN KEY (`scored_team`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_goal`
--

LOCK TABLES `event_goal` WRITE;
/*!40000 ALTER TABLE `event_goal` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_injury`
--

DROP TABLE IF EXISTS `event_injury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_injury` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `injured_name` varchar(50) NOT NULL DEFAULT '',
  `minute` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`event_id`),
  KEY `FK__player_injured` (`injured_name`),
  KEY `FK_event_injury_game` (`game_id`),
  CONSTRAINT `FK__player_injured` FOREIGN KEY (`injured_name`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_injury_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_injury`
--

LOCK TABLES `event_injury` WRITE;
/*!40000 ALTER TABLE `event_injury` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_injury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_offside`
--

DROP TABLE IF EXISTS `event_offside`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_offside` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `minute` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK__team_offside` (`team_name`),
  KEY `FK_event_offside_game` (`game_id`),
  CONSTRAINT `FK__team_offside` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_offside_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_offside`
--

LOCK TABLES `event_offside` WRITE;
/*!40000 ALTER TABLE `event_offside` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_offside` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_penalty`
--

DROP TABLE IF EXISTS `event_penalty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_penalty` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `minute` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK__team_penalty` (`team_name`),
  KEY `FK_event_penalty_game` (`game_id`),
  CONSTRAINT `FK__team_penalty` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_penalty_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_penalty`
--

LOCK TABLES `event_penalty` WRITE;
/*!40000 ALTER TABLE `event_penalty` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_penalty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_switch_players`
--

DROP TABLE IF EXISTS `event_switch_players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_switch_players` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `entering_Player` varchar(50) NOT NULL DEFAULT '',
  `exiting_Player` varchar(50) NOT NULL DEFAULT '',
  `minute` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK__player_enter` (`entering_Player`),
  KEY `FK__player_exit` (`exiting_Player`),
  KEY `FK_event_switch_players_team` (`team_name`),
  KEY `FK_event_switch_players_game` (`game_id`),
  CONSTRAINT `FK__player_enter` FOREIGN KEY (`entering_Player`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__player_exit` FOREIGN KEY (`exiting_Player`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_switch_players_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_event_switch_players_team` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_switch_players`
--

LOCK TABLES `event_switch_players` WRITE;
/*!40000 ALTER TABLE `event_switch_players` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_switch_players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `stadium_id` int(11) NOT NULL,
  `home_team` varchar(50) NOT NULL,
  `away_team` varchar(50) NOT NULL,
  `Date` date NOT NULL,
  `finished` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`game_id`),
  KEY `FK_game_stadium` (`stadium_id`),
  KEY `FK_game_team` (`home_team`),
  KEY `FK_game_team_2` (`away_team`),
  CONSTRAINT `FK_game_stadium` FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`stadium_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_game_team` FOREIGN KEY (`home_team`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_game_team_2` FOREIGN KEY (`away_team`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_in_season`
--

DROP TABLE IF EXISTS `games_in_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_in_season` (
  `season_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  PRIMARY KEY (`season_id`,`game_id`),
  KEY `FK_games_in_season_game` (`game_id`),
  CONSTRAINT `FK_games_in_season_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_games_in_season_season` FOREIGN KEY (`season_id`) REFERENCES `season` (`season_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_in_season`
--

LOCK TABLES `games_in_season` WRITE;
/*!40000 ALTER TABLE `games_in_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_in_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `league`
--

DROP TABLE IF EXISTS `league`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `league` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `league`
--

LOCK TABLES `league` WRITE;
/*!40000 ALTER TABLE `league` DISABLE KEYS */;
/*!40000 ALTER TABLE `league` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_in_teams`
--

DROP TABLE IF EXISTS `manager_in_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_in_teams` (
  `username` varchar(50) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `REMOVE_PLAYER` bit(1) NOT NULL DEFAULT b'0',
  `ADD_PLAYER` bit(1) NOT NULL DEFAULT b'0',
  `CHANGE_POSITION_PLAYER` bit(1) NOT NULL DEFAULT b'0',
  `REMOVE_COACH` bit(1) NOT NULL DEFAULT b'0',
  `ADD_COACH` bit(1) NOT NULL DEFAULT b'0',
  `CHANGE_TEAM_JOB_COACH` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`username`,`team_name`) USING BTREE,
  KEY `FK_managed_teams_team` (`team_name`),
  CONSTRAINT `FK_managed_teams_team` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_managed_teams_team_manager` FOREIGN KEY (`username`) REFERENCES `team_manager` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_in_teams`
--

LOCK TABLES `manager_in_teams` WRITE;
/*!40000 ALTER TABLE `manager_in_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager_in_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owned_teams`
--

DROP TABLE IF EXISTS `owned_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owned_teams` (
  `username` varchar(50) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  PRIMARY KEY (`username`,`team_name`),
  KEY `FK__team` (`team_name`),
  CONSTRAINT `FK__team` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__team_owner` FOREIGN KEY (`username`) REFERENCES `team_owner` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owned_teams`
--

LOCK TABLES `owned_teams` WRITE;
/*!40000 ALTER TABLE `owned_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `owned_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `username` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK_player_user_roles` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_in_team`
--

DROP TABLE IF EXISTS `player_in_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_in_team` (
  `username` varchar(50) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `field_job` enum('DEFENSE','GOAL_KEEPER','FRONT') NOT NULL,
  PRIMARY KEY (`username`,`team_name`),
  KEY `FK_player_in_team_team` (`team_name`),
  CONSTRAINT `FK__player` FOREIGN KEY (`username`) REFERENCES `player` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_player_in_team_team` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_in_team`
--

LOCK TABLES `player_in_team` WRITE;
/*!40000 ALTER TABLE `player_in_team` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_in_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `points_policy`
--

DROP TABLE IF EXISTS `points_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `points_policy` (
  `policy_id` int(11) NOT NULL AUTO_INCREMENT,
  `victory_points` int(11) NOT NULL,
  `loss_points` int(11) NOT NULL,
  `tie_points` int(11) NOT NULL,
  PRIMARY KEY (`policy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `points_policy`
--

LOCK TABLES `points_policy` WRITE;
/*!40000 ALTER TABLE `points_policy` DISABLE KEYS */;
/*!40000 ALTER TABLE `points_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee`
--

DROP TABLE IF EXISTS `referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee` (
  `username` varchar(50) NOT NULL,
  `training` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK_refree_user_roles` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee`
--

LOCK TABLES `referee` WRITE;
/*!40000 ALTER TABLE `referee` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee_in_game`
--

DROP TABLE IF EXISTS `referee_in_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_in_game` (
  `username` varchar(50) NOT NULL,
  `game_id` int(11) NOT NULL,
  PRIMARY KEY (`username`,`game_id`) USING BTREE,
  KEY `FK_referee_in_game_game` (`game_id`),
  CONSTRAINT `FK__referee` FOREIGN KEY (`username`) REFERENCES `referee` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_referee_in_game_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_in_game`
--

LOCK TABLES `referee_in_game` WRITE;
/*!40000 ALTER TABLE `referee_in_game` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_in_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee_in_season`
--

DROP TABLE IF EXISTS `referee_in_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_in_season` (
  `username` varchar(50) NOT NULL,
  `season_id` int(11) NOT NULL,
  PRIMARY KEY (`username`,`season_id`) USING BTREE,
  KEY `FK_referee_in_season_season` (`season_id`),
  CONSTRAINT `FK_referee_in_season_referee` FOREIGN KEY (`username`) REFERENCES `referee` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_referee_in_season_season` FOREIGN KEY (`season_id`) REFERENCES `season` (`season_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_in_season`
--

LOCK TABLES `referee_in_season` WRITE;
/*!40000 ALTER TABLE `referee_in_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_in_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduling_policy`
--

DROP TABLE IF EXISTS `scheduling_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduling_policy` (
  `scheduling_id` int(11) NOT NULL,
  `games_Per_Season` int(11) NOT NULL,
  `games_Per_Day` int(11) NOT NULL,
  `minimum_Rest_Days` int(11) NOT NULL,
  PRIMARY KEY (`scheduling_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduling_policy`
--

LOCK TABLES `scheduling_policy` WRITE;
/*!40000 ALTER TABLE `scheduling_policy` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduling_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season`
--

DROP TABLE IF EXISTS `season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season` (
  `season_id` int(11) NOT NULL AUTO_INCREMENT,
  `league_name` varchar(50) NOT NULL,
  `years` varchar(50) NOT NULL,
  `is_under_way` bit(1) NOT NULL DEFAULT b'0',
  `points_policy_id` int(11) NOT NULL,
  PRIMARY KEY (`season_id`,`league_name`,`years`) USING BTREE,
  KEY `league_name` (`league_name`),
  KEY `years` (`years`),
  KEY `FK_season_points_policy` (`points_policy_id`) USING BTREE,
  CONSTRAINT `FK_season_league` FOREIGN KEY (`league_name`) REFERENCES `league` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_season_points_policy` FOREIGN KEY (`points_policy_id`) REFERENCES `points_policy` (`policy_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season`
--

LOCK TABLES `season` WRITE;
/*!40000 ALTER TABLE `season` DISABLE KEYS */;
/*!40000 ALTER TABLE `season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stadium`
--

DROP TABLE IF EXISTS `stadium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stadium` (
  `stadium_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  PRIMARY KEY (`stadium_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stadium`
--

LOCK TABLES `stadium` WRITE;
/*!40000 ALTER TABLE `stadium` DISABLE KEYS */;
/*!40000 ALTER TABLE `stadium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stadium_home_teams`
--

DROP TABLE IF EXISTS `stadium_home_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stadium_home_teams` (
  `stadium_id` int(11) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  PRIMARY KEY (`stadium_id`,`team_name`),
  KEY `FK__team_stadium` (`team_name`),
  CONSTRAINT `FK__stadium_teams` FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`stadium_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__team_stadium` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stadium_home_teams`
--

LOCK TABLES `stadium_home_teams` WRITE;
/*!40000 ALTER TABLE `stadium_home_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `stadium_home_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_admin`
--

DROP TABLE IF EXISTS `system_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_admin` (
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK__user_roles_system_admin` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_admin`
--

LOCK TABLES `system_admin` WRITE;
/*!40000 ALTER TABLE `system_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemuser` (
  `username` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `notify_by_email` bit(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemuser`
--

LOCK TABLES `systemuser` WRITE;
/*!40000 ALTER TABLE `systemuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `systemuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `name` varchar(50) NOT NULL,
  `status` enum('OPEN','CLOSED','PERMENENTLY_CLOSED') NOT NULL DEFAULT 'OPEN',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_manager`
--

DROP TABLE IF EXISTS `team_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_manager` (
  `username` varchar(50) NOT NULL,
  `appointer` varchar(50) NOT NULL,
  PRIMARY KEY (`appointer`,`username`),
  KEY `FK_team_manager_user_roles` (`username`),
  CONSTRAINT `FK_team_manager_team_owner` FOREIGN KEY (`appointer`) REFERENCES `team_owner` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_team_manager_user_roles` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_manager`
--

LOCK TABLES `team_manager` WRITE;
/*!40000 ALTER TABLE `team_manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_owner`
--

DROP TABLE IF EXISTS `team_owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_owner` (
  `username` varchar(50) NOT NULL,
  `appointer` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_team_owner_systemuser` (`appointer`),
  CONSTRAINT `FK__user_roles` FOREIGN KEY (`username`) REFERENCES `user_roles` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_team_owner_systemuser` FOREIGN KEY (`appointer`) REFERENCES `systemuser` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_owner`
--

LOCK TABLES `team_owner` WRITE;
/*!40000 ALTER TABLE `team_owner` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams_in_season`
--

DROP TABLE IF EXISTS `teams_in_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams_in_season` (
  `season_id` int(11) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  PRIMARY KEY (`season_id`,`team_name`),
  KEY `FK__team_name` (`team_name`),
  CONSTRAINT `FK__team_name` FOREIGN KEY (`team_name`) REFERENCES `team` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_teams_in_season_season` FOREIGN KEY (`season_id`) REFERENCES `season` (`season_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams_in_season`
--

LOCK TABLES `teams_in_season` WRITE;
/*!40000 ALTER TABLE `teams_in_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `teams_in_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_complaint`
--

DROP TABLE IF EXISTS `user_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_complaint` (
  `complaint_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` text DEFAULT NULL,
  `date` date NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`complaint_id`),
  KEY `FK__systemuser` (`username`),
  CONSTRAINT `FK__systemuser` FOREIGN KEY (`username`) REFERENCES `systemuser` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_complaint`
--

LOCK TABLES `user_complaint` WRITE;
/*!40000 ALTER TABLE `user_complaint` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `username` varchar(50) NOT NULL,
  `role_type` enum('FAN','PLAYER','COACH','TEAM_MANAGER','TEAM_OWNER','SYSTEM_ADMIN','REFEREE','ASSOCIATION_REPRESENTATIVE') NOT NULL,
  PRIMARY KEY (`username`,`role_type`) USING BTREE,
  CONSTRAINT `FK_role_systemuser` FOREIGN KEY (`username`) REFERENCES `systemuser` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-22 17:55:32
