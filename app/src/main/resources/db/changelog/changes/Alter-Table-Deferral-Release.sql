--liquibase formatted sql
--liquibase update

--changeset Ajith Rameshkumar:Alter-table-deferral-flow


ALTER TABLE anchor_basic  ALTER `constitution` DROP DEFAULT;

ALTER TABLE anchor_basic  MODIFY COLUMN `constitution`  VARCHAR(255) NOT NULL;

ALTER TABLE anchor_beneficiary  MODIFY COLUMN `bank_acct_number` VARCHAR(18);

ALTER TABLE document_category RENAME TO dms_document_category;

ALTER TABLE document_list RENAME TO dms_document_list;

ALTER TABLE document_reports RENAME TO dms_document_reports;

ALTER TABLE document_types RENAME TO dms_document_types;

ALTER TABLE dms_document_list ADD (`deferral` int(11) DEFAULT NULL, `type` int(11) DEFAULT NULL,  `deferral_time` bigint(20) DEFAULT NULL);


INSERT INTO dms_document_list VALUES
(20160,'','2023-06-14 12:52:20.048000','Balaji','CERSAI',1,'CERSAI',18,1,NULL,'Balaji',16133,'v1',2,0,30),
(20161,'','2023-06-14 12:54:32.721000','Balaji','Charge Filing',1,'chargeFiling',19,1,NULL,'Balaji',16133,'v1',2,0,30);

UPDATE dms_document_list SET seq_no = 16,deferral = 1,type = 0,deferral_time=0 WHERE id = 16159;
UPDATE dms_document_list SET seq_no = 17,deferral = 1,type = 0,deferral_time=0 WHERE id = 16160;
UPDATE dms_document_list SET seq_no = 20,deferral = 1,type = 1,deferral_time=0 WHERE id = 16166;

UPDATE dms_document_list SET seq_no = 11 WHERE id = 16164;
UPDATE dms_document_list SET seq_no = 12 WHERE id = 16165;

UPDATE dms_document_list SET seq_no = 8 WHERE id = 16046;
UPDATE dms_document_list SET seq_no = 8 WHERE id = 16095;

UPDATE dms_document_list SET seq_no = 9,deferral = 1,type = 0,deferral_time=0 WHERE id = 16116;
UPDATE dms_document_list SET seq_no = 10,deferral = 1,type = 0,deferral_time=0 WHERE id = 16117;
UPDATE dms_document_list SET seq_no = 18,deferral = 1,type = 1,deferral_time=0 WHERE id = 16125;


