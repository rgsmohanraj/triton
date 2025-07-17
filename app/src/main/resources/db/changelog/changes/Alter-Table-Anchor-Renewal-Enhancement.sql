--liquibase formatted sql
--liquibase update

--changeset Sindhu Manickam:Alter-Table-Renewal-Enhancement

ALTER TABLE  anchor_basic ADD COLUMN business_expiry varchar(255) default '12';
ALTER TABLE application_details_info ADD COLUMN wf_type int not null;
ALTER TABLE application_details_info ADD COLUMN created_at date DEFAULT NULL, ADD COLUMN created_by varchar(255) DEFAULT NULL, ADD COLUMN updated_at date DEFAULT NULL, ADD COLUMN updated_by varchar(255) DEFAULT NULL;
ALTER TABLE application_details_info ADD COLUMN seq_no int(11) DEFAULT 1;