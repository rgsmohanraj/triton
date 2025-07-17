--liquibase formatted sql
--changeset Ajith Rameshkumar:create_table

CREATE TABLE `BANK_MASTER` (
  `BM_CD` int(11) DEFAULT NULL,
  `MAKERID` varchar(10) NOT NULL,
  `MAKEDATE` date NOT NULL,
  `STATUS` varchar(1) NOT NULL,
  `AUTHORID` varchar(8) DEFAULT NULL,
  `AUTHDATE` date DEFAULT NULL,
  `BM_SRNO` int(11) NOT NULL,
  `BM_LAST_BRANCH_NO` int(11) DEFAULT NULL,
  `BM_VALID_FROM` date DEFAULT NULL,
  `BM_VALID_TO` date DEFAULT NULL,
  `BM_CITY_CD` varchar(32) DEFAULT NULL,
  `BM_NAME` varchar(500) DEFAULT NULL,
  `BM_NAME2` varchar(500) DEFAULT NULL,
  `BM_NAME3` varchar(500) DEFAULT NULL,
  `LEGACYCODE1` varchar(4000) DEFAULT NULL,
  `COMPANYID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `Bank_Branch_Master` (
  `BBM_BANK_CD` int(11) DEFAULT NULL,
  `BBM_CD` varchar(80) DEFAULT NULL,
  `MAKERID` varchar(8) NOT NULL,
  `MAKEDATE` date NOT NULL,
  `STATUS` varchar(1) NOT NULL,
  `AUTHORID` varchar(8) DEFAULT NULL,
  `AUTHDATE` date DEFAULT NULL,
  `BBM_DESC` varchar(1000) DEFAULT NULL,
  `BBM_PIN` int(11) DEFAULT NULL,
  `BBM_SR_NO` int(11) DEFAULT NULL,
  `BBM_SRNO` int(11) NOT NULL,
  `CG_LOCATIONCODE` int(11) DEFAULT NULL,
  `BBM_VALID_FROM` date DEFAULT NULL,
  `BBM_VALID_TO` date DEFAULT NULL,
  `BBM_ADDRESS1` varchar(200) DEFAULT NULL,
  `BBM_ADDRESS2` varchar(200) DEFAULT NULL,
  `BBM_ADDRESS3` varchar(200) DEFAULT NULL,
  `BBM_ADDRESS4` varchar(200) DEFAULT NULL,
  `BBM_ZIP` varchar(40) DEFAULT NULL,
  `BBM_TEL1` varchar(80) DEFAULT NULL,
  `BBM_TEL2` varchar(80) DEFAULT NULL,
  `BBM_TEL3` varchar(80) DEFAULT NULL,
  `BBM_CITY_CD` varchar(400) DEFAULT NULL,
  `BBM_CONT_PERSON` varchar(160) DEFAULT NULL,
  `BBM_SPONSOR_FLAG` varchar(1) DEFAULT NULL,
  `BBM_DESCRIPTION2` varchar(1000) DEFAULT NULL,
  `BBM_DESCRIPTION3` varchar(1000) DEFAULT NULL,
  `LEGACYCODE1` varchar(1000) DEFAULT NULL,
  `LEGACYCODE2` varchar(1000) DEFAULT NULL,
  `LEGACYCODE3` varchar(1000) DEFAULT NULL,
  `CG_COUNTRY` int(11) DEFAULT NULL,
  `CG_STATE` int(11) DEFAULT NULL,
  `COMPANYID` int(11) DEFAULT NULL,
  `CG_DISTRICTCODE` int(11) DEFAULT NULL,
  `IFSC_CODE` varchar(20) DEFAULT NULL,
  `MICRCODE` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_COMP_MASTER` (
  `LSTCM_CIF_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LSTCM_COMP_NAME` varchar(200) DEFAULT NULL,
  `LSTCM_COMP_SHORT_NAME` varchar(200) DEFAULT NULL,
  `LSTCM_COMP_NAME_LOCAL` varchar(200) DEFAULT NULL,
  `LSTCM_SHORT_NAME_LOCAL` varchar(200) DEFAULT NULL,
  `LSTCM_COUNTRY` varchar(200) DEFAULT NULL,
  `LSTCM_LEGAL_FORM` varchar(200) DEFAULT NULL,
  `LSTCM_KYC` varchar(200) DEFAULT NULL,
  `LSTCM_ADD_SEC_AVAILABLE` varchar(200) DEFAULT NULL,
  `LSTCM_BLNC_SHEET_DATE` varchar(200) DEFAULT NULL,
  `LSTCM_PRFT_AND_LOSS_DT` varchar(200) DEFAULT NULL,
  `LSTCM_REG_DT` varchar(200) DEFAULT NULL,
  `LSTCM_IE_CODE` varchar(200) DEFAULT NULL,
  `LSTCM_COMP_REG_NO` varchar(200) DEFAULT NULL,
  `LSTCM_EXP_TYPE` varchar(200) DEFAULT NULL,
  `LSTCM_BLACK_LISTED` varchar(200) DEFAULT NULL,
  `LSTCM_GROUP` varchar(200) DEFAULT NULL,
  `LSTCM_NOG` varchar(200) DEFAULT NULL,
  `LSTCM_MAIN_SHARE_HOLDR` varchar(200) DEFAULT NULL,
  `LSTCM_REL_SHARE_HOLDR` varchar(200) DEFAULT NULL,
  `LSTCM_INDUSTRY` varchar(200) DEFAULT NULL,
  `LSTCM_INDUS_SUB_TYP` varchar(200) DEFAULT NULL,
  `LSTCM_MAJOR_SUB_INDUS` varchar(200) DEFAULT NULL,
  `LSTCM_LOCATION` varchar(200) DEFAULT NULL,
  `LSCM_STG_COMP_ID` varchar(200) DEFAULT NULL,
  `LSCM_INTF_STATUS` varchar(200) DEFAULT NULL,
  `LSCM_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSCM_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSCM_COMP_CODE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LSTCM_CIF_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=511011 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_ACCT_INTEREST_DTL` (
  `LSAID_AID` int(11) NOT NULL AUTO_INCREMENT,
  `LSAID_RATE_TYPE` varchar(200) DEFAULT NULL,
  `LSAID_CALCULATION_METHOD` varchar(200) DEFAULT NULL,
  `LSAID_CALCULATION_FOR` varchar(200) DEFAULT NULL,
  `LSAID_PENALITY_SPREAD` varchar(200) DEFAULT NULL,
  `LSAID_FROM` varchar(200) DEFAULT NULL,
  `LSAID_TO` varchar(200) DEFAULT NULL,
  `LSAID_INTEREST_PER` varchar(200) DEFAULT NULL,
  `LSAID_FLOATING_REFERNCE` varchar(200) DEFAULT NULL,
  `LSAID_SPREAD` varchar(200) DEFAULT NULL,
  `LSAID_ACTUAL_FLOATING_RATE` varchar(200) DEFAULT NULL,
  `LSAID_INTF_STATUS` varchar(1) DEFAULT NULL,
  `LSAID_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSAID_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSAID_ACCT_CODE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LSAID_AID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `LOT_STG_ACCT_SETUP_MASTER` (
  `LSASM_ACCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LSASM_CORPORATE_NAME` varchar(200) DEFAULT NULL,
  `LSASM_CORPORATE_PROG_NAME` varchar(200) DEFAULT NULL,
  `LSASM_SCF_PROG_NAME` varchar(200) DEFAULT NULL,
  `LSASM_SCF_PROG_CODE` varchar(200) DEFAULT NULL,
  `LSASM_SCF_PROG_TYPE` varchar(200) DEFAULT NULL,
  `LSASM_SCF_PROG_DESCRIPTION` varchar(200) DEFAULT NULL,
  `LSASM_PRODUCT_CODE` varchar(200) DEFAULT NULL,
  `LSASM_MAP_FACILITY_TO_PORTAL` varchar(200) DEFAULT NULL,
  `LSASM_FUNDING_CCY` varchar(200) DEFAULT NULL,
  `LSASM_TRANSACTION_TYPE` varchar(200) DEFAULT NULL,
  `LSASM_RELATIONSHIP_MANGR` varchar(200) DEFAULT NULL,
  `LSASM_VENDR_DLR_LIMIT_REQUIRED` varchar(200) DEFAULT NULL,
  `LSASM_INVOICE_FUNDING_PER` varchar(200) DEFAULT NULL,
  `LSASM_PROG_PAYMT_TERMS` varchar(200) DEFAULT NULL,
  `LSASM_ORIGINAL_PYMT_TRM` varchar(200) DEFAULT NULL,
  `LSASM_GRACE_DAYS` varchar(200) DEFAULT NULL,
  `LSASM_DUE_DATE_CALCULATION` varchar(200) DEFAULT NULL,
  `LSASM_BILLING_ADDRESS` varchar(200) DEFAULT NULL,
  `LSASM_CLIENT_FUNDING_LIMIT` varchar(200) DEFAULT NULL,
  `LSASM_TEMPORARY_LIMIT` varchar(200) DEFAULT NULL,
  `LSASM_CLIENT_REVIEW_DATE` varchar(200) DEFAULT NULL,
  `LSASM_TEMPORARY_LIMIT_EXPIRY_DATE` varchar(200) DEFAULT NULL,
  `LSASM_INTERIM_REVIEW_FREQUENCY` varchar(200) DEFAULT NULL,
  `LSASM_NEXT_INTERIM_REVIEW_ON` varchar(200) DEFAULT NULL,
  `LSASM_SERVICE_CHARGE_BORN_BY` varchar(200) DEFAULT NULL,
  `LSASM_SERVICE_CHARGE_APPLIED_ON` varchar(200) DEFAULT NULL,
  `LSASM_IGST` varchar(200) DEFAULT NULL,
  `LSASM_CGST` varchar(200) DEFAULT NULL,
  `LSASM_SCGT` varchar(200) DEFAULT NULL,
  `LSASM_UGST` varchar(200) DEFAULT NULL,
  `LSASM_APPLICATION_OF_INTEREST` varchar(200) DEFAULT NULL,
  `LSASM_INTEREST_BORNE_BY` varchar(200) DEFAULT NULL,
  `LSASM_APLLICATION_FREQUENCY` varchar(200) DEFAULT NULL,
  `LSASM_PENALITY_CHARGING_MATHODOLOGY` varchar(200) DEFAULT NULL,
  `LSASM_MINIMUM_INTEREST_APPLICABLE` varchar(200) DEFAULT NULL,
  `LSASM_PENALITY_BORNE_BY` varchar(200) DEFAULT NULL,
  `LSASM_INTREST_OVERRIDE_ALLOWED` varchar(200) DEFAULT NULL,
  `LSASM_FINDER` varchar(200) DEFAULT NULL,
  `LSASM_FINDER_CCY` varchar(200) DEFAULT NULL,
  `LSASM_INTF_STATUS` varchar(1) DEFAULT NULL,
  `LSASM_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSASM_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSASM_ACCT_CODE` varchar(200) DEFAULT NULL,
  `LSASM_CIF_ID` int(11) DEFAULT NULL,
  `LSASM_COMP_APPL_ID` varchar(200) DEFAULT NULL,
  `LSASM_SL_ID` varchar(50) DEFAULT NULL,
  `THIRDPARTY_ACCT_CODE` varchar(50) DEFAULT NULL,
  `CMPD_INT` varchar(200) DEFAULT NULL,
  `CMPD_OVD_INT` varchar(200) DEFAULT NULL,
  `DTD_TENOR` varchar(200) DEFAULT NULL,
  `LSASM_STALE_DAYS` varchar(200) DEFAULT NULL,
  `INT_INCOME_RECO_IND` varchar(200) DEFAULT NULL,
  `DISB_INT_DED_IND` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LSASM_ACCT_ID`),
  KEY `LSASM_CIF_ID` (`LSASM_CIF_ID`),
  CONSTRAINT `LOT_STG_ACCT_SETUP_MASTER_ibfk_1` FOREIGN KEY (`LSASM_CIF_ID`) REFERENCES `LOT_STG_COMP_MASTER` (`LSTCM_CIF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_COMP_ADDR` (
  `LSCA_STG_ADDR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LSCA_ADDR_TYP` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS1` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS1_LOCAL` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS2` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS2_LOCAL` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS3` varchar(200) DEFAULT NULL,
  `LSCA_ADDRESS3_LOCAL` varchar(200) DEFAULT NULL,
  `LSCA_COUNTRY` varchar(200) DEFAULT NULL,
  `LSCA_STATE` varchar(200) DEFAULT NULL,
  `LSCA_CITY` varchar(200) DEFAULT NULL,
  `LSCA_BRANCH` varchar(200) DEFAULT NULL,
  `LSCA_POST_CODE` varchar(200) DEFAULT NULL,
  `LSCA_EMAILID` varchar(200) DEFAULT NULL,
  `LSCA_LAND_LINE_NO` varchar(200) DEFAULT NULL,
  `LSCA_FAX_NO` varchar(200) DEFAULT NULL,
  `LSCA_MOBILE_NO` varchar(200) DEFAULT NULL,
  `LSCA_ADDITIONAL_CONTCT_NO` varchar(200) DEFAULT NULL,
  `LSCA_INTF_STATUS` varchar(200) DEFAULT NULL,
  `LSCA_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSCA_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSCA_CIF_ID` int(11) DEFAULT NULL,
  `LSCA_COMP_CODE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LSCA_STG_ADDR_ID`),
  KEY `LSCA_CIF_ID` (`LSCA_CIF_ID`),
  CONSTRAINT `LOT_STG_COMP_ADDR_ibfk_1` FOREIGN KEY (`LSCA_CIF_ID`) REFERENCES `LOT_STG_COMP_MASTER` (`LSTCM_CIF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_COMP_BANK_DTL` (
  `LSCBD_COMP_BANK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LSCBD_BANK_NAME` varchar(200) DEFAULT NULL,
  `LSCBD_BANK_BRANCH` varchar(200) DEFAULT NULL,
  `LSCBD_BANK_FULL_NAME` varchar(200) DEFAULT NULL,
  `LSCBD_BANK_FULL_BRANCH` varchar(200) DEFAULT NULL,
  `LSCBD_ACCT_TYP` varchar(200) DEFAULT NULL,
  `LSCBD_ACCOUNT_NO` varchar(200) DEFAULT NULL,
  `LSCBD_PAYMENT_CHANNEL` varchar(200) DEFAULT NULL,
  `LSCBD_CURRENCY` varchar(200) DEFAULT NULL,
  `LSCBD_PAYMENT_ACCT` varchar(200) DEFAULT NULL,
  `LSCBD_CUSTOMER_PAYOUT_ACCT` varchar(200) DEFAULT NULL,
  `LSCBD_AUTHORIZATION_BANK_RPT` varchar(200) DEFAULT NULL,
  `LSCBD_CLEARING_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_SWIFT_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_ABA_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_RTGS_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_NEFT_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_MICR_CODE` varchar(200) DEFAULT NULL,
  `LSCBD_ADD1` varchar(200) DEFAULT NULL,
  `LSCBD_ADD2` varchar(200) DEFAULT NULL,
  `LSCBD_ADD3` varchar(200) DEFAULT NULL,
  `LSCBD_INTF_STATUS` varchar(1) DEFAULT NULL,
  `LSCBD_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSCBD_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSCBD_CIF_ID` int(11) DEFAULT NULL,
  `LSCBD_COMP_CODE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LSCBD_COMP_BANK_ID`),
  KEY `LSCBD_CIF_ID` (`LSCBD_CIF_ID`),
  CONSTRAINT `LOT_STG_COMP_BANK_DTL_ibfk_1` FOREIGN KEY (`LSCBD_CIF_ID`) REFERENCES `LOT_STG_COMP_MASTER` (`LSTCM_CIF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_TP_INTEREST_DTL` (
  `LSTCD_TPID` int(11) NOT NULL,
  `LSTCD_RATE_TYPE` varchar(200) DEFAULT NULL,
  `LSTCD_CALCULATION_METHOD` varchar(200) DEFAULT NULL,
  `LSTCD_CALCULATION_FOR` varchar(200) DEFAULT NULL,
  `LSTCD_PENALITY_SPREAD` varchar(200) DEFAULT NULL,
  `LSTCD_FROM` varchar(200) DEFAULT NULL,
  `LSTCD_TO` varchar(200) DEFAULT NULL,
  `LSTCD_INTEREST_PER` varchar(200) DEFAULT NULL,
  `LSTCD_FLOATING_REFERNCE` varchar(200) DEFAULT NULL,
  `LSTCD_SPREAD` varchar(200) DEFAULT NULL,
  `LSTCD_ACTUAL_FLOATING_RATE` varchar(200) DEFAULT NULL,
  `LSTID_INTF_STATUS` varchar(1) DEFAULT NULL,
  `LSTID_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSTID_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSTSD_ACCT_CODE` varchar(200) DEFAULT NULL,
  `LSTCD_TP_CODE` varchar(200) DEFAULT NULL,
  `LSTCD_TPSD_CODE` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`LSTCD_TPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `LOT_STG_TP_SETUP_DTL` (
  `LSTSD_TPSDID` int(11) NOT NULL,
  `LSTSD_CORPORATE_NAME` varchar(200) DEFAULT NULL,
  `LSTSD_CORPORATE_PROGRAM_NAME` varchar(200) DEFAULT NULL,
  `LSTSD_SCF_FACILITY_NAME` varchar(200) DEFAULT NULL,
  `LSTSD_BU_NAME` varchar(200) DEFAULT NULL,
  `LSTSD_FACILITY_EXPIRY_DATE` varchar(200) DEFAULT NULL,
  `LSTSD_FUNDING_LIMIT` varchar(200) DEFAULT NULL,
  `LSTSD_FUNDING_CCY` varchar(200) DEFAULT NULL,
  `LSTSD_RELATIONSHIP_MANAGER` varchar(200) DEFAULT NULL,
  `LSTSD_BU_FACILITY_CODE` varchar(200) DEFAULT NULL,
  `LSTSD_ANCH_CODE_OF_BUS_HST_SYTM` varchar(200) DEFAULT NULL,
  `LSTSD_BU_CODE_OF_ANCH_HST_SYTM` varchar(200) DEFAULT NULL,
  `LSTSD_GRACE_PRD_FOR_BLK_LIMIT` varchar(200) DEFAULT NULL,
  `LSTSD_INVOICE_FUNDING_CCY` varchar(200) DEFAULT NULL,
  `LSTSD_INSTRUMENT_CCY` varchar(200) DEFAULT NULL,
  `LSTSD_PAYMENT_TERMS` varchar(200) DEFAULT NULL,
  `LSTSD_PAYMENT_TERMS_FLAG` varchar(200) DEFAULT NULL,
  `LSTSD_GRACE_DAYS` varchar(200) DEFAULT NULL,
  `LSTSD_DUE_DATE_CAL` varchar(200) DEFAULT NULL,
  `LSTSD_INVOICE_CONFIRMATION` varchar(200) DEFAULT NULL,
  `LSTSD_STOP_DUNNING` varchar(200) DEFAULT NULL,
  `LSTSD_PARENT_BU_NAME` varchar(200) DEFAULT NULL,
  `LSTSD_SEND_EMAIL` varchar(200) DEFAULT NULL,
  `LSTSD_STOP_SUPPLY_INDCATOR` varchar(200) DEFAULT NULL,
  `LSTSD_EMAILID` varchar(200) DEFAULT NULL,
  `LSTSD_NOG` varchar(200) DEFAULT NULL,
  `LSTSD_BILLING_ADDR` varchar(200) DEFAULT NULL,
  `LSTSD_DESCRIPTION` varchar(200) DEFAULT NULL,
  `LSTSD_SERVICE_CHARGE_BORNE_BY` varchar(200) DEFAULT NULL,
  `LSTSD_CREDIT_TO_BANK_ACCT` varchar(200) DEFAULT NULL,
  `LSTSD_AUTO_DEBIT_CHG_FROM_BU` varchar(200) DEFAULT NULL,
  `LSTSD_APPL_OF_INTEREST` varchar(200) DEFAULT NULL,
  `LSTSD_APPL_FREQUENCY` varchar(200) DEFAULT NULL,
  `LSTSD_INTEREST_BORNE_BY` varchar(200) DEFAULT NULL,
  `LSTSD_MINIMUM_INTEREST_APPL` varchar(200) DEFAULT NULL,
  `LSTSD_PENALTY_CHARGING_METH` varchar(200) DEFAULT NULL,
  `LSTSD_PENALTY_BORNE_BY` varchar(200) DEFAULT NULL,
  `LSTSD_OVER_ALLOWED_AT_TRAN_LEVEL` varchar(200) DEFAULT NULL,
  `LSTSD_CREDIT_TO_BANK_ACCOUNT` varchar(200) DEFAULT NULL,
  `LSTSD_REPYMNT_OPTION` varchar(200) DEFAULT NULL,
  `LSTSD_REPYMNT_FROM` varchar(200) DEFAULT NULL,
  `LSTSD_REPYMNT_BANK_ACCT` varchar(200) DEFAULT NULL,
  `LSTSD_REPYMNT_TO_ACCOUNT` varchar(200) DEFAULT NULL,
  `LSTSD_RECOVERY_OF_OVERDUES` varchar(200) DEFAULT NULL,
  `LSTSD_DISBMT_REQT_ALLOWED` varchar(200) DEFAULT NULL,
  `LSTSD_MODE_OF_PAYMENT` varchar(200) DEFAULT NULL,
  `LSTSD_BENEFICIARY_PAYOUT_TO` varchar(200) DEFAULT NULL,
  `LSTSD_PAYMENT_CHANNEL` varchar(200) DEFAULT NULL,
  `LSTSD_PAYOUT_FROM_BANK_ACCT` varchar(200) DEFAULT NULL,
  `LSTSD_PAYOUT_TO_BANK_ACCT` varchar(200) DEFAULT NULL,
  `LSTSD_AUTO_PAYOUT` varchar(200) DEFAULT NULL,
  `LSTSD_ACCT_CODE` varchar(200) DEFAULT NULL,
  `LSTSD_TP_CODE` varchar(200) DEFAULT NULL,
  `LSTSD_INTF_STATUS` varchar(1) DEFAULT NULL,
  `LSTSD_INTF_DATE` varchar(200) DEFAULT NULL,
  `LSTSD_INTF_ERROR` varchar(200) DEFAULT NULL,
  `LSTSD_INVOICE_FUNDING` varchar(200) DEFAULT NULL,
  `THIRDPARTY_ACCT_CODE` varchar(50) DEFAULT NULL,
  `CMPD_INT` varchar(200) DEFAULT NULL,
  `CMPD_OVD_INT` varchar(200) DEFAULT NULL,
  `DTD_TENOR` varchar(200) DEFAULT NULL,
  `LSTCD_CPRTY_ACCT_CODE` varchar(200) DEFAULT NULL,
  `LSTSD_STALE_DAYS` int(11) DEFAULT NULL,
  PRIMARY KEY (`LSTSD_TPSDID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `anchor_address` (
  `id` bigint(20) NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `pin_code` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `app_id` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtn723jns6jnosohjmjvbf65xh` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;


CREATE TABLE anchor_authorized (
  id int(20) NOT NULL,
  email_id varchar(255) NOT NULL,
  indemnity_doc varchar(255) DEFAULT NULL,
  mobile varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKillq9k9yy2jvp8j2xo3gqg5n4 (app_id)
) ;


CREATE TABLE anchor_basic (
  id bigint(20) NOT NULL,
  anchor_name varchar(255) NOT NULL,
  cin varchar(255) NOT NULL,
  incorp_date datetime NOT NULL,
  industry varchar(255) NOT NULL,
  pan varchar(255) NOT NULL,
  sector varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  activity varchar(255) NOT NULL,
  constitution varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY FKsdonsip4wb4ijguygu9wpodlj (app_id)
) ;


CREATE TABLE anchor_beneficiary (
  id bigint(20) NOT NULL,
  bank_acct_number varchar(18) NOT NULL,
  bank_name varchar(255) NOT NULL,
  bank_ifsc_code varchar(255) NOT NULL,
  ben_name varchar(255) NOT NULL,
  ben_type varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  bank_branch_code varchar(255) DEFAULT NULL,
  bank_branch_name varchar(255) NOT NULL,
  bank_code varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_97c219li1afxdpwssgaga4a19 (bank_acct_number),
  KEY FKfp9xeg415j3xadl0up8miaeap (app_id)
) ;



CREATE TABLE anchor_code (
  id bigint(20) NOT NULL,
  cif_id decimal(19,2) NOT NULL,
  vcpl_code varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);



CREATE TABLE anchor_gst (
  id bigint(20) NOT NULL,
  address_line1 varchar(255) NOT NULL,
  address_line2 varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  country varchar(255) NOT NULL,
  gst_acct_holder_name varchar(255) NOT NULL,
  gst_number varchar(255) NOT NULL,
  pincode varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKswy91yw52skkub32hky7sarki (app_id)
) ;


CREATE TABLE anchor_key (
  id bigint(20) NOT NULL,
  email varchar(255) NOT NULL,
  mobile varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK63rvrhort31itcv9yjj957g4a (app_id)
) ;


CREATE TABLE anchor_program (
  id bigint(20) NOT NULL,
  adhoc_prog_limit double NOT NULL,
  anchor_onboarding_date datetime DEFAULT NULL,
  cmp_ovd_int varchar(255) NOT NULL,
  cmpd_int varchar(255) NOT NULL,
  counter_party_grace_period double NOT NULL,
  cp_max_limit double NOT NULL,
  cp_min_limit double NOT NULL,
  door_to_door double NOT NULL,
  due_date_calculation varchar(255) NOT NULL,
  funding_percent double NOT NULL,
  funding_type varchar(255) NOT NULL,
  interest_app_tye varchar(255) NOT NULL,
  interest_calculation varchar(255) NOT NULL,
  interest_ownership varchar(255) NOT NULL,
  interest_payment_frequency varchar(255) NOT NULL,
  interest_rate double NOT NULL,
  interim_review_frequency double NOT NULL,
  invoice_ageing double NOT NULL,
  max_drawdown double NOT NULL,
  next_interim_review_on datetime NOT NULL,
  overall_prog_limit double NOT NULL,
  penal_interest double NOT NULL,
  penal_interest_ownership varchar(255) NOT NULL,
  pricing_roi_max double NOT NULL,
  pricing_roi_min double NOT NULL,
  processing_fees_max double NOT NULL,
  processing_fees_min double NOT NULL,
  program_type varchar(255) NOT NULL,
  regular_prog_limit double NOT NULL,
  repayment_type varchar(255) NOT NULL,
  security_covg_primary varchar(255) DEFAULT NULL,
  security_covg_secondry varchar(255) DEFAULT NULL,
  stop_supply_trigger_days double NOT NULL,
  sub_product varchar(255) NOT NULL,
  tenure double NOT NULL,
  transaction_type varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK58eetc7dvjmftlsyid2fmalat (app_id)
) ;


CREATE TABLE application_details_info (
  id bigint(20) NOT NULL,
  app_type int(11) NOT NULL,
  type int(11) NOT NULL,
  cust_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKsyfu19mg428atss57h162r5n (cust_id)
) ;



CREATE TABLE application_details_info_anchor_address_details_entity_list (
  application_entity_id bigint(20) NOT NULL,
  anchor_address_details_entity_list_id bigint(20) NOT NULL,
  UNIQUE KEY UK_i5gi03g7apw9da0wj06sfs1b1 (anchor_address_details_entity_list_id),
  KEY FKlswwe10jnq2l8inbyhh44h6qb (application_entity_id)
) ;



CREATE TABLE application_details_info_anchor_authorized_entities (
  application_entity_id bigint(20) NOT NULL,
  anchor_authorized_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_9q4di29n6dbu2lox1ns6im4nf (anchor_authorized_entities_id)
);


CREATE TABLE application_details_info_anchor_basic_details (
  application_entity_id bigint(20) NOT NULL,
  anchor_basic_details_id bigint(20) NOT NULL,
  UNIQUE KEY UK_gbpwkcd11l1w00eof3q5m9rfl (anchor_basic_details_id)
);



CREATE TABLE application_details_info_anchor_gst_entities (
  application_entity_id bigint(20) NOT NULL,
  anchor_gst_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_qkk45tdaxy4irsutibdllr9o6 (anchor_gst_entities_id)
);


CREATE TABLE application_details_info_anchor_key_entities (
  application_entity_id bigint(20) NOT NULL,
  anchor_key_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_4iqelyte85xa80tht372xoccf (anchor_key_entities_id)
);


CREATE TABLE application_details_info_beneficiary_entities (
  application_entity_id bigint(20) NOT NULL,
  beneficiary_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_joyfvijfdvporgdpio6g5lqgc (beneficiary_entities_id)
);


CREATE TABLE application_details_info_counter_party_credit_policy_entities (
  application_entity_id bigint(20) NOT NULL,
  counter_party_credit_policy_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_gmk9v1qra4lvsgd1kuftpcgwx (counter_party_credit_policy_entities_id)
);


CREATE TABLE application_details_info_program_details_entities (
  application_entity_id bigint(20) NOT NULL,
  program_details_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_kxlgkr8n8mjko73ck0hv0r0pq (program_details_entities_id)
);


CREATE TABLE application_details_info_proposed_products_entities (
  application_entity_id bigint(20) NOT NULL,
  proposed_products_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_3g3yjwkfypjxdlcg96xilkxdi (proposed_products_entities_id)
);


CREATE TABLE application_details_info_term_sheet_entity (
  application_entity_id bigint(20) NOT NULL,
  term_sheet_entity_id bigint(20) NOT NULL,
  UNIQUE KEY UK_8yrj70uqc91tvrdq8g9c5my2b (term_sheet_entity_id)
);



CREATE TABLE assign_underwriter (
  id bigint(20) NOT NULL,
  assign_to varchar(255) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  update_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);



CREATE TABLE cp_basic_details (
  id bigint(20) NOT NULL,
  cin_number varchar(21) DEFAULT NULL,
  city varchar(255) NOT NULL,
  company_name varchar(255) NOT NULL,
  constitution varchar(255) NOT NULL,
  cus_contact_name varchar(255) NOT NULL,
  cus_contact_email varchar(255) NOT NULL,
  cus_contact_number varchar(255) NOT NULL,
  gst_number varchar(255) DEFAULT NULL,
  pan varchar(255) NOT NULL,
  rm_name varchar(255) NOT NULL,
  source varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  sub_source varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  activity varchar(255) NOT NULL,
  anchor_relation_ship varchar(255) DEFAULT NULL,
  assessment_type varchar(255) DEFAULT NULL,
  total_inward_cheques varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKsvhowo3lnbi8cs8dp6nwm0wmw (app_id)
) ;


CREATE TABLE cp_beneficiary_details (
  id bigint(20) NOT NULL,
  cp_beneficiary_acct bigint(20) NOT NULL,
  cp_beneficiary_acct_type varchar(255) NOT NULL,
  cp_beneficiary_bank_name varchar(255) NOT NULL,
  cp_beneficiary_branch varchar(255) NOT NULL,
  cp_beneficiary_ifsc varchar(255) NOT NULL,
  cp_beneficiary_micr varchar(255) NOT NULL,
  cp_beneficiary_name varchar(255) NOT NULL,
  cp_beneficiary_swift_code varchar(255) NOT NULL,
  cp_beneficiary_type varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_7fywywu8mw6n7vil1h9kdff4a (cp_beneficiary_acct)
);


CREATE TABLE cp_collateral_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  cm_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKh2poyao2nbvhyublkwpti0t45 (app_id),
  KEY FKoiqjkesrvfafsas1ubmnqydgk (cm_id)
) ;


CREATE TABLE cp_collateral_master (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  datatype varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  regex varchar(255) NOT NULL,
  sequence varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


CREATE TABLE cp_commercial_table (
  id bigint(20) NOT NULL,
  adhoc_limit_remarks varchar(255) DEFAULT NULL,
  anchor_remarks varchar(255) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  credit_period_remarks varchar(255) DEFAULT NULL,
  door_remarks varchar(255) DEFAULT NULL,
  interest_rate_remarks varchar(255) DEFAULT NULL,
  interest_type_remarks varchar(255) DEFAULT NULL,
  invoice_ageing_remarks varchar(255) DEFAULT NULL,
  margin_remarks varchar(255) DEFAULT NULL,
  pf_remarks varchar(255) DEFAULT NULL,
  product_remarks varchar(255) DEFAULT NULL,
  regular_limits_remarks varchar(255) DEFAULT NULL,
  renewal_period_remarks varchar(255) DEFAULT NULL,
  renewal_remarks varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKe2qtp1t96fds4545p4yxrb7co (app_id)
) ;


CREATE TABLE cp_credit_policy_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  cp_master_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKr6i9217oct7mkuhke98v7bitd (app_id),
  KEY FKku5vvhmxrll3ujs4nt1ub7bsx (cp_master_id)
) ;



CREATE TABLE cp_dd_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  dd_master_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKmis5qgv4ngxqubq33m1d4rhvv (app_id),
  KEY FKkjlt8bqw1hibawdwjb55ysff5 (dd_master_id)
) ;


CREATE TABLE cp_dd_master (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  datatype varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  regex varchar(500) NOT NULL,
  sequence varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


CREATE TABLE cp_debt_profile (
  id bigint(20) NOT NULL,
  bank_fi varchar(255) DEFAULT NULL,
  emi varchar(255) NOT NULL,
  facility_type varchar(255) NOT NULL,
  interest_rate float NOT NULL,
  outstanding_on_date varchar(255) NOT NULL,
  remarks varchar(255) NOT NULL,
  sanction_date varchar(255) NOT NULL,
  sanction_limit varchar(255) NOT NULL,
  security varchar(255) NOT NULL,
  specific_limit varchar(255) NOT NULL,
  tenure varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  cmpd_int varchar(255) NOT NULL,
  cmpd_ovd_int varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY FKmnble848cuyq84caxvuh335d4 (app_id)
) ;


CREATE TABLE cp_fund_requirement_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  fund_req_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK98bt3o2hfndjayqaetxcxo76y (app_id),
  KEY FKbu4k6lh6fdqmwkuqbb16eotpt (fund_req_id)
) ;


CREATE TABLE cp_fund_requirement_master (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  data_type varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  questions varchar(255) NOT NULL,
  requirement_name varchar(255) NOT NULL,
  sequence_no varchar(255) DEFAULT NULL,
  status varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE cp_limit_eligibility_details (
  id bigint(20) NOT NULL,
  adhoc_limit varchar(255) NOT NULL,
  anchor_recommended_amount varchar(255) NOT NULL,
  approtioned_limits varchar(255) NOT NULL,
  calculated_limit_wo_set_off varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  credit_period varchar(255) NOT NULL,
  current_limit varchar(255) NOT NULL,
  customer_requested_amount varchar(255) NOT NULL,
  door_to_door varchar(255) NOT NULL,
  eligible_limit varchar(255) NOT NULL,
  existing_scf_limits varchar(255) NOT NULL,
  expected_growth varchar(255) NOT NULL,
  invoice_ageing varchar(255) NOT NULL,
  margin varchar(255) NOT NULL,
  model_limit varchar(255) NOT NULL,
  monthly_average varchar(255) NOT NULL,
  product varchar(255) NOT NULL,
  proposed_limit varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  cust_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK887r2tsm1t79jya7s912xyds3 (app_id),
  KEY FKryfkl58v5y5ax1pnny3n3v6b4 (cust_id)
) ;

CREATE TABLE cp_proposed_products (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  product varchar(255) NOT NULL,
  proposed varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  cust_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK2wccofc6638iu5seg371if4c6 (app_id),
  KEY FKj2wknc7ax11y6ntpyt25w66di (cust_id)
) ;


CREATE TABLE cp_soft_policy_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  soft_policy_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK43w61sg78ok7n9tsu6v6kdtx1 (app_id),
  KEY FKhbmxlfexgsmvnkqis9jp69wxn (soft_policy_id)
) ;


CREATE TABLE cp_term_sheet (
  id bigint(20) NOT NULL,
  adhoc_limit varchar(255) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  credit_period varchar(255) DEFAULT NULL,
  door_to_door varchar(255) DEFAULT NULL,
  interest_rate varchar(255) DEFAULT NULL,
  interest_rate_type varchar(255) DEFAULT NULL,
  invoice_ageing varchar(255) DEFAULT NULL,
  margin varchar(255) DEFAULT NULL,
  pf varchar(255) DEFAULT NULL,
  product varchar(255) DEFAULT NULL,
  regular_limit varchar(255) DEFAULT NULL,
  renewal varchar(255) DEFAULT NULL,
  renewal_period varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  cust_id bigint(20) DEFAULT NULL,
  apply_of_interest varchar(255) NOT NULL,
  grace_days varchar(255) NOT NULL,
  interest_borne_by varchar(255) NOT NULL,
  invoice_funding varchar(255) NOT NULL,
  penalty_borne_by varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY FK3xlurdlmopuhf0hyk6rplsbii (app_id),
  KEY FK2f1sm53knf6on3qbnjbj6xvu2 (cust_id)
) ;


CREATE TABLE credit_norms_details (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  value varchar(255) NOT NULL,
  app_id bigint(20) DEFAULT NULL,
  cn_master_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKma79cevk9mv28nt310vc2m1n1 (app_id),
  KEY FKhnuui7b5375b49ss6cjhqgi6v (cn_master_id)
) ;


CREATE TABLE credit_norms_master (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  datatype varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  regex varchar(255) NOT NULL,
  sequence varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


CREATE TABLE credit_policy_config (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  anchor_relationship varchar(255) NOT NULL,
  assessment_type varchar(255) NOT NULL,
  cp_type varchar(255) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  recourse varchar(255) NOT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;



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



CREATE TABLE credit_policy_master (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  data_type varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  mandatory varchar(255) NOT NULL,
  policy_name varchar(255) NOT NULL,
  policy_type varchar(255) NOT NULL,
  regex varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  filters varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


CREATE TABLE credit_policy_master_credit_policy_filters (
  credit_policy_master_entity_id bigint(20) NOT NULL,
  credit_policy_filters_id bigint(20) NOT NULL,
  UNIQUE KEY UK_ouc5n4ujwmvuu6jlo5vhd63mb (credit_policy_filters_id),
  CONSTRAINT FKeom5jjsqvx44b617owc4tt35i FOREIGN KEY (credit_policy_filters_id) REFERENCES credit_policy_filters (id)
);



CREATE TABLE customer_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cin varchar(21) DEFAULT NULL,
  customer_name varchar(50) NOT NULL,
  pan varchar(10) NOT NULL,
  product varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_idnf6o90v07sskqo07prcqvv0 (pan)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;



CREATE TABLE customer_info_anchor_address_details_entity_list (
  customer_info_entity_id bigint(20) NOT NULL,
  anchor_address_details_entity_list_id bigint(20) NOT NULL,
  UNIQUE KEY UK_716dwabmcprsam0bl49dhy67j (anchor_address_details_entity_list_id)
);


CREATE TABLE customer_info_anchor_authorized_entities (
  customer_info_entity_id bigint(20) NOT NULL,
  anchor_authorized_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_7cjbn2ninn9xuq3xbwb1wpixj (anchor_authorized_entities_id)
);


CREATE TABLE customer_info_anchor_basic_details (
  customer_info_entity_id bigint(20) NOT NULL,
  anchor_basic_details_id bigint(20) NOT NULL,
  UNIQUE KEY UK_sfbdogeci5g47ot3nf5f7pfoy (anchor_basic_details_id)
);


CREATE TABLE customer_info_anchor_gst_entities (
  customer_info_entity_id bigint(20) NOT NULL,
  anchor_gst_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_4a91wlrdsoctyfsludfa2xyyr (anchor_gst_entities_id)
);


CREATE TABLE customer_info_anchor_key_entities (
  customer_info_entity_id bigint(20) NOT NULL,
  anchor_key_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_cs6bwui1gs7p3wial3u79c0f9 (anchor_key_entities_id)
);


CREATE TABLE customer_info_beneficiary_entities (
  customer_info_entity_id bigint(20) NOT NULL,
  beneficiary_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_pncyvx4w73393x8d1932dxg93 (beneficiary_entities_id)
);


CREATE TABLE customer_info_program_details_entities (
  customer_info_entity_id bigint(20) NOT NULL,
  program_details_entities_id bigint(20) NOT NULL,
  UNIQUE KEY UK_55d64i7xnjkw62x90jp7v62u0 (program_details_entities_id)
);


--CREATE TABLE deferral_report_data (
--  id bigint(20) NOT NULL,
--  created_at datetime DEFAULT NULL,
--  created_by varchar(255) DEFAULT NULL,
--  data_view varchar(255) DEFAULT NULL,
--  doc_completion_date date DEFAULT NULL,
--  document_id varchar(255) DEFAULT NULL,
--  document_name varchar(255) DEFAULT NULL,
--  initial_time date DEFAULT NULL,
--  revised_time date DEFAULT NULL,
--  rm_name varchar(255) DEFAULT NULL,
--  status int(11) DEFAULT NULL,
--  updated_at datetime DEFAULT NULL,
--  updated_by varchar(255) DEFAULT NULL,
--  app_id bigint(20) DEFAULT NULL,
--  doc_list_id bigint(20) DEFAULT NULL,
--  PRIMARY KEY (id),
--  KEY FKcq0o10k9cqtat95xsxt4464qn (app_id),
--  KEY FKi7j6pyvs87jkjsss3ds01nuu7 (doc_list_id)
--) ;


CREATE TABLE deferral_workflow_stage_approval_status (
  id bigint(20) NOT NULL,
  approved_status bit(1) DEFAULT NULL,
  user_info varchar(255) NOT NULL,
  created_time varchar(255) DEFAULT NULL,
  current_stage_approver varchar(255) DEFAULT NULL,
  timestamp varchar(255) NOT NULL,
  remarks varchar(255) DEFAULT NULL,
  seq_no int(11) NOT NULL,
  application_status bigint(20) NOT NULL,
  updated_time varchar(255) DEFAULT NULL,
  app_id bigint(20) NOT NULL,
  stage_id bigint(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE dms_document_types (
  id bigint(20) NOT NULL,
  status int(11) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  display_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  seq_no int(11) NOT NULL,
  type int(11) NOT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE dms_document_category (
  id bigint(20) NOT NULL,
  category varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  seq_no int(11) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  doc_type_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY FK3r0eteb3fdmh40cl6v2d5t0dq (doc_type_id),
  CONSTRAINT FK3r0eteb3fdmh40cl6v2d5t0dq FOREIGN KEY (doc_type_id) REFERENCES dms_document_types (id) ON DELETE CASCADE
);



CREATE TABLE dms_document_list (
  id bigint(20) NOT NULL,
  category varchar(255) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  mandatory int(11) NOT NULL,
  name varchar(255) DEFAULT NULL,
  seq_no int(11) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  doc_category_id bigint(20) NOT NULL,
  version varchar(255) DEFAULT NULL,
  deferral int(11) DEFAULT NULL,
  type int(11) DEFAULT NULL,
  deferral_time bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK4apmcabmj22plffa7xkm9wqnd (doc_category_id),
  CONSTRAINT FK4apmcabmj22plffa7xkm9wqnd FOREIGN KEY (doc_category_id) REFERENCES dms_document_category (id) ON DELETE CASCADE
);


CREATE TABLE dms_deferral_document_reports (
  id bigint(20) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  document_name varchar(255) DEFAULT NULL,
  initial_time date DEFAULT NULL,
  revised_time date DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  doc_list_id bigint(20) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  doc_completion_date date DEFAULT NULL,
  document_id varchar(255) DEFAULT NULL,
  rm_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKpqsqo4ylnk38ca5symd06gyqe (doc_list_id),
  CONSTRAINT FKpqsqo4ylnk38ca5symd06gyqe FOREIGN KEY (doc_list_id) REFERENCES dms_document_list (id)
);

CREATE TABLE dms_document_reports (
  id bigint(20) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  file_name varchar(255) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  version varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  doc_category_id bigint(20) DEFAULT NULL,
  doc_list_id bigint(20) DEFAULT NULL,
  doc_type_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKcmecndgfri4kdwesgubosc0q2 (doc_category_id),
  KEY FKqnhu2iqr7yjn63w50vusj8xrb (doc_list_id),
  KEY FKdwtdjgct26hh60anj8anuumo5 (doc_type_id),
  CONSTRAINT FKcmecndgfri4kdwesgubosc0q2 FOREIGN KEY (doc_category_id) REFERENCES dms_document_category (id),
  CONSTRAINT FKdwtdjgct26hh60anj8anuumo5 FOREIGN KEY (doc_type_id) REFERENCES dms_document_types (id),
  CONSTRAINT FKqnhu2iqr7yjn63w50vusj8xrb FOREIGN KEY (doc_list_id) REFERENCES dms_document_list (id)
);

CREATE TABLE dms_other_document_master (
  id bigint(20) NOT NULL,
  seq_no int(11) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  doc_list_id bigint(20) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  initial_time date DEFAULT NULL,
  revised_time date DEFAULT NULL,
  rm_name varchar(255) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK3cejvkfvsyfkhfuf8yq67yuix (app_id),
  KEY FKpddyoyr3c88ylf25hl2lydttc (doc_list_id)
) ;

CREATE TABLE dms_other_document_reports (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  file_name varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  app_id bigint(20) DEFAULT NULL,
  doc_category_id bigint(20) DEFAULT NULL,
  doc_list_id bigint(20) DEFAULT NULL,
  doc_type_id bigint(20) DEFAULT NULL,
  other_doc_master bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK4lvp78o2f4umjq3suii346mtq (app_id),
  KEY FK103uhx7a26hb0lin1l09ljdk1 (doc_category_id),
  KEY FKip1ytdkwvh0vwov1u6i34oxkd (doc_list_id),
  KEY FKcf0nfk02awg233psbas7c55pk (doc_type_id),
  KEY FKjm2xpapu4gnu0vbfh6ky140h0 (other_doc_master)
) ;

CREATE TABLE email_notification_tamplate (
  id bigint(20) NOT NULL,
  action varchar(255) NOT NULL,
  content varchar(255) NOT NULL,
  stage varchar(255) NOT NULL,
  subject varchar(255) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE hibernate_sequence (next_val bigint) ENGINE=InnoDB;
INSERT INTO hibernate_sequence VALUES(1);

CREATE TABLE keycloak_group (
  id bigint(20) NOT NULL,
  group_name varchar(255) NOT NULL,
  group_path varchar(255) NOT NULL,
  sub_group tinyblob NOT NULL,
  user_id varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE keycloak_user (
  id bigint(20) NOT NULL,
  email varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  email_verified bit(1) NOT NULL,
  last_name varchar(255) NOT NULL,
  user_creation_timestamp datetime DEFAULT NULL,
  user_id varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  group_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK7tqbaibg4rdf48i1lb3j9u9up (group_id)
) ;

CREATE TABLE pin_code (
  id int(11) NOT NULL AUTO_INCREMENT,
  pincode double DEFAULT NULL,
  district varchar(100) DEFAULT NULL,
  state varchar(100) DEFAULT NULL,
  country varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=19253 DEFAULT CHARSET=utf8mb4;

CREATE TABLE rbac_group (
  id bigint(20) NOT NULL,
  application_count int(11) NOT NULL,
  community int(11) NOT NULL,
  group_name varchar(255) NOT NULL,
  is_enable int(11) NOT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE rbac_permission (
  id bigint(20) NOT NULL,
  approve bit(1) NOT NULL,
  view bit(1) NOT NULL,
  reject bit(1) NOT NULL,
  is_return bit(1) NOT NULL,
  is_submit bit(1) NOT NULL,
  role varchar(255) NOT NULL,
  stage_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK6d56o9cvo5d2by3ldjmd3qa8i (stage_id)
) ;

CREATE TABLE rbac_token (
  id bigint(20) NOT NULL,
  browser_id varchar(255) DEFAULT NULL,
  flag int(11) NOT NULL,
  jwt_token longtext NOT NULL,
  refresh_token longtext NOT NULL,
  status bit(1) DEFAULT NULL,
  timestamp datetime DEFAULT NULL,
  expired_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE rbac_user (
  id bigint(20) NOT NULL,
  user_name varchar(255) NOT NULL,
  group_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKfohb6lssrylax09w34hoh5f9c (group_id)
) ;

CREATE TABLE remarks (
  id bigint(20) NOT NULL,
  created_by varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  stepper_tab varchar(255) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  ci_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKrh780i369cuyb3dskdx24j7hf (ci_id)
) ;

CREATE TABLE soft_policy_filters (
  id bigint(20) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  formula varchar(500) NOT NULL,
  type varchar(255) NOT NULL,
  updated_at datetime(6) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  soft_policy_sub_type_id bigint(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE soft_policy_master_sub_type (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  data_type varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  regex varchar(255) DEFAULT NULL,
  sequance_number varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  soft_policy_type_id bigint(20) NOT NULL,
  mandatory varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKgl0fay6s59e4b6qo1q535e84o (soft_policy_type_id)
) ;

CREATE TABLE soft_policy_master_type (
  id bigint(20) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  sequance_no varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE workflow_stage (
  id bigint(20) NOT NULL,
  client_type bigint(20) NOT NULL,
  stage varchar(255) NOT NULL,
  group_id bigint(20) DEFAULT NULL,
  approver_permission bit(1) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKed1xdy1gtfnpiqa68k7urcic4 (group_id)
) ;

CREATE TABLE workflow_stage_approval_status (
  id bigint(20) NOT NULL,
  approved_status bit(1) DEFAULT NULL,
  user_info varchar(255) NOT NULL,
  created_time datetime(6) DEFAULT NULL,
  current_stage_approver varchar(255) DEFAULT NULL,
  timestamp varchar(255) NOT NULL,
  remarks varchar(255) DEFAULT NULL,
  seq_no int(11) NOT NULL,
  application_status bigint(20) NOT NULL,
  updated_time datetime(6) DEFAULT NULL,
  app_id bigint(20) NOT NULL,
  stage_id bigint(20) NOT NULL,
  PRIMARY KEY (id)
);