-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 10.100.10.95    Database: tritron
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `anchor`
--

DROP TABLE IF EXISTS `anchor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_address`
--

DROP TABLE IF EXISTS `anchor_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_address` (
  `id` bigint NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `pin_code` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtn723jns6jnosohjmjvbf65xh` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_address_details`
--

DROP TABLE IF EXISTS `anchor_address_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_address_details` (
  `id` bigint NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `pin_code` int NOT NULL,
  `state` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmdnndt7kswuq5prq1pg34xbiq` (`ci_id`),
  CONSTRAINT `FKmdnndt7kswuq5prq1pg34xbiq` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_authorized`
--

DROP TABLE IF EXISTS `anchor_authorized`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_authorized` (
  `id` bigint NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `indemnity_doc` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKillq9k9yy2jvp8j2xo3gqg5n4` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_authorized_details`
--

DROP TABLE IF EXISTS `anchor_authorized_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_authorized_details` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `email_id` varchar(255) NOT NULL,
  `indemnity_doc` varchar(255) DEFAULT NULL,
  `mobile` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ok50cfcwavoslrb2tprdfhq5` (`ci_id`),
  CONSTRAINT `FK3ok50cfcwavoslrb2tprdfhq5` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_basic`
--

DROP TABLE IF EXISTS `anchor_basic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_basic` (
  `id` bigint NOT NULL,
  `anchor_name` varchar(255) NOT NULL,
  `cin` varchar(255) NOT NULL,
  `incorp_date` datetime NOT NULL,
  `industry` varchar(255) NOT NULL,
  `pan` varchar(255) NOT NULL,
  `sector` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsdonsip4wb4ijguygu9wpodlj` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_basic_details`
--

DROP TABLE IF EXISTS `anchor_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_basic_details` (
  `id` bigint NOT NULL,
  `anchor_name` varchar(255) NOT NULL,
  `cin` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `incorporation_date` datetime(6) DEFAULT NULL,
  `industry` varchar(255) NOT NULL,
  `pan` varchar(255) NOT NULL,
  `sector` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKilogqjww5ilt79a8j1gjfruvj` (`ci_id`),
  CONSTRAINT `FKilogqjww5ilt79a8j1gjfruvj` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_beneficiary`
--

DROP TABLE IF EXISTS `anchor_beneficiary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_beneficiary` (
  `id` bigint NOT NULL,
  `bank_acct_number` varchar(255) NOT NULL,
  `bank_branch` varchar(255) NOT NULL,
  `bank_name` varchar(255) NOT NULL,
  `bank_ifsc_code` varchar(255) NOT NULL,
  `ben_name` varchar(255) NOT NULL,
  `ben_type` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfp9xeg415j3xadl0up8miaeap` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_beneficiary_details`
--

DROP TABLE IF EXISTS `anchor_beneficiary_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_beneficiary_details` (
  `id` bigint NOT NULL,
  `bank_acct_number` varchar(255) NOT NULL,
  `bank_branch` varchar(255) NOT NULL,
  `bank_ifsc_code` varchar(255) NOT NULL,
  `bank_name` varchar(255) NOT NULL,
  `ben_name` varchar(255) NOT NULL,
  `ben_type` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `doc_path` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7vx9vdfaevyvns8li72qmkeq6` (`ci_id`),
  CONSTRAINT `FK7vx9vdfaevyvns8li72qmkeq6` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_cred_norms`
--

DROP TABLE IF EXISTS `anchor_cred_norms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_cred_norms` (
  `id` bigint NOT NULL,
  `bureau_comer` int NOT NULL,
  `bureau_consu` int NOT NULL,
  `entity_gst` varchar(255) NOT NULL,
  `adjusted_leverage` float NOT NULL,
  `anchor_dependency` int NOT NULL,
  `anchor_dependency_month` int NOT NULL,
  `cheque_bounce` int NOT NULL,
  `churn` int NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `current_overdue_comer` int NOT NULL,
  `current_overdue_consu` int NOT NULL,
  `current_overdue_cred_comer` int NOT NULL,
  `current_overdue_cred_consu` int NOT NULL,
  `current_ratio` float NOT NULL,
  `entity_pan` varchar(255) NOT NULL,
  `entity_shop_establishment` varchar(255) NOT NULL,
  `entity_urc` varchar(255) NOT NULL,
  `epfo_delay` int NOT NULL,
  `geo_restriction` varchar(255) NOT NULL,
  `grp_of_vintage_corp_guarantee` int NOT NULL,
  `gst_payment_delay` int NOT NULL,
  `individual_aadhaar` varchar(255) NOT NULL,
  `individual_pan` varchar(255) NOT NULL,
  `min_anu_purch_anchor` float NOT NULL,
  `min_business_vintage` int NOT NULL,
  `min_vintage_with_anchor` int NOT NULL,
  `networth` int NOT NULL,
  `pat` float NOT NULL,
  `penal` float NOT NULL,
  `positive_atnw` int NOT NULL,
  `restructured` int NOT NULL,
  `sales_growth` int NOT NULL,
  `satu_due_delay` int NOT NULL,
  `sma_a` int NOT NULL,
  `sma_b` int NOT NULL,
  `sma_c` int NOT NULL,
  `sma_d` int NOT NULL,
  `sub_dbt_loss` int NOT NULL,
  `tol_atnw` float NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `wilful_suit_comer` int NOT NULL,
  `wilful_suit_consu` int NOT NULL,
  `working_capital` int DEFAULT NULL,
  `app_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKstv2cf19bu5wiuc5vhxw7y4mg` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_credit_norms`
--

DROP TABLE IF EXISTS `anchor_credit_norms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_credit_norms` (
  `id` bigint NOT NULL,
  `bureau` varchar(255) NOT NULL,
  `adjusted_leverage` varchar(255) NOT NULL,
  `anchor_dependency` varchar(255) NOT NULL,
  `anchorreco` varchar(255) NOT NULL,
  `bankstatement` varchar(255) NOT NULL,
  `commercial` varchar(255) NOT NULL,
  `consumer` varchar(255) NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `crime_checks` varchar(255) NOT NULL,
  `current_ratio` varchar(255) NOT NULL,
  `dedupechecks` varchar(255) NOT NULL,
  `grp_of_vintage_corp_guarantee` varchar(255) NOT NULL,
  `gst` varchar(255) NOT NULL,
  `min_anu_purch_anchor` varchar(255) NOT NULL,
  `min_business_vintage` varchar(255) NOT NULL,
  `min_vintage_with_anchor` varchar(255) NOT NULL,
  `networth` varchar(255) NOT NULL,
  `pat` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `watch_out_investor` varchar(255) NOT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6r2tcrr7886v1mbiddmcn6hlp` (`ci_id`),
  CONSTRAINT `FK6r2tcrr7886v1mbiddmcn6hlp` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_credit_policy`
--

DROP TABLE IF EXISTS `anchor_credit_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_credit_policy` (
  `id` bigint NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `credit_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrqvtg8x6n9vnxkwipu06ohx9h` (`app_id`),
  KEY `FKmi8u5scp20gmcpj7c3xmwafdt` (`credit_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer`
--

DROP TABLE IF EXISTS `anchor_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer` (
  `id` bigint NOT NULL,
  `status` varchar(255) NOT NULL,
  `cin` varchar(30) DEFAULT NULL,
  `customer_name` varchar(255) NOT NULL,
  `customer_type` varchar(255) NOT NULL,
  `pan` varchar(13) DEFAULT NULL,
  `product` varchar(255) NOT NULL,
  `gst` varchar(255) NOT NULL,
  `client_type` int NOT NULL,
  `stage` int NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cin` (`cin`),
  UNIQUE KEY `pan` (`pan`),
  KEY `FKnccap3jg8qg10e0nmo764nptn` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_anchor_address_details_entity_list`
--

DROP TABLE IF EXISTS `anchor_customer_anchor_address_details_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_anchor_address_details_entity_list` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_address_details_entity_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_chq7d3mrgdbcc1351q89ue7ee` (`anchor_address_details_entity_list_id`),
  KEY `FKpv0qvs9cxbw1qjpycs5kw16tw` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_anchor_authorized_entities`
--

DROP TABLE IF EXISTS `anchor_customer_anchor_authorized_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_anchor_authorized_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_authorized_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_gxjegiir9mxjetrfhsaetolbw` (`anchor_authorized_entities_id`),
  KEY `FKgy5tqq6at95aym5ysx3on3pw8` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_anchor_basic_details`
--

DROP TABLE IF EXISTS `anchor_customer_anchor_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_anchor_basic_details` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_basic_details_id` bigint NOT NULL,
  UNIQUE KEY `UK_sui16ns8ic8mevqscofma7hgx` (`anchor_basic_details_id`),
  KEY `FKk5vnyv6t9v12jev1b7a8hsgnx` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_anchor_gst_entities`
--

DROP TABLE IF EXISTS `anchor_customer_anchor_gst_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_anchor_gst_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_gst_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_ix85m4wko9p922nqywsrbymul` (`anchor_gst_entities_id`),
  KEY `FKye0j6h2u58bui7au14driyh5` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_anchor_key_entities`
--

DROP TABLE IF EXISTS `anchor_customer_anchor_key_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_anchor_key_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_key_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_mletvp0i8svv0by25waxxkhc2` (`anchor_key_entities_id`),
  KEY `FKsm8g4mvoixa38fnc8b3gxhrkg` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_beneficiary_entities`
--

DROP TABLE IF EXISTS `anchor_customer_beneficiary_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_beneficiary_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `beneficiary_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_axj48rr0xhfpikqtwqatipido` (`beneficiary_entities_id`),
  KEY `FKgn34db8i06kcg94haubigb8jx` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_info`
--

DROP TABLE IF EXISTS `anchor_customer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_info` (
  `id` bigint NOT NULL,
  `cin` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) NOT NULL,
  `customer_type` varchar(255) NOT NULL,
  `gst` varchar(255) NOT NULL,
  `pan` varchar(255) NOT NULL,
  `product` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_customer_program_details_entities`
--

DROP TABLE IF EXISTS `anchor_customer_program_details_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_customer_program_details_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `program_details_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_6wppxj07pb56vg2kga7bv4sdq` (`program_details_entities_id`),
  KEY `FK9lyvn6j0vsnkndihnjx726fmo` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_gst`
--

DROP TABLE IF EXISTS `anchor_gst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_gst` (
  `id` bigint NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `gst_acct_holder_name` varchar(255) NOT NULL,
  `gst_number` varchar(255) NOT NULL,
  `pincode` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswy91yw52skkub32hky7sarki` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_gst_details`
--

DROP TABLE IF EXISTS `anchor_gst_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_gst_details` (
  `id` bigint NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `gst_accnt_holder_name` varchar(255) NOT NULL,
  `gst_number` varchar(255) NOT NULL,
  `pin_code` int NOT NULL,
  `state` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpkvwfpknmoe8asldpavld6he` (`ci_id`),
  CONSTRAINT `FKpkvwfpknmoe8asldpavld6he` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_key`
--

DROP TABLE IF EXISTS `anchor_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_key` (
  `id` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK63rvrhort31itcv9yjj957g4a` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_key_tab`
--

DROP TABLE IF EXISTS `anchor_key_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_key_tab` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `sequence_no` int DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9nbgtd7fmrsvsxjbwgqx3wvp0` (`ci_id`),
  CONSTRAINT `FK9nbgtd7fmrsvsxjbwgqx3wvp0` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_product_details`
--

DROP TABLE IF EXISTS `anchor_product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_product_details` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `mou` varchar(255) NOT NULL,
  `product` varchar(255) NOT NULL,
  `sub_product` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7xpvxpv5lantnkhr6cvy3lk6q` (`ci_id`),
  CONSTRAINT `FK7xpvxpv5lantnkhr6cvy3lk6q` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_program`
--

DROP TABLE IF EXISTS `anchor_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_program` (
  `id` bigint NOT NULL,
  `adhoc_prog_limit` int NOT NULL,
  `anchor_onboarding_date` datetime DEFAULT NULL,
  `counter_party_grace_period` int NOT NULL,
  `cp_max_limit` int NOT NULL,
  `cp_min_limit` int NOT NULL,
  `door_to_door` int NOT NULL,
  `funding_percent` int NOT NULL,
  `funding_type` varchar(255) NOT NULL,
  `interest_app_tye` varchar(255) NOT NULL,
  `interest_calculation` varchar(255) NOT NULL,
  `interest_ownership` varchar(255) NOT NULL,
  `interest_payment_frequency` varchar(255) NOT NULL,
  `max_drawdown` int NOT NULL,
  `overall_prog_limit` int NOT NULL,
  `penal_interest` int NOT NULL,
  `pricing_roi_max` int NOT NULL,
  `pricing_roi_min` int NOT NULL,
  `processing_fees_max` int NOT NULL,
  `processing_fees_min` int NOT NULL,
  `regular_prog_limit` int NOT NULL,
  `repayment_nature` varchar(255) NOT NULL,
  `repayment_type` varchar(255) NOT NULL,
  `security_covg_primary` varchar(255) DEFAULT NULL,
  `security_covg_secondry` varchar(255) DEFAULT NULL,
  `stop_supply_trigger_days` int NOT NULL,
  `sub_product` varchar(255) NOT NULL,
  `tenure` int NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK58eetc7dvjmftlsyid2fmalat` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `anchor_program_details`
--

DROP TABLE IF EXISTS `anchor_program_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anchor_program_details` (
  `id` bigint NOT NULL,
  `adhoc_prog_limit` float NOT NULL,
  `anchor_onboarding_date` datetime(6) DEFAULT NULL,
  `counter_party_grace_period` float NOT NULL,
  `cp_max_limit` float NOT NULL,
  `cp_min_limit` float NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `door_to_door` float NOT NULL,
  `funding_percent` float NOT NULL,
  `funding_type` varchar(255) NOT NULL,
  `interest_app_tye` varchar(255) NOT NULL,
  `interest_calculation` float NOT NULL,
  `interest_ownership` varchar(255) NOT NULL,
  `interest_payment_frequency` varchar(255) NOT NULL,
  `max_drawdown` float NOT NULL,
  `overall_prog_limit` float NOT NULL,
  `penal_interest` float NOT NULL,
  `pricing_roi_max` float NOT NULL,
  `pricing_roi_min` float NOT NULL,
  `processing_fees_max` float NOT NULL,
  `processing_fees_min` float NOT NULL,
  `regular_prog_limit` float NOT NULL,
  `repayment_nature` varchar(255) NOT NULL,
  `repayment_type` varchar(255) NOT NULL,
  `security_covg_primary` varchar(255) DEFAULT NULL,
  `security_covg_secondry` varchar(255) DEFAULT NULL,
  `stop_supply_trigger_days` float NOT NULL,
  `tenure` float NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1emdma93ujmt1j7n351e0vg3y` (`ci_id`),
  CONSTRAINT `FK1emdma93ujmt1j7n351e0vg3y` FOREIGN KEY (`ci_id`) REFERENCES `anchor_customer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details`
--

DROP TABLE IF EXISTS `application_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details` (
  `id` bigint NOT NULL,
  `app_type` int NOT NULL,
  `type` int NOT NULL,
  `cust_id` bigint DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjiup1lsds19paqe2e9mb3wb2l` (`cust_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_anchor_address_details_entity_list`
--

DROP TABLE IF EXISTS `application_details_anchor_address_details_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_anchor_address_details_entity_list` (
  `application_entity_id` bigint NOT NULL,
  `anchor_address_details_entity_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_jtvgyl9lhps53f3kyhgn1ivbt` (`anchor_address_details_entity_list_id`),
  KEY `FK8gbmdb8lr6g9u2f58ihkwxvg8` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_anchor_authorized_entities`
--

DROP TABLE IF EXISTS `application_details_anchor_authorized_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_anchor_authorized_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_authorized_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_8y37bstsj7bwuvv177hvvcpoq` (`anchor_authorized_entities_id`),
  KEY `FKnt3xp744kx5ll7wejabk4y47m` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_anchor_basic_details`
--

DROP TABLE IF EXISTS `application_details_anchor_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_anchor_basic_details` (
  `application_entity_id` bigint NOT NULL,
  `anchor_basic_details_id` bigint NOT NULL,
  UNIQUE KEY `UK_b0s3ev2ywbruve15q80dc80hb` (`anchor_basic_details_id`),
  KEY `FKaunf1nf8dbkhn3urlep9v1cq7` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_anchor_gst_entities`
--

DROP TABLE IF EXISTS `application_details_anchor_gst_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_anchor_gst_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_gst_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_6gg7bnyydbbj6c4d5cgl29sha` (`anchor_gst_entities_id`),
  KEY `FK2oa6bclexhi79hvlh2u6ydgbm` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_anchor_key_entities`
--

DROP TABLE IF EXISTS `application_details_anchor_key_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_anchor_key_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_key_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_h9pn4vhsumnn6ainrsqs9jvkn` (`anchor_key_entities_id`),
  KEY `FKq83ecfibgp1e0jcc98e4300ji` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_beneficiary_entities`
--

DROP TABLE IF EXISTS `application_details_beneficiary_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_beneficiary_entities` (
  `application_entity_id` bigint NOT NULL,
  `beneficiary_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_he67x21ngq10v7fggws8mx3bo` (`beneficiary_entities_id`),
  KEY `FKdlvlqf29mqxxfjud42ded2lrm` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_counter_party_credit_policy_entities`
--

DROP TABLE IF EXISTS `application_details_counter_party_credit_policy_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_counter_party_credit_policy_entities` (
  `application_entity_id` bigint NOT NULL,
  `counter_party_credit_policy_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_iv4q42xn2k0g8fswctufrieaf` (`counter_party_credit_policy_entities_id`),
  KEY `FKoyyo5yg6pb9tq7vbllly8j9od` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info`
--

DROP TABLE IF EXISTS `application_details_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info` (
  `id` bigint NOT NULL,
  `app_type` int NOT NULL,
  `type` int NOT NULL,
  `cust_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsyfu19mg428atss57h162r5n` (`cust_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_anchor_address_details_entity_list`
--

DROP TABLE IF EXISTS `application_details_info_anchor_address_details_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_anchor_address_details_entity_list` (
  `application_entity_id` bigint NOT NULL,
  `anchor_address_details_entity_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_i5gi03g7apw9da0wj06sfs1b1` (`anchor_address_details_entity_list_id`),
  KEY `FKlswwe10jnq2l8inbyhh44h6qb` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_anchor_authorized_entities`
--

DROP TABLE IF EXISTS `application_details_info_anchor_authorized_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_anchor_authorized_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_authorized_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_9q4di29n6dbu2lox1ns6im4nf` (`anchor_authorized_entities_id`),
  KEY `FKliw2315einr7x33av4bdj2prl` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_anchor_basic_details`
--

DROP TABLE IF EXISTS `application_details_info_anchor_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_anchor_basic_details` (
  `application_entity_id` bigint NOT NULL,
  `anchor_basic_details_id` bigint NOT NULL,
  UNIQUE KEY `UK_gbpwkcd11l1w00eof3q5m9rfl` (`anchor_basic_details_id`),
  KEY `FKetu7p6stsujk4of5agxc2mg6o` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_anchor_gst_entities`
--

DROP TABLE IF EXISTS `application_details_info_anchor_gst_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_anchor_gst_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_gst_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_qkk45tdaxy4irsutibdllr9o6` (`anchor_gst_entities_id`),
  KEY `FK2ndftv4atqlc090ygl414odna` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_anchor_key_entities`
--

DROP TABLE IF EXISTS `application_details_info_anchor_key_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_anchor_key_entities` (
  `application_entity_id` bigint NOT NULL,
  `anchor_key_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_4iqelyte85xa80tht372xoccf` (`anchor_key_entities_id`),
  KEY `FK5fp5upsc9lrb5sjot6o04jcmw` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_beneficiary_entities`
--

DROP TABLE IF EXISTS `application_details_info_beneficiary_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_beneficiary_entities` (
  `application_entity_id` bigint NOT NULL,
  `beneficiary_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_joyfvijfdvporgdpio6g5lqgc` (`beneficiary_entities_id`),
  KEY `FK52g9l5xvto9w6kikdmpmv32kx` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_counter_party_credit_policy_entities`
--

DROP TABLE IF EXISTS `application_details_info_counter_party_credit_policy_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_counter_party_credit_policy_entities` (
  `application_entity_id` bigint NOT NULL,
  `counter_party_credit_policy_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_gmk9v1qra4lvsgd1kuftpcgwx` (`counter_party_credit_policy_entities_id`),
  KEY `FKeilsoov2woonysutdh607nrnc` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_program_details_entities`
--

DROP TABLE IF EXISTS `application_details_info_program_details_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_program_details_entities` (
  `application_entity_id` bigint NOT NULL,
  `program_details_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_kxlgkr8n8mjko73ck0hv0r0pq` (`program_details_entities_id`),
  KEY `FKqja6px8mfwurv1mkgae017mr4` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_proposed_products_entities`
--

DROP TABLE IF EXISTS `application_details_info_proposed_products_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_proposed_products_entities` (
  `application_entity_id` bigint NOT NULL,
  `proposed_products_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_3g3yjwkfypjxdlcg96xilkxdi` (`proposed_products_entities_id`),
  KEY `FK821h9ly18eije4sbigrod6vfv` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_info_term_sheet_entity`
--

DROP TABLE IF EXISTS `application_details_info_term_sheet_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_info_term_sheet_entity` (
  `application_entity_id` bigint NOT NULL,
  `term_sheet_entity_id` bigint NOT NULL,
  UNIQUE KEY `UK_8yrj70uqc91tvrdq8g9c5my2b` (`term_sheet_entity_id`),
  KEY `FKa66idi3ynk5b34y1rh2evihac` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_program_details_entities`
--

DROP TABLE IF EXISTS `application_details_program_details_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_program_details_entities` (
  `application_entity_id` bigint NOT NULL,
  `program_details_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_k87os659kn11ryq4s8wjrt9ox` (`program_details_entities_id`),
  KEY `FKnv5th0bjjve88jru85vq7nxlj` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_proposed_products_entities`
--

DROP TABLE IF EXISTS `application_details_proposed_products_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_proposed_products_entities` (
  `application_entity_id` bigint NOT NULL,
  `proposed_products_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_ibhk0qneqc17iw3exfv7982en` (`proposed_products_entities_id`),
  KEY `FKto5iwtwfmfl3u1mrulau2fcbm` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_details_term_sheet_entity`
--

DROP TABLE IF EXISTS `application_details_term_sheet_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_details_term_sheet_entity` (
  `application_entity_id` bigint NOT NULL,
  `term_sheet_entity_id` bigint NOT NULL,
  UNIQUE KEY `UK_moq5ei9m19hccuo2pk0i235k6` (`term_sheet_entity_id`),
  KEY `FKcyvl6uraffvts6imcv2655uvd` (`application_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client_details`
--

DROP TABLE IF EXISTS `client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_details` (
  `id` bigint NOT NULL,
  `cin` varchar(255) NOT NULL,
  `pan` varchar(255) NOT NULL,
  `cust_name` varchar(255) NOT NULL,
  `anchor_id` bigint NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `cp_id` bigint NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `commercial_cc_table`
--

DROP TABLE IF EXISTS `commercial_cc_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_cc_table` (
  `id` bigint NOT NULL,
  `adhoc_limit_remarks` varchar(255) NOT NULL,
  `anchor_remarks` varchar(255) NOT NULL,
  `condition_adhoc_limit` varchar(255) NOT NULL,
  `condition_anchor_name` varchar(255) NOT NULL,
  `condition_credit_period` varchar(255) NOT NULL,
  `condition_door` varchar(255) NOT NULL,
  `condition_interest_rate` varchar(255) NOT NULL,
  `condition_interest_type` varchar(255) NOT NULL,
  `condition_invoice_ageing` varchar(255) NOT NULL,
  `condition_margin` varchar(255) NOT NULL,
  `condition_pf` varchar(255) NOT NULL,
  `condition_product` varchar(255) NOT NULL,
  `condition_regular_limit` varchar(255) NOT NULL,
  `condition_renewal` varchar(255) NOT NULL,
  `condition_renewal_period` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `credit_period_remarks` varchar(255) NOT NULL,
  `door_remarks` varchar(255) NOT NULL,
  `interest_rate_remarks` varchar(255) NOT NULL,
  `interest_type_remarks` varchar(255) NOT NULL,
  `invoice_ageing_remarks` varchar(255) NOT NULL,
  `margin_remarks` varchar(255) NOT NULL,
  `pf_remarks` varchar(255) NOT NULL,
  `product_remarks` varchar(255) NOT NULL,
  `regular_limits_remarks` varchar(255) NOT NULL,
  `renewal_period_remarks` varchar(255) NOT NULL,
  `renewal_remarks` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ro5xrg9kv1mjecv8n7bwqp1k` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `counter_party_credit_policy`
--

DROP TABLE IF EXISTS `counter_party_credit_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `counter_party_credit_policy` (
  `id` bigint NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `credit_policy_master_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmeiyjxcyph2au133bg07ueee3` (`app_id`),
  KEY `FKl5frbfd0q68ce24myiddl62v8` (`credit_policy_master_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_basic_details`
--

DROP TABLE IF EXISTS `cp_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_basic_details` (
  `id` bigint NOT NULL,
  `cin_number` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `constitution` varchar(255) NOT NULL,
  `cus_contact_name` varchar(255) NOT NULL,
  `cus_contact_email` varchar(255) NOT NULL,
  `cus_contact_number` varchar(255) NOT NULL,
  `gst_number` varchar(255) NOT NULL,
  `pan` varchar(255) NOT NULL,
  `rm_name` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `sub_source` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsvhowo3lnbi8cs8dp6nwm0wmw` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_collateral_details`
--

DROP TABLE IF EXISTS `cp_collateral_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_collateral_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `cm_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2poyao2nbvhyublkwpti0t45` (`app_id`),
  KEY `FKoiqjkesrvfafsas1ubmnqydgk` (`cm_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_collateral_master`
--

DROP TABLE IF EXISTS `cp_collateral_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_collateral_master` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `datatype` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sequence` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `regex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_commercial_table`
--

DROP TABLE IF EXISTS `cp_commercial_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_commercial_table` (
  `id` bigint NOT NULL,
  `adhoc_limit_remarks` varchar(255) DEFAULT NULL,
  `anchor_remarks` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `credit_period_remarks` varchar(255) DEFAULT NULL,
  `door_remarks` varchar(255) DEFAULT NULL,
  `interest_rate_remarks` varchar(255) DEFAULT NULL,
  `interest_type_remarks` varchar(255) DEFAULT NULL,
  `invoice_ageing_remarks` varchar(255) DEFAULT NULL,
  `margin_remarks` varchar(255) DEFAULT NULL,
  `pf_remarks` varchar(255) DEFAULT NULL,
  `product_remarks` varchar(255) DEFAULT NULL,
  `regular_limits_remarks` varchar(255) DEFAULT NULL,
  `renewal_period_remarks` varchar(255) DEFAULT NULL,
  `renewal_remarks` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe2qtp1t96fds4545p4yxrb7co` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_credit_policy_details`
--

DROP TABLE IF EXISTS `cp_credit_policy_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_credit_policy_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `cp_master_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr6i9217oct7mkuhke98v7bitd` (`app_id`),
  KEY `FKku5vvhmxrll3ujs4nt1ub7bsx` (`cp_master_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_dd_details`
--

DROP TABLE IF EXISTS `cp_dd_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_dd_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `dd_master_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmis5qgv4ngxqubq33m1d4rhvv` (`app_id`),
  KEY `FKkjlt8bqw1hibawdwjb55ysff5` (`dd_master_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_dd_master`
--

DROP TABLE IF EXISTS `cp_dd_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_dd_master` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `datatype` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sequence` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `regex` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_debt_profile`
--

DROP TABLE IF EXISTS `cp_debt_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_debt_profile` (
  `id` bigint NOT NULL,
  `bank_fi` varchar(255) DEFAULT NULL,
  `emi` varchar(255) NOT NULL,
  `facility_type` varchar(255) NOT NULL,
  `interest_rate` varchar(255) NOT NULL,
  `outstanding_on_date` varchar(255) NOT NULL,
  `remarks` varchar(255) NOT NULL,
  `sanction_date` varchar(255) NOT NULL,
  `sanction_limit` varchar(255) NOT NULL,
  `security` varchar(255) NOT NULL,
  `seq` varchar(255) NOT NULL,
  `specific_limit` varchar(255) NOT NULL,
  `tenure` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmnble848cuyq84caxvuh335d4` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_fund_requirement_details`
--

DROP TABLE IF EXISTS `cp_fund_requirement_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_fund_requirement_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `fund_req_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK98bt3o2hfndjayqaetxcxo76y` (`app_id`),
  KEY `FKbu4k6lh6fdqmwkuqbb16eotpt` (`fund_req_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_fund_requirement_master`
--

DROP TABLE IF EXISTS `cp_fund_requirement_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_fund_requirement_master` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `questions` varchar(255) NOT NULL,
  `requirement_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `sequence_no` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_limit_eligibility_details`
--

DROP TABLE IF EXISTS `cp_limit_eligibility_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_limit_eligibility_details` (
  `id` bigint NOT NULL,
  `adhoc_limit` varchar(255) NOT NULL,
  `anchor_recommended_amount` varchar(255) NOT NULL,
  `approtioned_limits` varchar(255) NOT NULL,
  `calculated_limit_wo_set_off` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `credit_period` varchar(255) NOT NULL,
  `current_limit` varchar(255) NOT NULL,
  `customer_requested_amount` varchar(255) NOT NULL,
  `door_to_door` varchar(255) NOT NULL,
  `eligible_limit` varchar(255) NOT NULL,
  `existing_scf_limits` varchar(255) NOT NULL,
  `expected_growth` varchar(255) NOT NULL,
  `invoice_ageing` varchar(255) NOT NULL,
  `margin` varchar(255) NOT NULL,
  `model_limit` varchar(255) NOT NULL,
  `monthly_average` varchar(255) NOT NULL,
  `product` varchar(255) NOT NULL,
  `proposed_limit` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint DEFAULT NULL,
  `cust_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK887r2tsm1t79jya7s912xyds3` (`app_id`),
  KEY `FKryfkl58v5y5ax1pnny3n3v6b4` (`cust_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_proposed_products`
--

DROP TABLE IF EXISTS `cp_proposed_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_proposed_products` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `product` varchar(255) NOT NULL,
  `proposed` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint DEFAULT NULL,
  `cust_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2wccofc6638iu5seg371if4c6` (`app_id`),
  KEY `FKj2wknc7ax11y6ntpyt25w66di` (`cust_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_soft_policy_details`
--

DROP TABLE IF EXISTS `cp_soft_policy_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_soft_policy_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `soft_policy_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK43w61sg78ok7n9tsu6v6kdtx1` (`app_id`),
  KEY `FKhbmxlfexgsmvnkqis9jp69wxn` (`soft_policy_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_soft_policy_master`
--

DROP TABLE IF EXISTS `cp_soft_policy_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_soft_policy_master` (
  `id` bigint NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `sp_parameter` varchar(255) NOT NULL,
  `sp_parameter_display_name` varchar(255) NOT NULL,
  `sp_parameter_max_threshold` varchar(255) NOT NULL,
  `sp_parameter_min_threshold` varchar(255) NOT NULL,
  `sp_type` varchar(255) NOT NULL,
  `sp_type_display_name` varchar(255) NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cp_term_sheet`
--

DROP TABLE IF EXISTS `cp_term_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cp_term_sheet` (
  `id` bigint NOT NULL,
  `adhoc_limit` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `credit_period` varchar(255) NOT NULL,
  `door_to_door` varchar(255) NOT NULL,
  `interest_rate` varchar(255) NOT NULL,
  `interest_rate_type` varchar(255) NOT NULL,
  `invoice_ageing` varchar(255) NOT NULL,
  `margin` varchar(255) NOT NULL,
  `pf` varchar(255) NOT NULL,
  `product` varchar(255) NOT NULL,
  `regular_limit` varchar(255) NOT NULL,
  `renewal` varchar(255) NOT NULL,
  `renewal_period` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint DEFAULT NULL,
  `cust_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3xlurdlmopuhf0hyk6rplsbii` (`app_id`),
  KEY `FK2f1sm53knf6on3qbnjbj6xvu2` (`cust_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_norms_anchor_table`
--

DROP TABLE IF EXISTS `credit_norms_anchor_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_norms_anchor_table` (
  `id` bigint NOT NULL,
  `bureau_comer` int NOT NULL,
  `bureau_consu` int NOT NULL,
  `adjusted_leverage` float NOT NULL,
  `anchor_dependency` int NOT NULL,
  `anchor_dependency_month` int NOT NULL,
  `approver_info` varchar(255) NOT NULL,
  `cheque_bounce` int NOT NULL,
  `churn` int NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `cur_od_cred_comer` float NOT NULL,
  `current_overdue_cred_comer` int NOT NULL,
  `current_overdue_cred_consu` float NOT NULL,
  `current_overdue_comer` int NOT NULL,
  `current_overdue_consu` float NOT NULL,
  `current_ratio` float NOT NULL,
  `epfo_delay` int NOT NULL,
  `geo_restriction` varchar(255) NOT NULL,
  `grp_of_vintage_corp_guarantee` int NOT NULL,
  `gst_certificate` varchar(255) NOT NULL,
  `gst_payment_delay` int NOT NULL,
  `market_check` float NOT NULL,
  `min_anu_purch_anchor` float NOT NULL,
  `min_business_vintage` int NOT NULL,
  `min_turn_over` float NOT NULL,
  `min_vintage_with_anchor` int NOT NULL,
  `networth` int NOT NULL,
  `pan_business` varchar(255) NOT NULL,
  `pat` float NOT NULL,
  `payment_history` float NOT NULL,
  `pbt` float NOT NULL,
  `penal` float NOT NULL,
  `positive_atnw` int NOT NULL,
  `prom_guar_pan` varchar(255) NOT NULL,
  `prom_guar_pan_ovd` varchar(255) NOT NULL,
  `remarks` varchar(255) NOT NULL,
  `restructured` int NOT NULL,
  `sales_growth` int NOT NULL,
  `sat_report` float NOT NULL,
  `satu_due_delay` int NOT NULL,
  `sma_a` int NOT NULL,
  `sma_b` int NOT NULL,
  `sma_c` int NOT NULL,
  `sma_d` int NOT NULL,
  `stage_id` bigint NOT NULL,
  `status` bigint NOT NULL,
  `sub_loss_npa` int NOT NULL,
  `tol_atnw` float NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `wilful_suit_comer` int NOT NULL,
  `wilful_suit_consu` int NOT NULL,
  `working_capital` varchar(255) NOT NULL,
  `app_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8xm5sb1cfbuxe3k5ikbyx558l` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_norms_details`
--

DROP TABLE IF EXISTS `credit_norms_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_norms_details` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `value` varchar(255) NOT NULL,
  `app_id` bigint DEFAULT NULL,
  `cn_master_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKma79cevk9mv28nt310vc2m1n1` (`app_id`),
  KEY `FKhnuui7b5375b49ss6cjhqgi6v` (`cn_master_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_norms_master`
--

DROP TABLE IF EXISTS `credit_norms_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_norms_master` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `datatype` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sequence` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `regex` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_policy_master`
--

DROP TABLE IF EXISTS `credit_policy_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_policy_master` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) NOT NULL,
  `mandatory` varchar(255) NOT NULL,
  `policy_name` varchar(255) NOT NULL,
  `policy_type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) NOT NULL,
  `regex` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info`
--

DROP TABLE IF EXISTS `customer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cin` varchar(21) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `pan` varchar(10) NOT NULL,
  `product` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_njvvd8lfdi3bdy5pch0esqeri` (`cin`),
  UNIQUE KEY `UK_idnf6o90v07sskqo07prcqvv0` (`pan`)
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_anchor_address_details_entity_list`
--

DROP TABLE IF EXISTS `customer_info_anchor_address_details_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_anchor_address_details_entity_list` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_address_details_entity_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_716dwabmcprsam0bl49dhy67j` (`anchor_address_details_entity_list_id`),
  KEY `FK6k3tyk1dr17niuniq3qat730s` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_anchor_authorized_entities`
--

DROP TABLE IF EXISTS `customer_info_anchor_authorized_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_anchor_authorized_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_authorized_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_7cjbn2ninn9xuq3xbwb1wpixj` (`anchor_authorized_entities_id`),
  KEY `FK8nu8w7xru6nnwg1sr2q5vxkpt` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_anchor_basic_details`
--

DROP TABLE IF EXISTS `customer_info_anchor_basic_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_anchor_basic_details` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_basic_details_id` bigint NOT NULL,
  UNIQUE KEY `UK_sfbdogeci5g47ot3nf5f7pfoy` (`anchor_basic_details_id`),
  KEY `FKplge1af2tgdeqa3x1srmkunnk` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_anchor_gst_entities`
--

DROP TABLE IF EXISTS `customer_info_anchor_gst_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_anchor_gst_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_gst_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_4a91wlrdsoctyfsludfa2xyyr` (`anchor_gst_entities_id`),
  KEY `FKtrgjpal06a01qnmiie6xkogcw` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_anchor_key_entities`
--

DROP TABLE IF EXISTS `customer_info_anchor_key_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_anchor_key_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `anchor_key_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_cs6bwui1gs7p3wial3u79c0f9` (`anchor_key_entities_id`),
  KEY `FKi7jn1y8af6l1rf3wdddbcu08s` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_beneficiary_entities`
--

DROP TABLE IF EXISTS `customer_info_beneficiary_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_beneficiary_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `beneficiary_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_pncyvx4w73393x8d1932dxg93` (`beneficiary_entities_id`),
  KEY `FKlupumfrypx6yhm74bsalciglo` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_counter_party_credit_policy_entities`
--

DROP TABLE IF EXISTS `customer_info_counter_party_credit_policy_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_counter_party_credit_policy_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `counter_party_credit_policy_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_4d6byp34al7jh7ycgjjmg848i` (`counter_party_credit_policy_entities_id`),
  KEY `FKin8qel2omy8g6c31scec1exba` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_program_details_entities`
--

DROP TABLE IF EXISTS `customer_info_program_details_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_program_details_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `program_details_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_55d64i7xnjkw62x90jp7v62u0` (`program_details_entities_id`),
  KEY `FK478yaq8eebxf7vdutxg16mjxd` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_proposed_products_entities`
--

DROP TABLE IF EXISTS `customer_info_proposed_products_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_proposed_products_entities` (
  `customer_info_entity_id` bigint NOT NULL,
  `proposed_products_entities_id` bigint NOT NULL,
  UNIQUE KEY `UK_97ttwbx2d9s67gtl2n5fr7mo9` (`proposed_products_entities_id`),
  KEY `FKce1xc3tn4uo9dg3a7y0be60lv` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_info_term_sheet_entity`
--

DROP TABLE IF EXISTS `customer_info_term_sheet_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info_term_sheet_entity` (
  `customer_info_entity_id` bigint NOT NULL,
  `term_sheet_entity_id` bigint NOT NULL,
  UNIQUE KEY `UK_nlql5b099i3vix8jgjp1m821w` (`term_sheet_entity_id`),
  KEY `FK3214t1rqo4spb80h48syfi4vs` (`customer_info_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_report`
--

DROP TABLE IF EXISTS `document_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_report` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `document_sub_type` varchar(255) DEFAULT NULL,
  `document_sub_type_name` varchar(255) DEFAULT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `document_type_name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_sub_type`
--

DROP TABLE IF EXISTS `document_sub_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_sub_type` (
  `id` bigint NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sequance_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `doc_type_id` bigint NOT NULL,
  `required` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlly3ahqu46b6injhxnmuwgr7k` (`doc_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_type`
--

DROP TABLE IF EXISTS `document_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_type` (
  `id` bigint NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `anchor_counter_party` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `enable_time_line` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sequance_no` varchar(255) DEFAULT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fund_req_answer`
--

DROP TABLE IF EXISTS `fund_req_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_req_answer` (
  `id` bigint NOT NULL,
  `cp_id` bigint NOT NULL,
  `value` varchar(255) NOT NULL,
  `fund_req_question_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9d7n1yvyv7t834qio4q7tdh7t` (`fund_req_question_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fund_req_question`
--

DROP TABLE IF EXISTS `fund_req_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_req_question` (
  `id` bigint NOT NULL,
  `data_type` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `questions` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_credit_norms`
--

DROP TABLE IF EXISTS `info_credit_norms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_credit_norms` (
  `id` bigint NOT NULL,
  `bureau_comer` int NOT NULL,
  `bureau_consu` int NOT NULL,
  `adjusted_leverage` float NOT NULL,
  `anchor_dependency` int NOT NULL,
  `anchor_dependency_month` int NOT NULL,
  `approver_info` varchar(255) NOT NULL,
  `cheque_bounce` int NOT NULL,
  `churn` int NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `cur_od_cred_comer` float NOT NULL,
  `current_overdue_cred_comer` int NOT NULL,
  `current_overdue_cred_consu` float NOT NULL,
  `current_overdue_comer` int NOT NULL,
  `current_overdue_consu` float NOT NULL,
  `current_ratio` float NOT NULL,
  `epfo_delay` int NOT NULL,
  `geo_restriction` varchar(255) NOT NULL,
  `grp_of_vintage_corp_guarantee` int NOT NULL,
  `gst_certificate` varchar(255) NOT NULL,
  `gst_payment_delay` int NOT NULL,
  `market_check` varchar(255) NOT NULL,
  `min_anu_purch_anchor` float NOT NULL,
  `min_business_vintage` int NOT NULL,
  `min_turn_over` float NOT NULL,
  `min_vintage_with_anchor` int NOT NULL,
  `networth` int NOT NULL,
  `pan_business` varchar(255) NOT NULL,
  `pat` float NOT NULL,
  `payment_history` varchar(255) NOT NULL,
  `pbt` float NOT NULL,
  `penal` float NOT NULL,
  `positive_atnw` int NOT NULL,
  `prom_guar_pan` varchar(255) NOT NULL,
  `prom_guar_pan_ovd` varchar(255) NOT NULL,
  `remarks` varchar(255) NOT NULL,
  `restructured` int NOT NULL,
  `sales_growth` int NOT NULL,
  `sat_report` varchar(255) NOT NULL,
  `satu_due_delay` int NOT NULL,
  `sma_a` int NOT NULL,
  `sma_b` int NOT NULL,
  `sma_c` int NOT NULL,
  `sma_d` int NOT NULL,
  `stage_id` bigint NOT NULL,
  `status` bigint NOT NULL,
  `sub_loss_npa` int NOT NULL,
  `tol_atnw` float NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `wilful_suit_comer` int NOT NULL,
  `wilful_suit_consu` int NOT NULL,
  `working_capital` int NOT NULL,
  `app_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK51tl817i76i0vg3pp3or6v6d8` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keycloak_group`
--

DROP TABLE IF EXISTS `keycloak_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keycloak_group` (
  `id` bigint NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `group_path` varchar(255) NOT NULL,
  `sub_group` tinyblob NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keycloak_user`
--

DROP TABLE IF EXISTS `keycloak_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keycloak_user` (
  `id` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `email_verified` bit(1) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `user_creation_timestamp` datetime DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7tqbaibg4rdf48i1lb3j9u9up` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rbac_group`
--

DROP TABLE IF EXISTS `rbac_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rbac_group` (
  `id` bigint NOT NULL,
  `application_count` int NOT NULL,
  `community` int NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `is_enable` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rbac_permission`
--

DROP TABLE IF EXISTS `rbac_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rbac_permission` (
  `id` bigint NOT NULL,
  `approve` bit(1) NOT NULL,
  `view` bit(1) NOT NULL,
  `reject` bit(1) NOT NULL,
  `is_return` bit(1) NOT NULL,
  `is_submit` bit(1) NOT NULL,
  `role` varchar(255) NOT NULL,
  `stage_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6d56o9cvo5d2by3ldjmd3qa8i` (`stage_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rbac_token`
--

DROP TABLE IF EXISTS `rbac_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rbac_token` (
  `id` bigint NOT NULL,
  `browser_id` varchar(255) NOT NULL,
  `flag` int NOT NULL,
  `jwt_token` longtext NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `expired_time` datetime DEFAULT NULL,
  `refresh_token` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rbac_user`
--

DROP TABLE IF EXISTS `rbac_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rbac_user` (
  `id` bigint NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfohb6lssrylax09w34hoh5f9c` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `remarks`
--

DROP TABLE IF EXISTS `remarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remarks` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `stepper_tab` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `ci_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrh780i369cuyb3dskdx24j7hf` (`ci_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sampleTable`
--

DROP TABLE IF EXISTS `sampleTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sampleTable` (
  `ID` int NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `Age` int DEFAULT NULL,
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `idx_sampleTable_LastName` (`LastName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `soft_policy_master_sub_type`
--

DROP TABLE IF EXISTS `soft_policy_master_sub_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soft_policy_master_sub_type` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sequance_number` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `soft_policy_type_id` bigint NOT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `regex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgl0fay6s59e4b6qo1q535e84o` (`soft_policy_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `soft_policy_master_type`
--

DROP TABLE IF EXISTS `soft_policy_master_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soft_policy_master_type` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sequance_no` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_app_info`
--

DROP TABLE IF EXISTS `tab_app_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_app_info` (
  `id` bigint NOT NULL,
  `created_at` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `customer_id` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `ps_id` bigint NOT NULL,
  `psd_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi60vxess41fi26k4bg4qdn49h` (`ps_id`),
  KEY `FKetp5cbfhnd4m3uefqk5h3gxhh` (`psd_id`),
  CONSTRAINT `FKetp5cbfhnd4m3uefqk5h3gxhh` FOREIGN KEY (`psd_id`) REFERENCES `tab_prod_schema_details` (`id`),
  CONSTRAINT `FKi60vxess41fi26k4bg4qdn49h` FOREIGN KEY (`ps_id`) REFERENCES `tab_prod_schema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_app_status`
--

DROP TABLE IF EXISTS `tab_app_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_app_status` (
  `id` bigint NOT NULL,
  `approver_id` varchar(255) NOT NULL,
  `status_id` varchar(255) NOT NULL,
  `ai_id` bigint NOT NULL,
  `ws_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhddo552r4ipef6iu1h9jgtq7w` (`ai_id`),
  KEY `FKbw608i3qvpmbgso735ydlf29d` (`ws_id`),
  CONSTRAINT `FKbw608i3qvpmbgso735ydlf29d` FOREIGN KEY (`ws_id`) REFERENCES `tab_workflow_stage_tab` (`id`),
  CONSTRAINT `FKhddo552r4ipef6iu1h9jgtq7w` FOREIGN KEY (`ai_id`) REFERENCES `tab_app_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_prod_schema`
--

DROP TABLE IF EXISTS `tab_prod_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_prod_schema` (
  `id` bigint NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_prod_schema_details`
--

DROP TABLE IF EXISTS `tab_prod_schema_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_prod_schema_details` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subproduct_name` varchar(255) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_on` varchar(255) DEFAULT NULL,
  `ps_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf9m80l6mhfawkqpt21ib1l1v5` (`ps_id`),
  CONSTRAINT `FKf9m80l6mhfawkqpt21ib1l1v5` FOREIGN KEY (`ps_id`) REFERENCES `tab_prod_schema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_workflow_approve_info`
--

DROP TABLE IF EXISTS `tab_workflow_approve_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_workflow_approve_info` (
  `id` bigint NOT NULL,
  `approver_id` varchar(255) DEFAULT NULL,
  `approver_role` varchar(255) DEFAULT NULL,
  `psd_id` bigint NOT NULL,
  `wfs_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK50o5q4b2c5fqk7aidtbgsc2yr` (`psd_id`),
  KEY `FKt2mqhbmwpwmwnyo2glsjx0cy1` (`wfs_id`),
  CONSTRAINT `FK50o5q4b2c5fqk7aidtbgsc2yr` FOREIGN KEY (`psd_id`) REFERENCES `tab_prod_schema_details` (`id`),
  CONSTRAINT `FKt2mqhbmwpwmwnyo2glsjx0cy1` FOREIGN KEY (`wfs_id`) REFERENCES `tab_workflow_stage_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_workflow_stage_tab`
--

DROP TABLE IF EXISTS `tab_workflow_stage_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tab_workflow_stage_tab` (
  `id` bigint NOT NULL,
  `level` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `sub_product_id` varchar(255) NOT NULL,
  `tat` varchar(255) NOT NULL,
  `psd_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8jbgts2stglg8wx1theuk6r0o` (`psd_id`),
  CONSTRAINT `FK8jbgts2stglg8wx1theuk6r0o` FOREIGN KEY (`psd_id`) REFERENCES `tab_prod_schema_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_stage`
--

DROP TABLE IF EXISTS `workflow_stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_stage` (
  `id` bigint NOT NULL,
  `client_type` varchar(255) NOT NULL,
  `stage` varchar(255) NOT NULL,
  `group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKed1xdy1gtfnpiqa68k7urcic4` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_stage_approval_status`
--

DROP TABLE IF EXISTS `workflow_stage_approval_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_stage_approval_status` (
  `id` bigint NOT NULL,
  `user_info` varchar(255) NOT NULL,
  `timestamp` varchar(255) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `seq_no` int NOT NULL,
  `application_status` bigint NOT NULL,
  `app_id` bigint NOT NULL,
  `stage_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKljmp2dwf5sb6uv5hwben9hspk` (`app_id`),
  KEY `FK1nkcxlrubvsiwjoax7peefoqu` (`stage_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;



LOCK TABLES `rbac_group` WRITE;
/*!40000 ALTER TABLE `rbac_group` DISABLE KEYS */;
INSERT INTO `rbac_group` VALUES (1,2,1,'BUSINESS',1),(2,2,1,'CREDIT HEAD',1),(3,2,1,'RISK HEAD',1),(4,2,1,'CREDIT UNDERWRITER',1),(5,2,1,'RISK UNDERWRITER',1),(6,2,1,'CPA',1),(7,2,1,'CREDIT APPROVAL COMMITTEE',1),(8,2,1,'OPERATION MAKER',1),(9,2,1,'OPERATION CHECKER',1),(10,2,1,'COMMERCIAL APPROVAL COMMITTEE',1),(11,2,1,'CREDIT COMMITTEE',1);
/*!40000 ALTER TABLE `rbac_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rbac_permission`
--

LOCK TABLES `rbac_permission` WRITE;
/*!40000 ALTER TABLE `rbac_permission` DISABLE KEYS */;
INSERT INTO `rbac_permission` VALUES (1,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '','BUSINESS',6),(21,_binary '\0',_binary '\0',_binary '\0',_binary '',_binary '\0','CREDIT UNDERWRITER',3),(2,_binary '\0',_binary '',_binary '',_binary '\0',_binary '\0','BUSINESS',19),(3,_binary '',_binary '',_binary '',_binary '',_binary '','CREDIT HEAD',7),(4,_binary '',_binary '',_binary '',_binary '',_binary '','RISK HEAD',8),(5,_binary '',_binary '',_binary '',_binary '',_binary '','CREDIT UNDERWRITER',10),(6,_binary '\0',_binary '',_binary '',_binary '',_binary '','CREDIT UNDERWRITER',13),(7,_binary '\0',_binary '',_binary '',_binary '',_binary '','CREDIT UNDERWRITER',15),(8,_binary '',_binary '',_binary '',_binary '',_binary '','RISK UNDERWRITER',11),(9,_binary '\0',_binary '',_binary '',_binary '',_binary '','RISK UNDERWRITER',14),(10,_binary '\0',_binary '',_binary '',_binary '',_binary '','RISK UNDERWRITER',16),(11,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '','CPA',9),(12,_binary '\0',_binary '',_binary '\0',_binary '',_binary '','CPA',12),(13,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '','CPA',22),(14,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '','CPA',23),(15,_binary '',_binary '',_binary '',_binary '',_binary '','CREDIT APPROVAL COMMITTEE',17),(16,_binary '\0',_binary '',_binary '',_binary '',_binary '','OPERATION MAKER',20),(17,_binary '',_binary '',_binary '',_binary '',_binary '','OPERATION CHECKER',21),(18,_binary '',_binary '',_binary '',_binary '',_binary '','COMMERCIAL APPROVAL COMMITTEE',18),(19,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','CREDIT COMMITTEE',24),(20,_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '','CPA',1),(23,_binary '',_binary '',_binary '',_binary '',_binary '','CREDIT UNDERWRITER',25),(24,_binary '\0',_binary '',_binary '\0',_binary '',_binary '','OPERATION MAKER',4),(25,_binary '\0',_binary '',_binary '\0',_binary '',_binary '\0','OPERATION MAKER',26),(26,_binary '',_binary '',_binary '',_binary '',_binary '','OPERATION CHECKER',5),(27,_binary '',_binary '',_binary '',_binary '',_binary '','CPA',2);
/*!40000 ALTER TABLE `rbac_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflow_stage`
--

LOCK TABLES `workflow_stage` WRITE;
/*!40000 ALTER TABLE `workflow_stage` DISABLE KEYS */;
INSERT INTO `workflow_stage` VALUES (1,'1','A1',6),(2,'1','A2',6),(3,'1','A3',4),(4,'1','A4',8),(5,'1','A5',9),(6,'2','CP1',1),(7,'2','CP2',2),(8,'2','CP2',3),(9,'2','CP3',6),(10,'2','CP4',4),(11,'2','CP4',5),(12,'2','CP5',6),(13,'2','CP6',4),(14,'2','CP6',5),(15,'2','CP7',4),(16,'2','CP7',5),(17,'2','CP8',7),(18,'2','CP8',10),(19,'2','CP9',1),(20,'2','CP10',8),(21,'2','CP11',9),(23,'2','CP7',6),(22,'2','CP6',6),(24,'2','CP5',11),(25,'1','A3',4),(26,'1','A5',8);
/*!40000 ALTER TABLE `workflow_stage` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `cp_collateral_master` WRITE;
/*!40000 ALTER TABLE `cp_collateral_master` DISABLE KEYS */;
INSERT INTO `cp_collateral_master` VALUES (2293,'2022-11-25 14:38:40','','String','primarySecurity','Primary Security','primarySecurity','1','1','2022-11-25 14:38:40','','^[a-zA-Z ]*$'),(2294,'2022-11-25 14:39:39','','String','secondarySecurity','Secondary Security','secondarySecurity','2','1','2022-11-25 14:39:39','','^[a-zA-Z ]*$'),(2295,'2022-11-25 14:40:32','','String','guarantees','Guarantees','guarantees','3','1','2022-11-25 14:40:32','','^[a-zA-Z ]*$'),(2296,'2022-11-25 14:42:41','','String','pre-disbursementcreditconditions','Pre-Disbursement Credit Conditions','pre-disbursementcreditconditions','4','1','2022-11-25 14:42:41','','^[a-zA-Z ]*$'),(2297,'2022-11-25 14:44:44','','String','financialcovenants','Financial Covenants','financialcovenants','5','1','2022-11-25 14:44:44','','^[a-zA-Z ]*$'),(2298,'2022-11-25 14:49:21','','String','timeforcompliance','Time for Compliance','timeforcompliance','6','1','2022-11-25 14:49:21','','^[a-zA-Z ]*$'),(2299,'2022-11-25 14:51:35','','String','postdisbursmentcreditconditions','Post Disbursment Credit Conditions','postdisbursmentcreditconditions','7','1','2022-11-25 14:51:35','','^[a-zA-Z ]*$');
/*!40000 ALTER TABLE `cp_collateral_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cp_dd_master`
--

LOCK TABLES `cp_dd_master` WRITE;
/*!40000 ALTER TABLE `cp_dd_master` DISABLE KEYS */;
INSERT INTO `cp_dd_master` VALUES (2253,'2022-11-24 14:06:27','Balaji','TimeStamp','Date Of Visit','Date Of Visit','dateOfVisit','1','1','2022-11-24 14:06:27','','^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$'),(2260,'2022-11-24 14:33:09','Balaji','String','Name of the Person Meet and his roel/responsibility along with contact number','Name of the Person','nameOfThePerson','2','1','2022-11-24 14:33:09','','^[a-zA-Z ]*$'),(2261,'2022-11-24 14:33:52','Balaji','String','VCPL Attendees','VCPL Attendees','vcplAttendees','3','1','2022-11-24 14:33:52','','^[a-zA-Z ]*$'),(2262,'2022-11-24 14:21:21','Balaji','String','Address Of Premises Visited','Address Of Premises Visited','addressOfPremisesVisited','4','1','2022-11-24 14:21:21','','^[a-zA-Z ]*$'),(2263,'2022-11-24 14:26:48','Balaji','String','Observation On Facility','Observation On Facility','observationOnFacility','5','1','2022-11-24 14:26:48','','^[a-zA-Z ]*$'),(2264,'2022-11-24 14:29:47','Balaji','String','Business Model','Business Model','businessModel','6','1','2022-11-24 14:29:47','','^[a-zA-Z ]*$'),(2265,'2022-11-24 14:30:34','Balaji','String','Stock Observation','Stock Observation','stockObservation','7','1','2022-11-24 14:30:34','','^[a-zA-Z ]*$'),(2266,'2022-11-24 14:31:09','Balaji','String','Business Plans','Business Plans','businessPlans','8','1','2022-11-24 14:31:09','','^[a-zA-Z ]*$'),(2267,'2022-11-24 14:31:33','Balaji','String','Market Feedbacks','Market Feedbacks','marketFeedbacks','9','1','2022-11-24 14:31:33','','^[a-zA-Z ]*$'),(2268,'2022-11-24 14:35:14','Balaji','String','Market Information','Market Information','marketInformation','10','1','2022-11-24 14:35:14','','^[a-zA-Z ]*$'),(2269,'2022-11-24 14:35:14','Balaji','String','Promoter Backround','Promoter Backround','promoterbackround','11','1','2022-11-24 14:35:14',NULL,'^[a-zA-Z ]*$');
/*!40000 ALTER TABLE `cp_dd_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cp_fund_requirement_master`
--

LOCK TABLES `cp_fund_requirement_master` WRITE;
/*!40000 ALTER TABLE `cp_fund_requirement_master` DISABLE KEYS */;
INSERT INTO `cp_fund_requirement_master` VALUES (1386,NULL,NULL,'String','Why funds are required','Why funds are required?','Why funds are required?','Fund Requirement','1',NULL,NULL,''),(1388,NULL,NULL,'String','If this is for enhance is company utilizing the funds to justify enhancement','If this is for enhance is company utilizing the funds to justify enhancement?','If this is for enhance is company utilizing the funds to justify enhancement?','Fund Requirement','1',NULL,NULL,''),(1389,NULL,NULL,'String','What growth is expected','What growth is expected?','What growth is expected?','Fund Requirement','1',NULL,NULL,''),(1390,NULL,NULL,'String','What is the anchor recommendation','What is the anchor recommendation?','What is the anchor recommendation?','Fund Requirement','1',NULL,NULL,''),(1391,NULL,NULL,'String','Total consolidated limit proposed','Total consolidated limit proposed?','Total consolidated limit proposed?','Fund Requirement','1',NULL,NULL,'');
/*!40000 ALTER TABLE `cp_fund_requirement_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cp_soft_policy_master`
--

LOCK TABLES `cp_soft_policy_master` WRITE;
/*!40000 ALTER TABLE `cp_soft_policy_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `cp_soft_policy_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `credit_norms_master`
--

LOCK TABLES `credit_norms_master` WRITE;
/*!40000 ALTER TABLE `credit_norms_master` DISABLE KEYS */;
INSERT INTO `credit_norms_master` VALUES (263,'2022-12-10 18:03:36','Balaji','Integer','Min Vintage With Anchor','minVintageWithAnchor','1','1','2022-12-10 18:03:36','Balaji','^[0-9]{1,4}$'),(264,'2022-12-10 18:07:24','Balaji','Integer','Business Vintage','businessVintage','2','1','2022-12-10 18:07:24','Balaji','^[0-9]{1,4}$'),(265,'2022-12-10 18:07:55','Balaji','Integer','Group Vintage In Case Of Corp Guarantee','groupVintageInCaseOfCorpGuarantee','3','1','2022-12-10 18:07:55','Balaji','^[0-9]{1,4}$'),(266,'2022-12-10 18:14:27','Balaji','Float','Min Annual Purchases From Anchor','minAnnualPurchasesFromAnchor','4','1','2022-12-10 18:14:27','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(267,'2022-12-10 18:15:59','Balaji','Float','PAT For Last 3 Years','PAT','5','1','2022-12-10 18:15:59','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(327,'2022-12-11 18:13:11','Balaji','Float','PBT For Last 2 Years','PBT','6','1','2022-12-11 18:13:11','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(328,'2022-12-11 18:14:40','Balaji','String','Payment History Confirmed By Anchor','paymentHistory','7','1','2022-12-11 18:14:40','Balaji','^[a-zA-Z ]*$'),(329,'2022-12-11 18:15:48','Balaji','String','Market Check On The Dealer','marketCheck','8','1','2022-12-11 18:15:48','Balaji','^[a-zA-Z ]*$'),(332,'2022-12-11 18:20:18','Balaji','String','Satisfactory report about the dealer from Corporate','satReport','9','1','2022-12-11 18:20:18','Balaji','^[a-zA-Z ]*$'),(333,'2022-12-11 18:22:03','Balaji','Integer','Anchor Dependency','anchorDependency','10','1','2022-12-11 18:22:03','Balaji','^[1-9]?[0-9]{1}$|^100$'),(334,'2022-12-11 18:22:31','Balaji','Integer','Anchor Dependency Per Months','anchorDependencyMonth','11','1','2022-12-11 18:22:31','Balaji','^[0-9]{1,4}$'),(335,'2022-12-11 18:22:57','Balaji','Integer','Statutory Dues Delay','satuDueDelay','12','1','2022-12-11 18:22:57','Balaji','^[0-9]{1,4}$'),(336,'2022-12-11 18:23:16','Balaji','Integer','Positive ATNW','positiveAtnw','13','1','2022-12-11 18:23:16','Balaji','^[0-9]{1,10}$'),(337,'2022-12-11 18:23:54','Balaji','Integer','Sales Growth','salesGrowth','14','1','2022-12-11 18:23:54','Balaji','^[0-9]{1,10}$'),(338,'2022-12-11 18:24:26','Balaji','Integer','Net Worth','netWorth','15','1','2022-12-11 18:24:26','Balaji','^[0-9]{1,10}$'),(339,'2022-12-11 18:24:49','Balaji','Integer','Current Ratio','currentRatio','16','1','2022-12-11 18:24:49','Balaji','^[0-9]{1,10}$'),(340,'2022-12-11 18:25:17','Balaji','Float','Adjusted Leverage','adjustedLeverage','17','1','2022-12-11 18:25:17','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(341,'2022-12-11 18:26:28','Balaji','Integer','Churn','churn','18','1','2022-12-11 18:26:28','Balaji','^[1-9]?[0-9]{1}$|^100$'),(342,'2022-12-11 18:26:47','Balaji','Integer','Cheque Bounces','chequeBounce','19','1','2022-12-11 18:26:47','Balaji','^[0-9]{1,10}$'),(343,'2022-12-11 18:27:11','Balaji','Integer','Penal And Other Charges','penal','20','1','2022-12-11 18:27:11','Balaji','^[0-9]{1,10}$'),(344,'2022-12-11 18:27:46','Balaji','Float','TOL/ATNW','tolAtnw','21','1','2022-12-11 18:27:46','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(345,'2022-12-11 18:31:33','Balaji','Integer','Tranche','tranche','22','1','2022-12-11 18:31:33','Balaji','^[0-9]{1,10}$'),(346,'2022-12-11 18:31:56','Balaji','Float','Minimum Turnover','minTurnOver','23','1','2022-12-11 18:31:56','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(347,'2022-12-11 18:33:16','Balaji','Integer','Bureau Score Commercial','bureauComer','24','1','2022-12-11 18:33:16','Balaji','^[0-9]{1,10}$'),(348,'2022-12-11 18:36:14','Balaji','Integer','Current Overdues: Others','curOverDueComer','25','1','2022-12-11 18:36:14','Balaji','^[0-9]{1,10}$'),(351,'2022-12-11 18:36:42','Balaji','Integer','Count of Current Overdues: Credit Cards','countCurOverCredComer','26','1','2022-12-11 18:36:42','Balaji','^[0-9]{1,10}$'),(361,'2022-12-11 18:37:32','Balaji','Integer','SMA-0/1-30 DPD in the last 12 Months (Commercial)','smaA','27','1','2022-12-11 18:37:32','Balaji','^[0-9]{1,10}$'),(362,'2022-12-11 18:38:14','Balaji','Integer','SMA-1 /30-60: 12 Months','smaB','28','1','2022-12-11 18:38:14','Balaji','^[0-9]{1,10}$'),(363,'2022-12-11 18:38:34','Balaji','Integer','SMA-2 /60-90: 6 Months','smaC','29','1','2022-12-11 18:38:34','Balaji','^[0-9]{1,10}$'),(364,'2022-12-11 18:38:51','Balaji','Integer','SMA-2 /60-90: 12 Months','smaD','30','1','2022-12-11 18:38:51','Balaji','^[0-9]{1,10}$'),(365,'2022-12-11 18:39:23','Balaji','Integer','Sub/DBT/Loss/NPA: >91: 24 Months','subLossNpa','31','1','2022-12-11 18:39:23','Balaji','^[0-9]{1,10}$'),(366,'2022-12-11 18:39:44','Balaji','Integer','Wilful/Suit filed: 48 Months','wilfulSuitComer','32','1','2022-12-11 18:39:44','Balaji','^[0-9]{1,10}$'),(367,'2022-12-11 18:51:59','Balaji','Integer','Current Overdues Amount for Credit Cards','currentOverduesAmountForCreditCards','33','1','2022-12-11 18:51:59','Balaji','^[0-9]{1,10}$'),(368,'2022-12-11 18:40:43','Balaji','Integer','Bureau Score Consumer','bureauConsu','34','1','2022-12-11 18:40:43','Balaji','^[0-9]{1,10}$'),(369,'2022-12-11 18:43:04','Balaji','Float','Current Overdues: Others Consumer','curOverDueConsu','35','1','2022-12-11 18:43:04','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(370,'2022-12-11 18:43:24','Balaji','Float','Current Overdues: Credit Cards Consumer','curOveCredConsu','36','1','2022-12-11 18:43:24','Balaji','^[0-9]*(\\.[0-9]{0,3})?$'),(371,'2022-12-11 18:44:22','Balaji','String','Geo Restriction','geoRestriction','37','1','2022-12-11 18:44:22','Balaji','^[a-zA-Z ]+(,[a-zA-Z ]+)*$'),(382,'2022-12-11 18:45:47','Balaji','Integer','Wilful/suit filed: 36 Month','wilfulSuitConsu','38','1','2022-12-11 18:45:47','Balaji','^[0-9]{1,10}$'),(386,'2022-12-11 18:48:37','Balaji','Integer','Restructured/write offs: 24 Months','restructured','39','1','2022-12-11 18:48:37','Balaji','^[0-9]{1,10}$'),(387,'2022-12-11 18:49:04','Balaji','Integer','EPFO Delay','epfoDelay','40','1','2022-12-11 18:49:04','Balaji','^[0-9]{1,10}$'),(388,'2022-12-11 18:49:26','Balaji','Integer','GST Payment Delay','gstPaymentDelay','41','1','2022-12-11 18:49:26','Balaji','^[0-9]{1,10}$'),(389,'2022-12-11 18:50:21','Balaji','String','Promoter/Gurantor PAN','promGuarPan','42','1','2022-12-11 18:50:21','Balaji','^[a-zA-Z ]*$'),(390,'2022-12-11 18:50:41','Balaji','String','Promoter/Gurantor OVD','promGuarPanOvd','43','1','2022-12-11 18:50:41','Balaji','^[a-zA-Z ]*$'),(391,'2022-12-11 18:51:03','Balaji','String','Business PAN','panBusiness','44','1','2022-12-11 18:51:03','Balaji','^[a-zA-Z ]*$'),(392,'2022-12-11 18:51:59','Balaji','String','Gst Certificate','gstCertificate','45','1','2022-12-11 18:51:59','Balaji','^[a-zA-Z ]*$');
/*!40000 ALTER TABLE `credit_norms_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `credit_policy_master`
--

LOCK TABLES `credit_policy_master` WRITE;
/*!40000 ALTER TABLE `credit_policy_master` DISABLE KEYS */;
INSERT INTO `credit_policy_master` VALUES (2043,'2022-12-15 14:32:08','','Minimum Vintage with anchor(Months):','1','minVintageWithAnchor','Credit Policy','1','2022-12-15 14:32:08','','Integer','^[0-9]{1,4}$'),(2045,'2022-12-15 14:33:23','','Business Vintage(Months):','1','businessVintage','Credit Policy','1','2022-12-15 14:33:23','','Integer','^[0-9]{1,4}$'),(2046,'2022-12-15 14:33:59','','PBT for last 2 years:','1','PBT','Credit Policy','1','2022-12-15 14:33:59','','Integer','^[0-9]{1,10}$'),(2047,'2022-12-15 14:35:53','','Payment History As Confirmed By Anchor:','1','paymentHistory','Credit Policy','1','2022-12-15 14:35:53','','dropString','^[a-zA-Z ]*$'),(2048,'2022-12-15 14:36:20','','Market Check on the Dealar:','1','marketCheck','Credit Policy','1','2022-12-15 14:36:20','','dropString','^[a-zA-Z ]*$'),(2051,'2022-12-15 14:37:34','','Satisfactory report about the dealer from Corporate:','1','satReport','Credit Policy','1','2022-12-15 14:37:34','','dropString','^[a-zA-Z ]*$'),(2052,'2022-12-15 14:38:34','','Anchor dependency:','1','anchorDependency','Credit Policy','1','2022-12-15 14:38:34','','Integer','^[1-9]?[0-9]{1}$|^100$'),(2053,'2022-12-15 14:39:24','','Networth:','1','netWorth','Credit Policy','1','2022-12-15 14:39:24','','Integer','^[0-9]{1,10}$'),(2054,'2022-12-15 14:39:45','','Current Ratio:','1','currentRatio','Credit Policy','1','2022-12-15 14:39:45','','Integer','^[0-9]{1,10}$'),(2055,'2022-12-15 14:40:18','','TOL/ATNW:','1','tolAtnw','Credit Policy','1','2022-12-15 14:40:18','','Integer','^[0-9]{1,10}$'),(2056,'2022-12-15 14:40:44','','Minimum Turnover:','1','minTurnOver','Credit Policy','1','2022-12-15 14:40:44','','Integer','^[0-9]{1,10}$');
/*!40000 ALTER TABLE `credit_policy_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `document_sub_type`
--

LOCK TABLES `document_sub_type` WRITE;
/*!40000 ALTER TABLE `document_sub_type` DISABLE KEYS */;
INSERT INTO `document_sub_type` VALUES (401,'AOA Or MOA','AOAMOA','1','Active',394,'1',NULL),(402,'Company PAN Or TAN','companyPANTAN','2','Active',394,'1',NULL),(403,'Director\'s KYC','DirectorKyc','3','Active',394,'1',NULL),(404,'GST Certificate','gstCertificate','4','Active',394,'1',NULL),(405,'Certificate of Incorporation','certificateOfIncorporation','5','Active',394,'1',NULL),(406,'Specimen Signatures list','specimenSignatureslist','6','Active',394,'1',NULL),(407,'Director\'s list with DIN','directorlistWithDin','7','Active',394,'1',NULL),(408,'ROC Inc 22 Form','rocInc22Form','8','Active',394,'1',NULL),(409,'Utility Bill','utilityBill','9','Active',394,'1',NULL),(410,'Cancelled Cheque','cancelledCheque','10','Active',394,'1',NULL),(411,'KYC of Corporate Guarantor','kycOfCorporateGuarantor','11','Active',394,'1',NULL),(412,'Financial Statements','financialStatements','1','Active',395,'1',NULL),(413,'Borrowing Profile','borrowingProfile','2','Active',395,'1',NULL),(414,'Asset and Liablility Management','assetAndLiablilityManagement','3','Active',395,'1',NULL),(415,'Capital Adequacy Statements','capitalAdequacyStatements','4','Active',395,'1',NULL),(416,'Operational Information','operationalInformation','5','Active',395,'1',NULL),(417,'Share capital raise history till date and shareholding pattern','SCRHTDASP','6','Active',395,'1',NULL),(418,'Monthly DPD analysis','monthlyDpdAnalysis','1','Active',396,'1',NULL),(419,'Portfolio Cuts','portfolioCuts','2','Active',396,'1',NULL),(420,'Static pool Or Vintage curve','staticPoolVintageCurve','3','Active',396,'1',NULL),(421,'Org structure Or BOD Or Information Memorandum Or Management profile','orgStructureBodInformationMemorandumManagementProfile','1','Active',397,'1',NULL),(422,'Product profile','productProfile','2','Active',397,'1',NULL),(423,'Latest rating rationale','latestRatingRationale','3','Active',397,'1',NULL),(424,'Business Plan Or Business Projections','businessPlanBusinessProjections','1','Active',398,'1',NULL),(425,'Others','Others','1','Active',399,'1',NULL),(426,'CIR','CIR','1','Active',400,'1',NULL),(430,'KYC Document','kycDocument','1','Active',428,'1',NULL),(431,'Application Form','applicationForm','2','Active',428,'1',NULL),(432,'Promoter Or Guarantor PAN','promoterGuarantorPAN','3','Active',428,'1',NULL),(433,'Promoter Or Guarantor OVD','promoterGuarantorOVD','4','Active',428,'1',NULL),(434,'Business PAN','businessPAN','5','Active',428,'1',NULL),(435,'GST Certificate','gstCertificate','6','Active',428,'1',NULL),(436,'Registration Certificate','registrationCertificate','7','Active',428,'1',NULL),(437,'Udyam Registration','udyamRegistration','8','Active',428,'1',NULL),(438,'Other address Proof','otherAddressProof','9','Active',428,'1',NULL),(439,'Ownership Pattern','ownershipPattern','10','Active',428,'1',NULL),(440,'Credit Documents','creditDocuments','11','Active',428,'1',NULL),(441,'Audited Financials','auditedFinancials','12','Active',428,'1',NULL),(442,'ITR','ITR','13','Active',428,'1',NULL),(443,'Provisional Financials','provisionalFinancials','14','Active',428,'1',NULL),(444,'GST Returns','gstReturn','15','Active',428,'1',NULL),(445,'Bank Statement','bankStatement','16','Active',428,'1',NULL),(446,'Anchor Ledger','anchorLedger','17','Active',428,'1',NULL),(447,'Sanction letters','sanctionLetters','18','Active',428,'1',NULL),(448,'Debt Profile','debtProfile','19','Active',428,'1',NULL),(449,'Stock Statement','stockStatement','20','Active',428,'1',NULL),(450,'Other Documents','otherDocuments','21','Active',428,'1',NULL),(451,'KYC Document','kycDocument','1','Active',427,'1',NULL),(452,'Application Form','applicationForm','2','Active',427,'1',NULL),(453,'Promoter Or Guarantor PAN','promoterGuarantorPAN','3','Active',427,'1',NULL),(454,'Promoter Or Guarantor OVD','promoterGuarantorOVD','4','Active',427,'1',NULL),(455,'Business PAN','businessPAN','5','Active',427,'1',NULL),(456,'GST Certificate','gstCertificate','6','Active',427,'1',NULL),(457,'Registration Certificate','registrationCertificate','7','Active',427,'1',NULL),(458,'Udyam Registration','udyamRegistration','8','Active',427,'1',NULL),(459,'Other address Proof','otherAddressProof','9','Active',427,'0',NULL),(460,'OwnershipPattern','ownershipPattern','10','Active',427,'0',NULL),(461,'Credit Documents','creditDocuments','11','Active',427,'0',NULL),(462,'Audited Financials','auditedFinancials','12','Active',427,'0',NULL),(463,'ITR','ITR','13','Active',427,'0',NULL),(464,'Provisional Financials','provisionalFinancials','14','Active',427,'0',NULL),(465,'GST Return','gstReturn','15','Active',427,'0',NULL),(466,'Bank Statement','bankStatement','16','Active',427,'0',''),(467,'Anchor Ledger','anchorLedger','17','Active',427,'0',''),(468,'Sanction letters','sanctionLetters','18','Active',427,'0',''),(469,'Debt Profile','debtProfile','19','Active',427,'0',''),(470,'Stock Statement','stockStatement','20','Active',427,'0',''),(471,'Other documents','otherDocuments','21','Active',427,'0',''),(472,'KYC Document','kycDocument','1','Active',429,'1',''),(473,'Application Form','applicationForm','2','Active',429,'1',''),(474,'Promoter Or Guarantor PAN','promoterGuarantorPAN','3','Active',429,'1',''),(475,'Promoter Or Guarantor OVD','promoterGuarantorOVD','4','Active',429,'1',''),(476,'Business PAN','businessPAN','5','Active',429,'1',''),(477,'GST Certificate','gstCertificate','6','Active',429,'1',''),(478,'Registration Certificate','registrationCertificate','7','Active',429,'1',''),(479,'Udyam Registration','udyamRegistration','8','Active',429,'1',''),(480,'Other address Proof','otherAddressProof','9','Active',429,'0',''),(481,'OwnershipPattern','ownershipPattern','10','Active',429,'0',''),(482,'Credit Documents','creditDocuments','11','Active',429,'1',''),(483,'Audited Financials','auditedFinancials','12','Active',429,'0',''),(484,'ITR','ITR','13','Active',429,'0',''),(485,'Provisional Financials','provisionalFinancials','14','Active',429,'0',''),(486,'GST Return','gstReturn','15','Active',429,'1',''),(487,'Bank Statement','bankStatement','16','Active',429,'1',''),(488,'Anchor Ledger','anchorLedger','17','Active',429,'1',''),(489,'Sanction letters','sanctionLetters','18','Active',429,'1',''),(490,'Debt Profile','debtProfile','19','Active',429,'1',''),(491,'Stock Statement','stockStatement','20','Active',429,'1',''),(492,'Other documents','otherDocuments','21','Active',429,'1',''),(1531,'CAM','cam','1','Active',1530,'1',''),(3028,'Partnership Agreement','partnershipAgreement','1','Active',3022,'1','Mandatory for partnership'),(3102,'PAN for all partners','panForAllPartners','2','Active',3022,'1',''),(3103,'Aadhar/DL For All Partners','aadharorDLForAllPartners','3','Active',3022,'1',' '),(3104,'MOA & AOA','MOAAOA','1','Active',3023,'1',''),(3105,'Cerificate of Incorporation','cerificateOfIncorporation','2','Active',3023,'1',''),(3106,'PAN for Auth Sign','panForAuthSign','3','Active',3023,'1',''),(3107,'Aadhar/DL for Auth Sign','aadharOrDLForAuthSign','4','Active',3023,'1',''),(3108,'GST Mandatory','gstMandatory','1','Active',3024,'1',''),(3109,'Entiry Address Proof','entiryAddressProof','2','Active',3024,'0',''),(3110,'URC','URC','3','Active',3024,'0','Not Mandatory for Private Limited'),(3111,'Recommondation Letter ','recommondationLetter ','1','Active',3025,'0',''),(3112,'MOU','MOU ','2','Active',3025,'1',''),(3113,'Triparty Agreement','tripartyAgreement','3','Active',3025,'1',''),(3114,'Accepted Term sheet','acceptedTermSheet','4','Active',3025,'1',''),(3115,'DPN','DPN','5','Active',3025,'1',''),(3116,'Letter of continuity','letterOfContinuity','6','Active',3025,'1',''),(3117,'Deed of Personal Guarantee','deedOfPersonalGuarantee','7','Active',3025,'1',''),(3118,'Undated Cheque/Post Dated Cheque','undatedChequeOrPostDatedCheque','8','Active',3025,'1',''),(3119,'Networth Certificate','networthCertificate','9','Active',3025,'1',''),(3120,'Board resolution','boardResolution','10','Active',3025,'0',''),(3121,'Unattested DOH','unattestedDOH','11','Active',3025,'1',''),(3122,'Cheque Covering Letter','chequeCoveringLetter','12','Active',3025,'1',''),(3123,'Deed of Corporate Guarantee','deedOfCorporateGuarantee','13','Active',3025,'0',''),(3124,'Faclity Agreement','faclityAgreement','14','Active',3025,'1',''),(3125,'Request Letter','requestLetter','1','Active',3026,'1',''),(3126,'DPN','DPN','2','Active',3026,'1',''),(3127,'Letter of continunity','letterOfContinunity','3','Active',3026,'1',''),(3128,'Networth','Networth','4','Active',3026,'1',''),(3129,'Insurance if mentioned in term sheet','insuranceIfMentionedInTermSheet ','5','Active',3026,'0',''),(3130,'BR if applicable','brIfApplicable','6','Active',3026,'0',''),(3131,'UDC','UDC','7','Active',3026,'1',''),(3132,'Cheque Covering Letter','chequeCoveringLetter','8','Active',3026,'1',''),(3133,'Deed of Personal Guarantee','deedOfPersonalGuarantee','9','Active',3026,'1',''),(3134,'Deed of Corporate Guarantee If applicable','deedOfCorporateGuaranteeIfApplicable','10','Active',3026,'0',''),(3135,'Facility Agreement','facilityAgreement','11','Active',3026,'1',''),(3136,'Deed of Hypothycation','deedOfHypothycation','12','Active',3026,'1',''),(3137,'Other condition mentioned in term sheet if any','otherConditionMentionedInTermSheetIfAny','13','Active',3026,'1',''),(3138,'Triparty agreement','tripartyAgreement','1','Active',3027,'1',''),(3139,'BR if applicable','brIfApplicable','2','Active',3027,'0',''),(3140,'Deed of Personal Guarantee','deedOfPersonalGuarantee','3','Active',3027,'0','');
/*!40000 ALTER TABLE `document_sub_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `document_type`
--

LOCK TABLES `document_type` WRITE;
/*!40000 ALTER TABLE `document_type` DISABLE KEYS */;
INSERT INTO `document_type` VALUES (394,'Active','1','20.08.2022','Balaji','KYC','0','kycDocument','1','25.08.2022','MohanRaj'),(395,'Active','1','20.08.2022','Balaji','Financial','1','financialDocument','2','25.08.2022','MohanRaj'),(396,'Active','1','20.08.2022','Balaji','Porfolio','1','porfolioDocument','3','25.08.2022','MohanRaj'),(397,'Active','1','20.08.2022','Balaji','Organization','1','organizationDocument','4','25.08.2022','MohanRaj'),(398,'Active','1','20.08.2022','Balaji','Projections','1','projectionsDocument','5','25.08.2022','MohanRaj'),(399,'Active','1','20.08.2022','Balaji','Others','1','othersDocument','6','25.08.2022','MohanRaj'),(400,'Active','1','20.08.2022','Balaji','CIR','1','cirDocument','7','25.08.2022','MohanRaj'),(427,'Active','2','20.08.2022','Balaji','KYC','0','kycDocument','1','25.08.2022','MohanRaj'),(428,'Active','2','20.08.2022','Balaji','Financial','0','financialDocument','2','25.08.2022','MohanRaj'),(429,'Active','2','20.08.2022','Balaji','Parametric','0','parametricDocument','3','25.08.2022','MohanRaj'),(1530,'Active','3','20.08.2022','Balaji','Credit Assesment Memo','0','camDocument','1','25.08.2022','MohanRaj'),(3022,'Active','4','20.08.2022','Balaji','KYC ','0','kycDocument','1','25.08.2022','MohanRaj'),(3023,'Active','4','20.08.2022','Balaji','LTD Company','0','ltdCompanyDocument','2','25.08.2022','MohanRaj'),(3024,'Active','4','20.08.2022','Balaji','Common Documents','0','commonDocuments','3','25.08.2022','MohanRaj'),(3025,'Active','4','20.08.2022','Balaji','Pre Disbursement Documents','0','preDisbursementDocuments','4','25.08.2022','MohanRaj'),(3026,'Active','4','20.08.2022','Balaji','BIPARTY','0','BIPARTY','5','25.08.2022','MohanRaj'),(3027,'Active','4','20.08.2022','Balaji','TRIPARTY','0','TRIPARTY','6','25.08.2022','MohanRaj');
/*!40000 ALTER TABLE `document_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `fund_req_question`
--

LOCK TABLES `fund_req_question` WRITE;
/*!40000 ALTER TABLE `fund_req_question` DISABLE KEYS */;
INSERT INTO `fund_req_question` VALUES (1386,'String','Why funds are required?','Why funds are required?','1'),(1388,'String','If this is for enhance is company utilizing the funds to justify enhancement?','If this is for enhance is company utilizing the funds to justify enhancement?','1'),(1389,'String','What growth is expected?','What growth is expected?','1'),(1390,'String','What is the anchor recommendation?','What is the anchor recommendation?','1'),(1391,'String','Total consolidated limit proposed?','Total consolidated limit proposed?','1'),(1505,'string','string','string','string'),(1506,'string','string','string','string');
/*!40000 ALTER TABLE `fund_req_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `soft_policy_master_sub_type`
--

LOCK TABLES `soft_policy_master_sub_type` WRITE;
/*!40000 ALTER TABLE `soft_policy_master_sub_type` DISABLE KEYS */;
INSERT INTO `soft_policy_master_sub_type` VALUES (148,NULL,'Balaji','Bureau Score','bureauScore','1',NULL,'',139,'Integer','^[0-9]{1,10}$'),(149,NULL,'Balaji','SMA-0/1-30 In Last 12 Months','smaA','2',NULL,'',139,'Integer','^[0-9]{1,10}$'),(150,NULL,'Balaji','SMA-1/31-60 In Last 12 Months','smaB','3',NULL,'',139,'Integer','^[0-9]{1,10}$'),(151,NULL,'Balaji','SMA-2/61-90 In Last 6 Months','smaC','4',NULL,'',139,'Integer','^[0-9]{1,10}$'),(152,NULL,'Balaji','SMA-2/61-90 In Last 6-12 Months','smaD','5',NULL,'',139,'Integer','^[0-9]{1,10}$'),(153,NULL,'Balaji','Current OD','currentOD','6',NULL,'',139,'Integer','^[0-9]{1,10}$'),(154,NULL,'Balaji','Current OD for credit card','currentODForCreditCard','7',NULL,'',139,'Integer','^[0-9]{1,10}$'),(155,NULL,'Balaji','In last 24 months:Substd,Doubtful,Loss,Restructured/writeOff','SDLR','8',NULL,'',139,'Integer','^[0-9]{1,10}$'),(156,NULL,'Balaji','Wilful or suit filed','wilfulOrSuitFiled','9',NULL,'',139,'Integer','^[0-9]{1,10}$'),(157,NULL,'Balaji','Bureau Score','bureauScore','1',NULL,'',140,'Integer','^[0-9]{1,10}$'),(158,NULL,'Balaji','Current OD','currentOD','2',NULL,'',140,'Integer','^[0-9]{1,10}$'),(159,NULL,'Balaji','Current OD For Credit','currentODForCredit','3',NULL,'',140,'Integer','^[0-9]{1,10}$'),(160,NULL,'Balaji','In last 24 months:Substd,Doubtful,Loss,Restructured/writeOff','SDLR','4',NULL,'',140,'Integer','^[0-9]{1,10}$'),(161,NULL,'Balaji','Wilful or suit filed','wilfulOrSuitFiled','5',NULL,'',140,'Integer','^[0-9]{1,10}$'),(162,NULL,'Balaji','Latest EFO Delay in Months','latestEFODelayInMonths','1',NULL,'',142,'Integer','^[0-9]{1,10}$'),(163,NULL,'Balaji','Latest GST Payment Delay in Months','latestGSTPaymentDelayInMonths','2',NULL,'',142,'Integer','^[0-9]{1,10}$'),(164,NULL,'Balaji','State Should be in the serviceable area','stateShouldBeInTheServiceableArea','1',NULL,'',141,'multiString','^[a-zA-Z ]+(,[a-zA-Z ]+)*$'),(165,NULL,'Balaji','Promoter/Guarantor PAN','promoterGuarantorPAN','1',NULL,'',143,'dropString','^[a-zA-Z ]*$'),(166,NULL,'Balaji','Promoter/Guarantor OVD','promoterGuarantorOVD','2',NULL,'',143,'dropString','^[a-zA-Z ]*$'),(167,NULL,'Balaji','Business PAN','businessPAN','3',NULL,'',143,'dropString','^[a-zA-Z ]*$'),(168,NULL,'Balaji','GST Certificate','gstCertificate','4',NULL,'',143,'dropString','^[a-zA-Z ]*$');
/*!40000 ALTER TABLE `soft_policy_master_sub_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `soft_policy_master_type`
--

LOCK TABLES `soft_policy_master_type` WRITE;
/*!40000 ALTER TABLE `soft_policy_master_type` DISABLE KEYS */;
INSERT INTO `soft_policy_master_type` VALUES (139,'2022-12-09 09:55:38','Balaji','Commercial Bureau','commercialBureau','1',NULL,''),(140,'2022-12-09 09:57:17','Balaji','Consumer Bureau For Primary Promoter','consumerBureauForPrimaryPromoter','2',NULL,''),(141,'2022-12-09 10:56:02','Balaji','Geo Restriction','geoRestriction','3',NULL,''),(142,'2022-12-09 10:55:41','Balaji','Statutory Defaults','statutoryDefaults','4',NULL,''),(143,'2022-12-09 10:55:41','Balaji','KYC Verification Status','kycVerificationStatus','5',NULL,NULL);
/*!40000 ALTER TABLE `soft_policy_master_type` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-31  8:47:52

-- Dump completed on 2022-12-31  8:39:22
