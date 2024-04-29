-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: digitalinterview
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `familyhistory`
--

DROP TABLE IF EXISTS `familyhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `familyhistory` (
  `FamilyID` int NOT NULL AUTO_INCREMENT,
  `PatientID` int DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Relation` varchar(50) DEFAULT NULL,
  `Alive` tinyint(1) DEFAULT '0',
  `Lives_with_patient` tinyint(1) DEFAULT '0',
  `MajorDisorder` varchar(254) DEFAULT NULL,
  `SpecificTypeDisorder` varchar(254) DEFAULT NULL,
  `DisorderHRF` tinyint(1) DEFAULT '0',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`FamilyID`),
  KEY `I_PatientID` (`PatientID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `familyhistory`
--

LOCK TABLES `familyhistory` WRITE;
/*!40000 ALTER TABLE `familyhistory` DISABLE KEYS */;
INSERT INTO `familyhistory` VALUES (1,1,'John Doe','Father',1,0,'Heart Disease','Hypertension',1,0),(2,1,'Jane Doe','Mother',1,0,'Cancer','Breast Cancer',1,0),(3,2,'Michael Smith','Father',1,0,'Diabetes','Type 2 Diabetes',1,0);
/*!40000 ALTER TABLE `familyhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `PatientID` int NOT NULL AUTO_INCREMENT,
  `PtLastName` varchar(128) DEFAULT NULL,
  `PtPreviousLastName` varchar(128) DEFAULT NULL,
  `PtFirstName` varchar(128) DEFAULT NULL,
  `HomeAddress1` varchar(128) DEFAULT NULL,
  `HomeCity` varchar(128) DEFAULT NULL,
  `HomeState_Province_Region` varchar(50) DEFAULT NULL,
  `HomeZip` varchar(15) DEFAULT NULL,
  `Country` varchar(75) DEFAULT NULL,
  `Citizenship` varchar(75) DEFAULT NULL,
  `PtMobilePhone` varchar(14) DEFAULT NULL,
  `EmergencyPhoneNumber` varchar(14) DEFAULT NULL,
  `EmailAddress` varchar(128) DEFAULT NULL,
  `PtSS` varchar(12) DEFAULT NULL,
  `DOB` datetime DEFAULT NULL,
  `Gender` varchar(50) DEFAULT NULL,
  `EthnicAssociation` varchar(75) DEFAULT NULL,
  `MaritalStatus` varchar(25) DEFAULT NULL,
  `CurrentPrimaryHCP` varchar(128) DEFAULT NULL,
  `Comments` varchar(254) DEFAULT NULL,
  `NextOfKin` varchar(128) DEFAULT NULL,
  `NextOfKinRelationshipToPatient` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PatientID`),
  KEY `I_PtLastFirstName` (`PtLastName`,`PtFirstName`),
  KEY `I_HomePhone` (`PtMobilePhone`),
  KEY `I_SSN` (`PtSS`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (2,'Beckham','','David','456 Elm St','Othertown','New York','54321','USA','American','321-654-0987','789-012-3456','alice.smith@example.com','987-65-4321','1975-08-20 00:00:00','Male','Caucasian','Married','Dr. Johnson','','Bob Smith','Parent'),(3,'Patel',NULL,'Ravi','789 Oak St','Somewhere','Texas','67890','USA','Indian','456-789-0123','012-345-6789','ravi.patel@example.com','567-89-0123','1990-02-15 00:00:00','Male','Asian','Married','Dr. Lee',NULL,'Sara Patel','Sibling'),(13,'9','9','9','9','9','9','9','9','9','9','9','9','9','2024-04-10 00:00:00','Male','98','Single','9','9','9','9');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-30  1:18:52

DELIMITER //

CREATE PROCEDURE fetch_family_history(IN patient_id INT)
BEGIN
    SELECT * FROM familyhistory WHERE PatientID = patient_id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE fetch_patient_demographics(IN patient_id INT)
BEGIN
    SELECT * FROM patient WHERE PatientID = patient_id;
END //

DELIMITER ;
