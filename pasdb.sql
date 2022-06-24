-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.29 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pasdb
CREATE DATABASE IF NOT EXISTS `pasdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pasdb`;

-- Dumping structure for table pasdb.tbl_claim
CREATE TABLE IF NOT EXISTS `tbl_claim` (
  `claim_number` int NOT NULL AUTO_INCREMENT,
  `doa` date NOT NULL,
  `address` varchar(50) NOT NULL,
  `desAccident` varchar(50) NOT NULL,
  `desDamage` varchar(50) NOT NULL,
  `estCost` double NOT NULL,
  `policy_number` int NOT NULL,
  PRIMARY KEY (`claim_number`),
  KEY `tbl_claims_ibfk_1` (`policy_number`),
  CONSTRAINT `tbl_claim_ibfk_1` FOREIGN KEY (`policy_number`) REFERENCES `tbl_policy` (`policy_number`)
) ENGINE=InnoDB AUTO_INCREMENT=10015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pasdb.tbl_customersaccount
CREATE TABLE IF NOT EXISTS `tbl_customersaccount` (
  `account_number` int NOT NULL AUTO_INCREMENT,
  `account_fname` varchar(50) NOT NULL,
  `account_lname` varchar(50) NOT NULL,
  `account_address` varchar(50) NOT NULL,
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1090 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pasdb.tbl_policy
CREATE TABLE IF NOT EXISTS `tbl_policy` (
  `policy_number` int NOT NULL AUTO_INCREMENT,
  `policy_effectivedate` date NOT NULL,
  `policy_expirydate` date DEFAULT NULL,
  `account_number` int NOT NULL,
  `premium_charge` double NOT NULL,
  PRIMARY KEY (`policy_number`),
  KEY `account_number_idx` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=600091 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pasdb.tbl_policyholder
CREATE TABLE IF NOT EXISTS `tbl_policyholder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `address` varchar(50) NOT NULL,
  `driverlicense` varchar(50) NOT NULL,
  `driverlicenseissued` date NOT NULL,
  `policy_number` int NOT NULL,
  `account_number` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_number_idx` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pasdb.tbl_vehicle
CREATE TABLE IF NOT EXISTS `tbl_vehicle` (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `make` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `year` varchar(45) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `fuel_type` varchar(45) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `vehicle_premium` double DEFAULT NULL,
  `policy_number` int NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  KEY `policy_number` (`policy_number`),
  CONSTRAINT `tbl_vehicle_ibfk_1` FOREIGN KEY (`policy_number`) REFERENCES `tbl_policy` (`policy_number`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
