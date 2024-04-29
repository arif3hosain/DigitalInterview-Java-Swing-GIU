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
-- Table structure for table `allergyhistory`
--

DROP TABLE IF EXISTS `allergyhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allergyhistory` (
  `AllergyID` int NOT NULL AUTO_INCREMENT,
  `PatientID` int DEFAULT NULL,
  `Allergen` varchar(254) DEFAULT NULL,
  `AllergyStartDate` varchar(25) DEFAULT NULL,
  `AllergyEndDate` varchar(25) DEFAULT NULL,
  `AllergyDescription` varchar(254) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`AllergyID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergyhistory`
--

LOCK TABLES `allergyhistory` WRITE;
/*!40000 ALTER TABLE `allergyhistory` DISABLE KEYS */;
INSERT INTO `allergyhistory` VALUES (1,1,'Peanuts','2023-01-01','2024-04-05','Allergic reaction',0),(2,1,'Shellfish','2022-05-01','2024-04-01','Hives and itchings',0),(3,1,'Pollen','2021-07-10','2024-04-05','Seasonal allergies',0),(4,1,'Pollen','2021-07-10','2024-04-05','Seasonal allergies ok',0),(7,1,'Normal','2024-04-03','2024-04-03','Normal',0);
/*!40000 ALTER TABLE `allergyhistory` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `familyhistory` VALUES (1,1,'John Doe','Father',1,0,'Heart Disease','Hypertension',1,0),(2,1,'Jane Doe','Mother',1,0,'Cancer','Breast Cancer',1,0),(3,2,'Michael Smith','Father',1,0,'Diabetes','Type 2 Diabetes',1,0),(4,1,'','',0,0,'','',0,0),(5,1,'abc','bro',1,1,'8','8',1,0),(6,1,'Rakib','4',0,0,'94','94',0,0),(7,1,'abc','asdf',1,1,'asdf','asdf',1,0),(8,1,'asdfas','dfasdfa',1,1,'sdfasdf','asdf',1,0);
/*!40000 ALTER TABLE `familyhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalhistory`
--

DROP TABLE IF EXISTS `medicalhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalhistory` (
  `GeneralMedicalHistoryID` int NOT NULL AUTO_INCREMENT,
  `PatientID` int DEFAULT NULL,
  `Tobacco` varchar(50) DEFAULT NULL,
  `TobaccoQuantity` varchar(75) DEFAULT NULL,
  `Tobaccoduration` varchar(75) DEFAULT NULL,
  `Alcohol` varchar(50) DEFAULT NULL,
  `AlcoholQuantity` varchar(75) DEFAULT NULL,
  `Alcoholduration` varchar(75) DEFAULT NULL,
  `Drug` varchar(25) DEFAULT NULL,
  `DrugType` varchar(254) DEFAULT NULL,
  `Drugduration` varchar(75) DEFAULT NULL,
  `BloodType` varchar(10) DEFAULT NULL,
  `Rh` varchar(10) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`GeneralMedicalHistoryID`),
  KEY `GeneralMedHxPatientIDIndex` (`PatientID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalhistory`
--

LOCK TABLES `medicalhistory` WRITE;
/*!40000 ALTER TABLE `medicalhistory` DISABLE KEYS */;
INSERT INTO `medicalhistory` VALUES (1,1,'76','766','76','76','76','76','76','76','76','O','-',0),(2,2,'No',NULL,NULL,'Yes','2 drinks/week','3 years','No',NULL,NULL,'A','+',0),(3,3,'Yes','5 cigarettes/day','2 years','Yes','1 drink/week','1 year','No',NULL,NULL,'AB','-',0),(4,7,'7','7','7','7','7','7','7','7','7','A','+',0),(5,2,'9','9','9','9','9','9','9','9','9','A','+',0);
/*!40000 ALTER TABLE `medicalhistory` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (2,'Beckham','','David','456 Elm St','Othertown','New York','54321','USA','American','321-654-0987','789-012-3456','alice.smith@example.com','987-65-4321','1975-08-20 00:00:00','Female','Caucasian','Single','Dr. Johnson','','Bob Smith','Parent'),(3,'Patel',NULL,'Ravi','789 Oak St','Somewhere','Texas','67890','USA','Indian','456-789-0123','012-345-6789','ravi.patel@example.com','567-89-0123','1990-02-15 00:00:00','Male','Asian','Married','Dr. Lee',NULL,'Sara Patel','Sibling');
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

-- Dump completed on 2024-04-29 22:39:58
