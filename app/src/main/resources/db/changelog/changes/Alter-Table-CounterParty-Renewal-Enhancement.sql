--liquibase formatted sql
--liquibase update

--changeset Sindhu Manickam:Alter-Table-Renewal-Enhancement

ALTER TABLE customer_info RENAME COLUMN status TO dedupe_status;
ALTER TABLE customer_info ADD COLUMN business_expiry date DEFAULT NULL, ADD COLUMN status bit(1) DEFAULT NULL;
ALTER TABLE workflow_stage_approval_status MODIFY remarks TEXT;
ALTER TABLE cp_proposed_products ADD COLUMN credit_policy bit(1) DEFAULT null;
ALTER TABLE cp_basic_details ADD COLUMN counter_party_type varchar(20) DEFAULT 'New';
ALTER table cp_term_sheet ADD COLUMN expiry_date date DEFAULT NULL;
ALTER table anchor_program ADD COLUMN expiry_date date DEFAULT NULL;
ALTER TABLE cp_proposed_products MODIFY COLUMN type varchar(255) NULL;
ALTER TABLE anchor_program ADD COLUMN product_expiry int(12) DEFAULT 12;
INSERT INTO workflow_stage (`id`, `client_type`, `stage`, `group_id`, `approver_permission`) VALUES ('35', '2', 'CP0', '6', false);
INSERT INTO rbac_permission (`id`, `approve`, `view`, `reject`, `is_return`, `is_submit`, `role`, `stage_id`) VALUES (35, true, true,false, false, true, 'CPA', 35);
ALTER TABLE anchor_beneficiary MODIFY COLUMN bank_acct_number VARCHAR(25);