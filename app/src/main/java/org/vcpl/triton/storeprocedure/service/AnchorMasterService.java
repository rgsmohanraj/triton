package org.vcpl.triton.storeprocedure.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.vcpl.triton.security.config.AWSDBConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;
import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Service
public class AnchorMasterService {

    @Autowired
    private AWSDBConfiguration jdbcConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnchorMasterService.class);

    public String saveAnchorMaster (BigInteger cifId, long id, String companyName) throws SQLException {
        List<String> cifIdList = new ArrayList<>();
        JSONObject json = new JSONObject();
        String programcode = null;
        String LSASM_ACCT_CODE = null;
        String programCode = "";
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        Statement st1 = con.createStatement();
        Statement stTest = con.createStatement();
        Statement st2 = con2.createStatement();
        try {
            int count = 0;
            String corporate = companyName;
            String corporateProgram = corporate + " PROGRAM";
            ResultSet rs2 = st1.executeQuery("SELECT * FROM anchor_program WHERE app_id = " + id);
            JSONArray array = new JSONArray();

            while (rs2.next()) {
                    count++;
                    String programType = rs2.getString(29);
                    String programType1 = null;
                    if (programType.equalsIgnoreCase("Dealer")) {
                        programType1 = "PROG_TYPE_DEALER";
                    } else if (programType.equalsIgnoreCase("Vendor")) {
                        programType1 = "PROG_TYPE_VENDOR";
                    }
                    programCode = rs2.getString(35).trim(); //subProduct
                    if (programCode.equalsIgnoreCase("Anchor Sales Bill Discounting")) {
                        programcode = "ANCSBD";
                    } else if (programCode.equalsIgnoreCase("Anchor Purchase Bill Discounting")) {
                        programcode = "ANCPBD";
                    } else if (programCode.equalsIgnoreCase("Dealer Purchase Order Finance")){
                        programcode = "REGPIF";
                    }else if (programCode.equalsIgnoreCase("Dealer Invoice Finance")){
                        programcode = "REGPBD";
                    }else if (programCode.equalsIgnoreCase("Vendor Purchase Order Finance")){
                        programcode = "DF";
                    }else if (programCode.equalsIgnoreCase("Vendor Invoice Finance")){
                        programcode = "VF";
                    }
                    String fundingCode = "INR";
                    String transaction = rs2.getString(37);
                    String transaction1 = "";
                    if (transaction.equalsIgnoreCase("Recourse")) {
                        transaction1 = "TRANSTYPE_RECOURSE";
                    } else if (transaction.equalsIgnoreCase("Non Recourse")) {
                        transaction1 = "TRANSTYPE_NORECOURSE";
                    }
                    String fundingPercentage = rs2.getString(11);
                    String programPayment = rs2.getString(36); // tenure
                    String originalPayment = rs2.getString(36); // tenure
                    String graceDays = rs2.getString(6);
                    String dateCalculation = "CALC_DUE_DT_FROM_FROM_INV_DATE";
                    String fundingLimit = rs2.getString(22); //Overall ProgramLimit
                    String reviewFeq = rs2.getString(18); //interim_review_frequency
                    String reviewDate1 = rs2.getString(21); //next_interim_review_on
//                Date reviewDate = new SimpleDateFormat("dd-mm-yy").parse(reviewDate1);

                SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-mm-dd");
                SimpleDateFormat outputDate = new SimpleDateFormat("dd-mm-yy");
                Date date = inputDate.parse(reviewDate1);
                String reviewDate = outputDate.format(date);
                LOGGER.info("*******reviewDate*****"+reviewDate);
//                    String reviewDate = "14-02-27";
//               String reviewOn = format1.format(reviewDate);

                    String applicationInterest = rs2.getString(13).trim(); //Interest_app_type
                    String application = "";
                    if (applicationInterest.equalsIgnoreCase("In-Arrears")) {
                        application = "DISCRATETY_REARENDED";

                    } else if (applicationInterest.equalsIgnoreCase("Upfront")) {
                        application = "DISCRATETY_FRONTENDED";
                    }
                    String interestBorne = rs2.getString(15).trim(); //Interest Ownership
                    String interest = "";
                    if (interestBorne.equalsIgnoreCase("Counterparty")) {
                        interest = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (interestBorne.equalsIgnoreCase("anchor") || interestBorne.equalsIgnoreCase("Anchor")) {
                        interest = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    String applicationFreq = "DISCFREQ_MONTHLY";
                    String penality = "PENAL_INT_CHARGE_INT_RATE_PEN";
                    String penalityBorne = rs2.getString(15).trim(); //Interest Ownership
                    String penalityBornee = "";
                    if (penalityBorne.equalsIgnoreCase("Counterparty")) {
                        penalityBornee = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (penalityBorne.equalsIgnoreCase("anchor") || penalityBorne.equalsIgnoreCase("Anchor")) {
                        penalityBornee = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    String status = "P";
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                    java.util.Date date1 = new Date();
                    String CurrentDate = format.format(date1);
//                    String code = "VCPL" + cifId;
                    BigInteger CifId = cifId;
                    BigInteger SlId = cifId;
                    String ThirdPartyCode = null;
                    String code = null;
//                    String cif = String.valueOf(cifId);
//                    if(count>1){
//                        cif = String.valueOf(cifId)+programcode;
                    code = "VCPL" + cifId + programcode;
                    ThirdPartyCode = "A" + cifId + programcode;

//                    }else{
//                         code = "VCPL" + cifId;
//                         ThirdPartyCode = "A" + cifId;
//                    }

//                    String ThirdPartyCode = "A" + cif;
                    String cmpdInt = rs2.getString(5);
                    String cmpd = "";
                    if (cmpdInt.equalsIgnoreCase("No")) {
                        cmpd = "CMPD_INT_NO";
                    } else if (cmpdInt.equalsIgnoreCase("Yes")) {
                        cmpd = "CMPD_INT_YES";
                    }
                    String ovdInt = rs2.getString(4);
                    String cmpovd = "";
                    if (ovdInt.equalsIgnoreCase("No")) {
                        cmpovd = "CMPD_INT_NO";
                    } else if (ovdInt.equalsIgnoreCase("Yes")) {
                        cmpovd = "CMPD_INT_YES";
                    }
                    String tenor = rs2.getString(9); // DoorToDoor
                    String staleDays = String.valueOf(rs2.getDouble(19));  //Invoice Ageing

                LSASM_ACCT_CODE = "VCPL"+cifId + programcode;

                JSONObject obj1 = new JSONObject();
                obj1.put("accountCode", LSASM_ACCT_CODE);
                obj1.put("productCode", programcode);
                array.put(obj1);
                json.put("accountCodeList", array);

                    String save = String.format("INSERT INTO LOT_STG_ACCT_SETUP_MASTER (LSASM_CORPORATE_NAME,LSASM_CORPORATE_PROG_NAME,LSASM_SCF_PROG_TYPE,LSASM_PRODUCT_CODE,LSASM_FUNDING_CCY,LSASM_TRANSACTION_TYPE,LSASM_INVOICE_FUNDING_PER,LSASM_PROG_PAYMT_TERMS,LSASM_ORIGINAL_PYMT_TRM,LSASM_GRACE_DAYS,LSASM_DUE_DATE_CALCULATION,LSASM_CLIENT_FUNDING_LIMIT,LSASM_INTERIM_REVIEW_FREQUENCY,LSASM_NEXT_INTERIM_REVIEW_ON,LSASM_APPLICATION_OF_INTEREST,LSASM_INTEREST_BORNE_BY,LSASM_APLLICATION_FREQUENCY,LSASM_PENALITY_CHARGING_MATHODOLOGY,LSASM_PENALITY_BORNE_BY,LSASM_INTF_STATUS,LSASM_INTF_DATE,LSASM_ACCT_CODE,LSASM_CIF_ID,LSASM_SL_ID,THIRDPARTY_ACCT_CODE,CMPD_INT,CMPD_OVD_INT,DTD_TENOR,LSASM_STALE_DAYS)" +
                                    "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s','%s','%s','%s','%s','%s')", corporate, corporateProgram, programType1, programcode, fundingCode, transaction1, fundingPercentage, programPayment, originalPayment, graceDays, dateCalculation, fundingLimit, reviewFeq, reviewDate, application, interest, applicationFreq, penality, penalityBornee, status,
                            CurrentDate, code, CifId, SlId, ThirdPartyCode, cmpd, cmpovd, tenor, staleDays);
                    stTest.executeUpdate(save);
//                assert rs2.isClosed();
//                rs2.close();
//                assert st1.isClosed() : "Statement must be closed";
                    LOGGER.info("count********** "+count);
                    LOGGER.info("Anchor Details Saved in Triton DataBase");
//                }
            }
            ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_ACCT_SETUP_MASTER WHERE LSASM_CIF_ID = " + cifId);
            int count1 = 0;

            while (rs.next()) {
                count1++;
                int LSASM_ACCT_ID = rs.getInt(1);
                String LSASM_CORPORATE_NAME = rs.getString(2);
                String LSASM_CORPORATE_PROG_NAME = rs.getString(3);
                String LSASM_SCF_PROG_TYPE = rs.getString(6);
                String LSASM_PRODUCT_CODE = rs.getString(8);
                String LSASM_FUNDING_CCY = rs.getString(10);
                String LSASM_TRANSACTION_TYPE = rs.getString(11);
                String LSASM_INVOICE_FUNDING_PER = rs.getString(14);
                String LSASM_PROG_PAYMT_TERMS = rs.getString(15);
                String LSASM_ORIGINAL_PYMT_TRM = rs.getString(16);
                String LSASM_GRACE_DAYS = rs.getString(17);
                String LSASM_DUE_DATE_CALCULATION = rs.getString(18);
                String LSASM_CLIENT_FUNDING_LIMIT = rs.getString(20);
                String LSASM_INTERIM_REVIEW_FREQUENCY = rs.getString(24);
                String LSASM_NEXT_INTERIM_REVIEW_ON = rs.getString(25);
                String LSASM_APPLICATION_OF_INTEREST = rs.getString(32);
                String LSASM_INTEREST_BORNE_BY = rs.getString(33);
                String LSASM_APLLICATION_FREQUENCY = rs.getString(34);
                String LSASM_PENALITY_CHARGING_MATHODOLOGY = rs.getString(35);
                String LSASM_PENALITY_BORNE_BY = rs.getString(37);
                String LSASM_INTF_STATUS = rs.getString(41);
                String LSASM_INTF_DATE = rs.getString(42);
                LSASM_ACCT_CODE = rs.getString(44);
                long LSASM_CIF_ID = rs.getInt(45);
                String SlI = rs.getString(47);
                String ThdPartyCode = rs.getString(48);
                String CMPD_INT = rs.getString(49);
                String CMPD_OVD_INT = rs.getString(50);
                String DTD_TENOR = rs.getString(51);
                String LSASM_STALE_DAYS = rs.getString(52);
                String saveData = String.format("INSERT INTO LOT_STG_ACCT_SETUP_MASTER (LSASM_ACCT_ID,LSASM_CORPORATE_NAME," +
                                "LSASM_CORPORATE_PROG_NAME,LSASM_SCF_PROG_TYPE,LSASM_PRODUCT_CODE,LSASM_FUNDING_CCY,LSASM_TRANSACTION_TYPE," +
                                "LSASM_INVOICE_FUNDING_PER,LSASM_PROG_PAYMT_TERMS,LSASM_ORIGINAL_PYMT_TRM,LSASM_GRACE_DAYS,LSASM_DUE_DATE_CALCULATION," +
                                "LSASM_CLIENT_FUNDING_LIMIT,LSASM_INTERIM_REVIEW_FREQUENCY,LSASM_NEXT_INTERIM_REVIEW_ON," +
                                "LSASM_APPLICATION_OF_INTEREST,LSASM_INTEREST_BORNE_BY,LSASM_APLLICATION_FREQUENCY," +
                                "LSASM_PENALITY_CHARGING_MATHODOLOGY,LSASM_PENALITY_BORNE_BY,LSASM_INTF_STATUS," +
                                "LSASM_INTF_DATE,LSASM_ACCT_CODE,LSASM_CIF_ID,LSASM_SL_ID,THIRDPARTY_ACCT_CODE,CMPD_INT," +
                                "CMPD_OVD_INT,DTD_TENOR,LSASM_STALE_DAYS)" +
                                "VALUES (%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s','%s','%s','%s','%s','%s')",
                        LSASM_ACCT_ID, LSASM_CORPORATE_NAME, LSASM_CORPORATE_PROG_NAME, LSASM_SCF_PROG_TYPE, LSASM_PRODUCT_CODE,
                        LSASM_FUNDING_CCY, LSASM_TRANSACTION_TYPE, LSASM_INVOICE_FUNDING_PER, LSASM_PROG_PAYMT_TERMS, LSASM_ORIGINAL_PYMT_TRM, LSASM_GRACE_DAYS, LSASM_DUE_DATE_CALCULATION, LSASM_CLIENT_FUNDING_LIMIT,
                        LSASM_INTERIM_REVIEW_FREQUENCY, LSASM_NEXT_INTERIM_REVIEW_ON, LSASM_APPLICATION_OF_INTEREST, LSASM_INTEREST_BORNE_BY, LSASM_APLLICATION_FREQUENCY, LSASM_PENALITY_CHARGING_MATHODOLOGY, LSASM_PENALITY_BORNE_BY,
                        LSASM_INTF_STATUS, LSASM_INTF_DATE, LSASM_ACCT_CODE, LSASM_CIF_ID, SlI, ThdPartyCode, CMPD_INT, CMPD_OVD_INT, DTD_TENOR, LSASM_STALE_DAYS);
                st2.executeUpdate(saveData);
//                assert rs.isClosed();
//                rs.close();
//                assert st2.isClosed() : "Statement must be closed";
                LOGGER.info("******LSASM_ACCT_CODE*******" + LSASM_ACCT_CODE);
                LOGGER.info("Data Copied From Sql " + count1);
            }
//                return cifIdList;

            return json.toString();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (con != null){
                    con.close();}
                else if(con2 !=null) {
                con2.close();}
            }
                catch (SQLException ex){
                throw ex;
            }
            con.close();
            con2.close();
        }
        return null;
    }


    public  String anchorMaster () throws SQLException {
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Statement stmt = con.createStatement();
        try {
            int result = stmt.executeUpdate("INSERT INTO LOT_STG_ACCT_SETUP_MASTER (LSASM_CORPORATE_NAME,LSASM_CORPORATE_PROG_NAME,LSASM_SCF_PROG_TYPE,LSASM_PRODUCT_CODE,LSASM_FUNDING_CCY,LSASM_TRANSACTION_TYPE,LSASM_INVOICE_FUNDING_PER,LSASM_PROG_PAYMT_TERMS,LSASM_ORIGINAL_PYMT_TRM,LSASM_GRACE_DAYS,LSASM_CLIENT_FUNDING_LIMIT,LSASM_INTERIM_REVIEW_FREQUENCY,LSASM_NEXT_INTERIM_REVIEW_ON,LSASM_APPLICATION_OF_INTEREST,LSASM_INTEREST_BORNE_BY,LSASM_APLLICATION_FREQUENCY,LSASM_PENALITY_CHARGING_MATHODOLOGY,LSASM_PENALITY_BORNE_BY,LSASM_INTF_STATUS,LSASM_INTF_DATE,LSASM_INTF_ERROR,LSASM_CIF_ID,CMPD_INT,CMPD_OVD_INT,DTD_TENOR,LSASM_STALE_DAYS)" +
                    "                                                                   VALUES ('Amman Agency','Amman Agency','PROG_TYPE_VENDOR','REGPBD','INR','TRANSTYPE_RECOURSE','90','3','3','3','1000000000','2','01-05-24','DISCRATETY_FRONTENDED','INTREST_CHARGE_TO_ANCHOR','DISCFREQ_QUATERLY','PENAL_INT_CHARGE_PENAL_RATE','INTREST_CHARGE_TO_ANCHOR','P','01-07-22','VCPL0001',500000 ,'CMPD_INT_NO','CMPD_INT_NO','180','20')");
            if (result > 0) {
                con.close();
                return "Successfully inserted";
            } else {
                con.close();
                return "insertion Failed";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            stmt.close();
            con.close();
        }
        return null;
    }
}