UPDATE dms_document_list  SET deferral = 0 WHERE deferral  IS NULL;
UPDATE dms_document_list  SET deferral_time = 0 WHERE deferral_time IS NULL;
UPDATE dms_document_list SET type = 0 WHERE type IS NULL;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `credit_policy_filters`;
CREATE TABLE credit_policy_filters (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  formula varchar(500) NOT NULL,
  type varchar(255) NOT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  config_id bigint(20) DEFAULT NULL,
  display_name_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY config_id (config_id),
  CONSTRAINT FKta8wauk3xydv5su0qci7kvpm9 FOREIGN KEY (config_id) REFERENCES credit_policy_config (id),
  CONSTRAINT config_id FOREIGN KEY (config_id) REFERENCES credit_policy_filters (id)
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `deferral_workflow_stage_approval_status` (
  `id` bigint(20) NOT NULL,
  `approved_status` bit(1) DEFAULT NULL,
  `user_info` varchar(255) NOT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `current_stage_approver` varchar(255) DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `seq_no` int(11) NOT NULL,
  `application_status` bigint(20) NOT NULL,
  `updated_time` varchar(255) DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `stage_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `dms_deferral_document_reports` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `initial_time` date DEFAULT NULL,
  `revised_time` date DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint(20) DEFAULT NULL,
  `doc_list_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `doc_completion_date` date DEFAULT NULL,
  `document_id` varchar(255) DEFAULT NULL,
  `rm_name` varchar(255) DEFAULT NULL,
  `new_revised_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpqsqo4ylnk38ca5symd06gyqe` (`doc_list_id`),
  CONSTRAINT `FKpqsqo4ylnk38ca5symd06gyqe` FOREIGN KEY (`doc_list_id`) REFERENCES `dms_document_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `dms_other_document_master` (
  `id` bigint(20) NOT NULL,
  `seq_no` int(11) DEFAULT NULL,
  `app_id` bigint(20) DEFAULT NULL,
  `doc_list_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `new_revised_time` date DEFAULT NULL,
  `initial_time` date DEFAULT NULL,
  `revised_time` date DEFAULT NULL,
  `rm_name` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `deferral_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3cejvkfvsyfkhfuf8yq67yuix` (`app_id`),
  KEY `FKpddyoyr3c88ylf25hl2lydttc` (`doc_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;



CREATE TABLE `dms_other_document_reports` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `app_id` bigint(20) DEFAULT NULL,
  `doc_category_id` bigint(20) DEFAULT NULL,
  `doc_list_id` bigint(20) DEFAULT NULL,
  `doc_type_id` bigint(20) DEFAULT NULL,
  `other_doc_master` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4lvp78o2f4umjq3suii346mtq` (`app_id`),
  KEY `FK103uhx7a26hb0lin1l09ljdk1` (`doc_category_id`),
  KEY `FKip1ytdkwvh0vwov1u6i34oxkd` (`doc_list_id`),
  KEY `FKcf0nfk02awg233psbas7c55pk` (`doc_type_id`),
  KEY `FKjm2xpapu4gnu0vbfh6ky140h0` (`other_doc_master`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE hibernate_sequence (next_val bigint) ENGINE=InnoDB;
INSERT INTO hibernate_sequence VALUES(200000);

INSERT INTO `rbac_group` (`id`, `application_count`, `community`, `group_name`, `is_enable`) VALUES ('12', '2', '1', 'ANCHOR_DEFERRAL_COMMITTEE_LEAD', '1');
INSERT INTO `rbac_group` (`id`, `application_count`, `community`, `group_name`, `is_enable`) VALUES ('13', '2', '1', 'CP_DEFERRAL_COMMITTEE_LEAD', '1');


INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('27', '1', 'A6', '12', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('28', '2', 'CP12', '13', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('29', '1', 'DA1', '8', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('30', '1', 'DA2', '9', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('31', '1', 'DA3', '12','0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('32', '2', 'DCP1', '8', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('33', '2', 'DCP2', '9', '0');
INSERT INTO `workflow_stage` (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('34', '2', 'DCP3', '12', '0');


INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('28', '1', '1', '1', '1', '1', 'DEFERRAL COMMITTEE', '27');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('29', '1', '1', '1', '1', '1', 'DEFERRAL COMMITTEE', '31');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('30', '1', '1', '1', '1', '1', 'DEFERRAL COMMITTEE', '34');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('31', '1', '1', '1', '1', '1', 'OPERATION MAKER', '29');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('32', '1', '1', '1', '1', '1', 'OPERATION MAKER', '32');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('33', '1', '1', '1', '1', '1', 'OPERATION CHECKER', '30');
INSERT INTO `rbac_permission` (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES ('34', '1', '1', '1', '1', '1', 'OPERATION CHECKER', '33');


INSERT INTO `email_notification_tamplate` (`id`, `action`, `content`, `stage`, `subject`) VALUES ('9', '-2', 'ETA for submitting the Deferral Document has exceeded. The Disbursements are now on Hold.', 'T-', 'Triton | Deferral Reminder');
INSERT INTO `email_notification_tamplate` (`id`, `action`, `content`, `stage`, `subject`) VALUES ('10', '2', 'The ETA dates for Deferral Document submission are approaching.', 'T+', 'Triton | Deferral Reminder');
INSERT INTO `email_notification_tamplate` (`id`, `action`, `content`, `stage`, `subject`) VALUES ('11', '0', 'The ETA for submitting the Deferral Document is Today. Failure to comply with this submission shall further halt upcoming disbursements.', 'T0', 'Triton | Deferral Reminder');

INSERT INTO `credit_policy_filters` VALUES (1,NULL,NULL,'value>=12','1',NULL,NULL,1,1),(2,NULL,NULL,'','0',NULL,NULL,1,2),(3,NULL,NULL,'','0',NULL,NULL,1,3),(4,NULL,NULL,'','0',NULL,NULL,1,4),(5,NULL,NULL,'value>=0','1',NULL,NULL,1,5),(6,NULL,NULL,'value>=0','1',NULL,NULL,1,6),(7,NULL,NULL,'','0',NULL,NULL,1,7),(8,NULL,NULL,'','0',NULL,NULL,1,8),(9,NULL,NULL,'','0',NULL,NULL,1,9),(10,NULL,NULL,'Validate','1',NULL,NULL,1,10),(11,NULL,NULL,'value>=60','1',NULL,NULL,1,11),(12,NULL,NULL,'value<=25000','1',NULL,NULL,1,12),(13,NULL,NULL,'value<=2500','1',NULL,NULL,1,13),(14,NULL,NULL,'value<=4','1',NULL,NULL,1,14),(15,NULL,NULL,'value<=1','1',NULL,NULL,1,15),(16,NULL,NULL,'value<=2','1',NULL,NULL,1,16),(17,NULL,NULL,'value == 0','1',NULL,NULL,1,17),(18,NULL,NULL,'value == 0','1',NULL,NULL,1,18),(19,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,1,19),(20,NULL,NULL,'value<=25000','1',NULL,NULL,1,20),(21,NULL,NULL,'value<=2500','1',NULL,NULL,1,21),(22,NULL,NULL,'value == 0','1',NULL,NULL,1,22),(23,NULL,NULL,'value == 0','1',NULL,NULL,1,23),(24,NULL,NULL,'value<=1','1',NULL,NULL,1,24),(25,NULL,NULL,'value<=3','1',NULL,NULL,1,25),(26,NULL,NULL,'Validate','1',NULL,NULL,1,26),(27,NULL,NULL,'Validate','1',NULL,NULL,1,27),(28,NULL,NULL,'value>=12','1',NULL,NULL,2,1),(29,NULL,NULL,'','0',NULL,NULL,2,2),(30,NULL,NULL,'','0',NULL,NULL,2,3),(31,NULL,NULL,'','0',NULL,NULL,2,4),(32,NULL,NULL,'','0',NULL,NULL,2,5),(33,NULL,NULL,'','0',NULL,NULL,2,6),(34,NULL,NULL,'','0',NULL,NULL,2,7),(35,NULL,NULL,'','0',NULL,NULL,2,8),(36,NULL,NULL,'','0',NULL,NULL,2,9),(37,NULL,NULL,'Validate','1',NULL,NULL,2,10),(38,NULL,NULL,'value>=60','1',NULL,NULL,2,11),(39,NULL,NULL,'value<=25000','1',NULL,NULL,2,12),(40,NULL,NULL,'value<=2500','1',NULL,NULL,2,13),(41,NULL,NULL,'value<=4','1',NULL,NULL,2,14),(42,NULL,NULL,'value<=1','1',NULL,NULL,2,15),(43,NULL,NULL,'value<=2','1',NULL,NULL,2,16),(44,NULL,NULL,'value == 0','1',NULL,NULL,2,17),(45,NULL,NULL,'value == 0','1',NULL,NULL,2,18),(46,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,2,19),(47,NULL,NULL,'value<=25000','1',NULL,NULL,2,20),(48,NULL,NULL,'value<=2500','1',NULL,NULL,2,21),(49,NULL,NULL,'value == 0','1',NULL,NULL,2,22),(50,NULL,NULL,'value == 0','1',NULL,NULL,2,23),(51,NULL,NULL,'value<=1','1',NULL,NULL,2,24),(52,NULL,NULL,'value<=3','1',NULL,NULL,2,25),(53,NULL,NULL,'Validate','1',NULL,NULL,2,26),(54,NULL,NULL,'Validate','1',NULL,NULL,2,27),(55,NULL,NULL,'value>=12','1',NULL,NULL,3,1),(56,NULL,NULL,'','0',NULL,NULL,3,2),(57,NULL,NULL,'','0',NULL,NULL,3,3),(58,NULL,NULL,'','0',NULL,NULL,3,4),(59,NULL,NULL,'','0',NULL,NULL,3,5),(60,NULL,NULL,'','0',NULL,NULL,3,6),(61,NULL,NULL,'','0',NULL,NULL,3,7),(62,NULL,NULL,'','0',NULL,NULL,3,8),(63,NULL,NULL,'','0',NULL,NULL,3,9),(64,NULL,NULL,'','0',NULL,NULL,3,10),(65,NULL,NULL,'','0',NULL,NULL,3,11),(66,NULL,NULL,'value<=25000','1',NULL,NULL,3,12),(67,NULL,NULL,'value<=2500','1',NULL,NULL,3,13),(68,NULL,NULL,'value<=4','1',NULL,NULL,3,14),(69,NULL,NULL,'value<=1','1',NULL,NULL,3,15),(70,NULL,NULL,'value<=2','1',NULL,NULL,3,16),(71,NULL,NULL,'value == 0','1',NULL,NULL,3,17),(72,NULL,NULL,'value == 0','1',NULL,NULL,3,18),(73,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,3,19),(74,NULL,NULL,'value<=25000','1',NULL,NULL,3,20),(75,NULL,NULL,'value<=2500','1',NULL,NULL,3,21),(76,NULL,NULL,'value == 0','1',NULL,NULL,3,22),(77,NULL,NULL,'value == 0','1',NULL,NULL,3,23),(78,NULL,NULL,'value<=1','1',NULL,NULL,3,24),(79,NULL,NULL,'value<=3','1',NULL,NULL,3,25),(80,NULL,NULL,'Validate','1',NULL,NULL,3,26),(81,NULL,NULL,'Validate','1',NULL,NULL,3,27),(82,NULL,NULL,'value>=12','1',NULL,NULL,4,1),(83,NULL,NULL,'','0',NULL,NULL,4,2),(84,NULL,NULL,'','0',NULL,NULL,4,3),(85,NULL,NULL,'','0',NULL,NULL,4,4),(86,NULL,NULL,'value>=0','1',NULL,NULL,4,5),(87,NULL,NULL,'value>=0','1',NULL,NULL,4,6),(88,NULL,NULL,'','0',NULL,NULL,4,7),(89,NULL,NULL,'','0',NULL,NULL,4,8),(90,NULL,NULL,'','0',NULL,NULL,4,9),(91,NULL,NULL,'Validate','1',NULL,NULL,4,10),(92,NULL,NULL,'value>=60','1',NULL,NULL,4,11),(93,NULL,NULL,'value<=25000','1',NULL,NULL,4,12),(94,NULL,NULL,'value<=2500','1',NULL,NULL,4,13),(95,NULL,NULL,'value<=4','1',NULL,NULL,4,14),(96,NULL,NULL,'value<=1','1',NULL,NULL,4,15),(97,NULL,NULL,'value<=2','1',NULL,NULL,4,16),(98,NULL,NULL,'value == 0','1',NULL,NULL,4,17),(99,NULL,NULL,'value == 0','1',NULL,NULL,4,18),(100,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,4,19),(101,NULL,NULL,'value<=25000','1',NULL,NULL,4,20),(102,NULL,NULL,'value<=2500','1',NULL,NULL,4,21),(103,NULL,NULL,'value == 0','1',NULL,NULL,4,22),(104,NULL,NULL,'value == 0','1',NULL,NULL,4,23),(105,NULL,NULL,'value<=1','1',NULL,NULL,4,24),(106,NULL,NULL,'value<=3','1',NULL,NULL,4,25),(107,NULL,NULL,'Validate','1',NULL,NULL,4,26),(108,NULL,NULL,'Validate','1',NULL,NULL,4,27),(109,NULL,NULL,'value>=12','1',NULL,NULL,5,1),(110,NULL,NULL,'','0',NULL,NULL,5,2),(111,NULL,NULL,'','0',NULL,NULL,5,3),(112,NULL,NULL,'','0',NULL,NULL,5,4),(113,NULL,NULL,'','0',NULL,NULL,5,5),(114,NULL,NULL,'','0',NULL,NULL,5,6),(115,NULL,NULL,'','0',NULL,NULL,5,7),(116,NULL,NULL,'','0',NULL,NULL,5,8),(117,NULL,NULL,'','0',NULL,NULL,5,9),(118,NULL,NULL,'Validate','1',NULL,NULL,5,10),(119,NULL,NULL,'value>=60','1',NULL,NULL,5,11),(120,NULL,NULL,'value<=25000','1',NULL,NULL,5,12),(121,NULL,NULL,'value<=2500','1',NULL,NULL,5,13),(122,NULL,NULL,'value<=4','1',NULL,NULL,5,14),(123,NULL,NULL,'value<=1','1',NULL,NULL,5,15),(124,NULL,NULL,'value<=2','1',NULL,NULL,5,16),(125,NULL,NULL,'value == 0','1',NULL,NULL,5,17),(126,NULL,NULL,'value == 0','1',NULL,NULL,5,18),(127,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,5,19),(128,NULL,NULL,'value<=25000','1',NULL,NULL,5,20),(129,NULL,NULL,'value<=2500','1',NULL,NULL,5,21),(130,NULL,NULL,'value == 0','1',NULL,NULL,5,22),(131,NULL,NULL,'value == 0','1',NULL,NULL,5,23),(132,NULL,NULL,'value<=1','1',NULL,NULL,5,24),(133,NULL,NULL,'value<=3','1',NULL,NULL,5,25),(134,NULL,NULL,'Validate','1',NULL,NULL,5,26),(135,NULL,NULL,'Validate','1',NULL,NULL,5,27),(136,NULL,NULL,'value>=12','1',NULL,NULL,6,1),(137,NULL,NULL,'','0',NULL,NULL,6,2),(138,NULL,NULL,'','0',NULL,NULL,6,3),(139,NULL,NULL,'','0',NULL,NULL,6,4),(140,NULL,NULL,'','0',NULL,NULL,6,5),(141,NULL,NULL,'','0',NULL,NULL,6,6),(142,NULL,NULL,'','0',NULL,NULL,6,7),(143,NULL,NULL,'','0',NULL,NULL,6,8),(144,NULL,NULL,'','0',NULL,NULL,6,9),(145,NULL,NULL,'','0',NULL,NULL,6,10),(146,NULL,NULL,'','0',NULL,NULL,6,11),(147,NULL,NULL,'value<=25000','1',NULL,NULL,6,12),(148,NULL,NULL,'value<=2500','1',NULL,NULL,6,13),(149,NULL,NULL,'value<=4','1',NULL,NULL,6,14),(150,NULL,NULL,'value<=1','1',NULL,NULL,6,15),(151,NULL,NULL,'value<=2','1',NULL,NULL,6,16),(152,NULL,NULL,'value == 0','1',NULL,NULL,6,17),(153,NULL,NULL,'value == 0','1',NULL,NULL,6,18),(154,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,6,19),(155,NULL,NULL,'value<=25000','1',NULL,NULL,6,20),(156,NULL,NULL,'value<=2500','1',NULL,NULL,6,21),(157,NULL,NULL,'value == 0','1',NULL,NULL,6,22),(158,NULL,NULL,'value == 0','1',NULL,NULL,6,23),(159,NULL,NULL,'value<=1','1',NULL,NULL,6,24),(160,NULL,NULL,'value<=3','1',NULL,NULL,6,25),(161,NULL,NULL,'Validate','1',NULL,NULL,6,26),(162,NULL,NULL,'Validate','1',NULL,NULL,6,27),(163,NULL,NULL,'value>=36','1',NULL,NULL,7,1),(164,NULL,NULL,'value>=50000000','1',NULL,NULL,7,2),(165,NULL,NULL,'','0',NULL,NULL,7,3),(166,NULL,NULL,'Validate','1',NULL,NULL,7,4),(167,NULL,NULL,'','0',NULL,NULL,7,5),(168,NULL,NULL,'value>=0','1',NULL,NULL,7,6),(169,NULL,NULL,'value>=1.1','1',NULL,NULL,7,7),(170,NULL,NULL,'Validate','1',NULL,NULL,7,8),(171,NULL,NULL,'Validate','1',NULL,NULL,7,9),(172,NULL,NULL,'Validate','1',NULL,NULL,7,10),(173,NULL,NULL,'value>=75','1',NULL,NULL,7,11),(174,NULL,NULL,'value<=25000','1',NULL,NULL,7,12),(175,NULL,NULL,'value<=2500','1',NULL,NULL,7,13),(176,NULL,NULL,'value<=3','1',NULL,NULL,7,14),(177,NULL,NULL,'value<=0','1',NULL,NULL,7,15),(178,NULL,NULL,'value<=1','1',NULL,NULL,7,16),(179,NULL,NULL,'value == 0','1',NULL,NULL,7,17),(180,NULL,NULL,'value == 0','1',NULL,NULL,7,18),(181,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,7,19),(182,NULL,NULL,'value<=25000','1',NULL,NULL,7,20),(183,NULL,NULL,'value<=2500','1',NULL,NULL,7,21),(184,NULL,NULL,'value == 0','1',NULL,NULL,7,22),(185,NULL,NULL,'value ==0','1',NULL,NULL,7,23),(186,NULL,NULL,'value<=1','1',NULL,NULL,7,24),(187,NULL,NULL,'value<=3','1',NULL,NULL,7,25),(188,NULL,NULL,'Validate','1',NULL,NULL,7,26),(189,NULL,NULL,'Validate','1',NULL,NULL,7,27),(190,NULL,NULL,'value>=36','1',NULL,NULL,8,1),(191,NULL,NULL,'value>=50000000','1',NULL,NULL,8,2),(192,NULL,NULL,'','0',NULL,NULL,8,3),(193,NULL,NULL,'Validate','1',NULL,NULL,8,4),(194,NULL,NULL,'','0',NULL,NULL,8,5),(195,NULL,NULL,'value>=0','1',NULL,NULL,8,6),(196,NULL,NULL,'value>=1.2','1',NULL,NULL,8,7),(197,NULL,NULL,'Validate','1',NULL,NULL,8,8),(198,NULL,NULL,'Validate','1',NULL,NULL,8,9),(199,NULL,NULL,'Validate','1',NULL,NULL,8,10),(200,NULL,NULL,'value>=75','1',NULL,NULL,8,11),(201,NULL,NULL,'value<=25000','1',NULL,NULL,8,12),(202,NULL,NULL,'value<=2500','1',NULL,NULL,8,13),(203,NULL,NULL,'value<=3','1',NULL,NULL,8,14),(204,NULL,NULL,'value<=0','1',NULL,NULL,8,15),(205,NULL,NULL,'value<=1','1',NULL,NULL,8,16),(206,NULL,NULL,'value == 0','1',NULL,NULL,8,17),(207,NULL,NULL,'value == 0','1',NULL,NULL,8,18),(208,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,8,19),(209,NULL,NULL,'value<=25000','1',NULL,NULL,8,20),(210,NULL,NULL,'value<=2500','1',NULL,NULL,8,21),(211,NULL,NULL,'value == 0','1',NULL,NULL,8,22),(212,NULL,NULL,'value == 0','1',NULL,NULL,8,23),(213,NULL,NULL,'value<=1','1',NULL,NULL,8,24),(214,NULL,NULL,'value<=3','1',NULL,NULL,8,25),(215,NULL,NULL,'Validate','1',NULL,NULL,8,26),(216,NULL,NULL,'Validate','1',NULL,NULL,8,27),(217,NULL,NULL,'value>=36','1',NULL,NULL,9,1),(218,NULL,NULL,'value>=10000000','1',NULL,NULL,9,2),(219,NULL,NULL,'value<=30','1',NULL,NULL,9,3),(220,NULL,NULL,'','0',NULL,NULL,9,4),(221,NULL,NULL,'','0',NULL,NULL,9,5),(222,NULL,NULL,'','0',NULL,NULL,9,6),(223,NULL,NULL,'','0',NULL,NULL,9,7),(224,NULL,NULL,'','0',NULL,NULL,9,8),(225,NULL,NULL,'','0',NULL,NULL,9,9),(226,NULL,NULL,'Validate','1',NULL,NULL,9,10),(227,NULL,NULL,'value>=75','1',NULL,NULL,9,11),(228,NULL,NULL,'value<=25000','1',NULL,NULL,9,12),(229,NULL,NULL,'value<=2500','1',NULL,NULL,9,13),(230,NULL,NULL,'value<=3','1',NULL,NULL,9,14),(231,NULL,NULL,'value<=0','1',NULL,NULL,9,15),(232,NULL,NULL,'value<=1','1',NULL,NULL,9,16),(233,NULL,NULL,'value == 0','1',NULL,NULL,9,17),(234,NULL,NULL,'value == 0','1',NULL,NULL,9,18),(235,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,9,19),(236,NULL,NULL,'value<=25000','1',NULL,NULL,9,20),(237,NULL,NULL,'value<=2500','1',NULL,NULL,9,21),(238,NULL,NULL,'value == 0','1',NULL,NULL,9,22),(239,NULL,NULL,'value == 0','1',NULL,NULL,9,23),(240,NULL,NULL,'value<=1','1',NULL,NULL,9,24),(241,NULL,NULL,'value<=3','1',NULL,NULL,9,25),(242,NULL,NULL,'Validate','1',NULL,NULL,9,26),(243,NULL,NULL,'Validate','1',NULL,NULL,9,27),(244,NULL,NULL,'value>=36','1',NULL,NULL,10,1),(245,NULL,NULL,'value>=10000000','1',NULL,NULL,10,2),(246,NULL,NULL,'value<=30','1',NULL,NULL,10,3),(247,NULL,NULL,'','0',NULL,NULL,10,4),(248,NULL,NULL,'','0',NULL,NULL,10,5),(249,NULL,NULL,'','0',NULL,NULL,10,6),(250,NULL,NULL,'','0',NULL,NULL,10,7),(251,NULL,NULL,'','0',NULL,NULL,10,8),(252,NULL,NULL,'','0',NULL,NULL,10,9),(253,NULL,NULL,'Validate','1',NULL,NULL,10,10),(254,NULL,NULL,'value>=75','1',NULL,NULL,10,11),(255,NULL,NULL,'value<=25000','1',NULL,NULL,10,12),(256,NULL,NULL,'value<=2500','1',NULL,NULL,10,13),(257,NULL,NULL,'value<=3','1',NULL,NULL,10,14),(258,NULL,NULL,'value<=0','1',NULL,NULL,10,15),(259,NULL,NULL,'value<=1','1',NULL,NULL,10,16),(260,NULL,NULL,'value == 0','1',NULL,NULL,10,17),(261,NULL,NULL,'value == 0','1',NULL,NULL,10,18),(262,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,10,19),(263,NULL,NULL,'value<=25000','1',NULL,NULL,10,20),(264,NULL,NULL,'value<=2500','1',NULL,NULL,10,21),(265,NULL,NULL,'value == 0','1',NULL,NULL,10,22),(266,NULL,NULL,'value == 0','1',NULL,NULL,10,23),(267,NULL,NULL,'value<=1','1',NULL,NULL,10,24),(268,NULL,NULL,'value<=3','1',NULL,NULL,10,25),(269,NULL,NULL,'Validate','1',NULL,NULL,10,26),(270,NULL,NULL,'Validate','1',NULL,NULL,10,27),(271,NULL,NULL,'value>=36','1',NULL,NULL,11,1),(272,NULL,NULL,'value>=50000000','1',NULL,NULL,11,2),(273,NULL,NULL,'','0',NULL,NULL,11,3),(274,NULL,NULL,'Validate','1',NULL,NULL,11,4),(275,NULL,NULL,'','0',NULL,NULL,11,5),(276,NULL,NULL,'value>=0','1',NULL,NULL,11,6),(277,NULL,NULL,'value>=1.1','1',NULL,NULL,11,7),(278,NULL,NULL,'Validate','1',NULL,NULL,11,8),(279,NULL,NULL,'Validate','1',NULL,NULL,11,9),(280,NULL,NULL,'Validate','1',NULL,NULL,11,10),(281,NULL,NULL,'value>=75','1',NULL,NULL,11,11),(282,NULL,NULL,'value<=25000','1',NULL,NULL,11,12),(283,NULL,NULL,'value<=2500','1',NULL,NULL,11,13),(284,NULL,NULL,'value<=3','1',NULL,NULL,11,14),(285,NULL,NULL,'value<=0','1',NULL,NULL,11,15),(286,NULL,NULL,'value<=1','1',NULL,NULL,11,16),(287,NULL,NULL,'value == 0','1',NULL,NULL,11,17),(288,NULL,NULL,'value == 0','1',NULL,NULL,11,18),(289,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,11,19),(290,NULL,NULL,'value<=25000','1',NULL,NULL,11,20),(291,NULL,NULL,'value<=2500','1',NULL,NULL,11,21),(292,NULL,NULL,'value == 0','1',NULL,NULL,11,22),(293,NULL,NULL,'value == 0','1',NULL,NULL,11,23),(294,NULL,NULL,'value<=1','1',NULL,NULL,11,24),(295,NULL,NULL,'value<=3','1',NULL,NULL,11,25),(296,NULL,NULL,'Validate','1',NULL,NULL,11,26),(297,NULL,NULL,'Validate','1',NULL,NULL,11,27),(298,NULL,NULL,'value>=36','1',NULL,NULL,12,1),(299,NULL,NULL,'value>=50000000','1',NULL,NULL,12,2),(300,NULL,NULL,'','0',NULL,NULL,12,3),(301,NULL,NULL,'Validate','1',NULL,NULL,12,4),(302,NULL,NULL,'','0',NULL,NULL,12,5),(303,NULL,NULL,'value>=0','1',NULL,NULL,12,6),(304,NULL,NULL,'value>=1.1','1',NULL,NULL,12,7),(305,NULL,NULL,'Validate','1',NULL,NULL,12,8),(306,NULL,NULL,'Validate','1',NULL,NULL,12,9),(307,NULL,NULL,'Validate','1',NULL,NULL,12,10),(308,NULL,NULL,'value>=75','1',NULL,NULL,12,11),(309,NULL,NULL,'value<=25000','1',NULL,NULL,12,12),(310,NULL,NULL,'value<=2500','1',NULL,NULL,12,13),(311,NULL,NULL,'value<=3','1',NULL,NULL,12,14),(312,NULL,NULL,'value<=0','1',NULL,NULL,12,15),(313,NULL,NULL,'value<=1','1',NULL,NULL,12,16),(314,NULL,NULL,'value == 0','1',NULL,NULL,12,17),(315,NULL,NULL,'value == 0','1',NULL,NULL,12,18),(316,NULL,NULL,'(-1<=value && value<=99) || (value>=650)','1',NULL,NULL,12,19),(317,NULL,NULL,'value<=25000','1',NULL,NULL,12,20),(318,NULL,NULL,'value<=2500','1',NULL,NULL,12,21),(319,NULL,NULL,'value == 0','1',NULL,NULL,12,22),(320,NULL,NULL,'value == 0','1',NULL,NULL,12,23),(321,NULL,NULL,'value<=1','1',NULL,NULL,12,24),(322,NULL,NULL,'value<=3','1',NULL,NULL,12,25),(323,NULL,NULL,'Validate','1',NULL,NULL,12,26),(324,NULL,NULL,'Validate','1',NULL,NULL,12,27);