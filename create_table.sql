CREATE TABLE patienttable (
                              PatientID int(11) NOT NULL AUTO_INCREMENT,
                              PtLastName varchar(128) DEFAULT NULL,
                              PtPreviousLastName varchar(128) DEFAULT NULL,
                              PtFirstName varchar(128) DEFAULT NULL,
                              HomeAddress1 varchar(128) DEFAULT NULL,
                              HomeCity varchar(128) DEFAULT NULL,
                              HomeState_Province_Region varchar(50) DEFAULT NULL,
                              HomeZip varchar(15) DEFAULT NULL,
                              Country varchar(75) DEFAULT NULL,
                              Citizenship varchar(75) DEFAULT NULL,
                              PtMobilePhone varchar(14) DEFAULT NULL,
                              EmergencyPhoneNumber varchar(14) DEFAULT NULL,
                              EmailAddress varchar(128) DEFAULT NULL,
                              PtSS varchar(12) DEFAULT NULL,
                              DOB datetime DEFAULT NULL,
                              Gender varchar(50) DEFAULT NULL,
                              EthnicAssociation varchar(75) DEFAULT NULL,
                              MaritalStatus varchar(25) DEFAULT NULL,
                              CurrentPrimaryHCP varchar(128) DEFAULT NULL,
                              Comments varchar(254) DEFAULT NULL,
                              NextOfKin varchar(128) DEFAULT NULL,
                              NextOfKinRelationshipToPatient varchar(50) DEFAULT NULL,
                              PRIMARY KEY (PatientID),
                              KEY I_PtLastFirstName (PtLastName,PtFirstName),
                              KEY I_HomePhone (PtMobilePhone),
                              KEY I_SSN (PtSS)
);

CREATE TABLE allergyhistorytable (
                                     AllergyID int(11) NOT NULL AUTO_INCREMENT,
                                     PatientID int(11) DEFAULT NULL,
                                     Allergen varchar(254) DEFAULT NULL,
                                     AllergyStartDate varchar(25) DEFAULT NULL,
                                     AllergyEndDate varchar(25) DEFAULT NULL,
                                     AllergyDescription varchar(254) DEFAULT NULL,
                                     deleted tinyint(1) DEFAULT 0,
                                     PRIMARY KEY (AllergyID)
);

CREATE TABLE generalmedicalhistorytable (
                                            GeneralMedicalHistoryID int(11) NOT NULL AUTO_INCREMENT,
                                            PatientID int(11) DEFAULT NULL,
                                            Tobacco varchar(50) DEFAULT NULL,
                                            TobaccoQuantity varchar(75) DEFAULT NULL,
                                            Tobaccoduration varchar(75) DEFAULT NULL,
                                            Alcohol varchar(50) DEFAULT NULL,
                                            AlcoholQuantity varchar(75) DEFAULT NULL,
                                            Alcoholduration varchar(75) DEFAULT NULL,
                                            Drug varchar(25) DEFAULT NULL,
                                            DrugType varchar(254) DEFAULT NULL,
                                            Drugduration varchar(75) DEFAULT NULL,
                                            BloodType varchar(10) DEFAULT NULL,
                                            Rh varchar(10) DEFAULT NULL,
                                            deleted tinyint(1) DEFAULT 0,
                                            PRIMARY KEY (GeneralMedicalHistoryID),
                                            KEY GeneralMedHxPatientIDIndex (PatientID)
);

CREATE TABLE familyhistorytable (
                                    FamilyID int(11) NOT NULL AUTO_INCREMENT,
                                    PatientID int(11) DEFAULT NULL,
                                    Name varchar(50) DEFAULT NULL,
                                    Relation varchar(50) DEFAULT NULL,
                                    Alive tinyint(1) DEFAULT 0,
                                    Lives_with_patient tinyint(1) DEFAULT 0,
                                    MajorDisorder varchar(254) DEFAULT NULL,
                                    SpecificTypeDisorder varchar(254) DEFAULT NULL,
                                    DisorderHRF tinyint(1) DEFAULT 0,
                                    deleted tinyint(1) DEFAULT 0,
                                    PRIMARY KEY (FamilyID),
                                    KEY I_PatientID (PatientID)
);

CREATE TABLE immunizationshistorytable (
                                           ImmunizationsID int(11) NOT NULL AUTO_INCREMENT,
                                           PatientID int(11) DEFAULT NULL,
                                           Vaccine varchar(128) DEFAULT NULL,
                                           ImmunizationDate date DEFAULT NULL,
                                           ExperationDate date DEFAULT NULL,
                                           Delivery varchar(128) DEFAULT NULL,
                                           Comments varchar(254) DEFAULT NULL,
                                           HCPId varchar(75) DEFAULT NULL,
                                           deleted tinyint(1) DEFAULT 0,
                                           PRIMARY KEY (ImmunizationsID),
                                           KEY I_PatientID (PatientID)
);

CREATE TABLE medication (
                            MedicationID int(11) NOT NULL AUTO_INCREMENT,
                            CurrentMedicationID int(11) DEFAULT NULL,
                            PatientID int(11) DEFAULT NULL,
                            Medication varchar(254) DEFAULT NULL,
                            Quantity varchar(50) DEFAULT NULL,
                            QuantityUnits varchar(50) DEFAULT NULL,
                            MedicationOrderHCP varchar(75) DEFAULT NULL,
                            MedicationOrderDate datetime DEFAULT NULL,
                            Instructions varchar(1024) DEFAULT NULL,
                            MedicationStartDate date DEFAULT NULL,
                            MedicationEndDate date DEFAULT NULL,
                            deleted tinyint(1) DEFAULT 0,
                            PRIMARY KEY (MedicationID),
                            KEY I_PatientID (PatientID)
);
