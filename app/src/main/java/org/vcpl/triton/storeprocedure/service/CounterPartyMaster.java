package org.vcpl.triton.storeprocedure.service;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.security.config.AWSDBConfiguration;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CounterPartyMaster {


    @Autowired
    private AWSDBConfiguration jdbcConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterPartyMaster.class);

    public String saveCpMasterDetails(BigInteger cifId, Long id, String companyName, Long cust_id, String productName) throws ClassNotFoundException, SQLException {
        try {
// Connection con = jdbcConfig.tritonDataSource().getConnection();
            Connection con = jdbcConfig.tritonDataSource().getConnection();
            Connection con2 = jdbcConfig.lmsDataSource().getConnection();

            Statement st2 = con2.createStatement();
            Statement st1 = con.createStatement();
            Statement stmt = con.createStatement();
// ResultSet rs2 = st1.executeQuery("SELECT A.id,C.vcpl_code FROM customer_info I,anchor_code C,application_details_info A WHERE A.id In (SELECT app_id FROM anchor_code WHERE app_id IN (SELECT id FROM application_details_info WHERE cust_id = " + cust_id + "))group by id");
            ResultSet rs2 = st1.executeQuery("SELECT vcpl_code, product_code, app_id FROM anchor_code WHERE app_id IN ( SELECT id FROM application_details_info WHERE cust_id="+cust_id+");");
            String AcctCode = "";
            String AccountCode = "";
            String fundinglimit = null;
            String Payment = null;
            String cmpdInt = null;
            String cmpdOvdInt = null;
            String facilityName = null;
            String beneficiaryPayout = null;
            String repymentFrom = null;
            String interestBorne = null;
            String penaltyBorne = null;
//            ResultSet rs4 = st1.executeQuery("SELECT * FROM cp_proposed_products WHERE app_id = " + id + " AND cust_id ="+cust_id);
//            String facName = null;
//            if (rs4.next()) {
//                facName = rs4.getString(4).trim(); //subProduct
// facName = "Anchor Sales Bill Discounting";
//                assert rs4.isClosed();
//                rs4.close();
//            }

//        ResultSet rs4 = st1.executeQuery("SELECT * FROM cp_proposed_products WHERE app_id = " + id);
//        String facName = null;
//        if (rs4.next()) {
//            facName = rs4.getString(4).trim(); //subProduct
// facName = "Anchor Sales Bill Discounting";
//            assert rs4.isClosed();
//            rs4.close();
//        }
            ResultSet rs5 = st1.executeQuery("SELECT * FROM cp_term_sheet WHERE app_id = " + id + " AND cust_id ="+cust_id+
                    " AND product = '" + productName + "'");
            if (rs5.next()) {
// String interestBorn = rs5.getString(8);
                fundinglimit = rs5.getString(13);//Regular Limit
                String interestBorn = rs5.getString(22);
// String penaltyBorn = rs5.getString(14);
                String penaltyBorn = rs5.getString(24);
                LOGGER.info("productName ---- "+productName);
                if (productName.equalsIgnoreCase("Anchor Sales Bill Discounting")) {
                    facilityName = "ANCSBD";
                    beneficiaryPayout = "REPAY_FROM_ANCHOR";
// repymentFrom = "REPAY_FROM_VENDOR_DEALER";
                    repymentFrom = "REPAY_FROM_VENDOR_DEALER";
                    interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                } else if (productName.equalsIgnoreCase("Anchor Purchase Bill Discounting")) {
                    facilityName = "ANCPBD";
                    beneficiaryPayout = "REPAY_FROM_VENDOR_DEALER";
                    repymentFrom = "REPAY_FROM_ANCHOR";
                    interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                } else if (productName.equalsIgnoreCase("Dealer Purchase Order Finance")) {
                    facilityName = "REGPIF";
                    beneficiaryPayout = "REPAY_FROM_ANCHOR";
                    repymentFrom = "REPAY_FROM_VENDOR_DEALER";
                    if (interestBorn.equalsIgnoreCase("Counterparty")) {
                        interestBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (interestBorn.equalsIgnoreCase("anchor")) {
                        interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    if (penaltyBorn.equalsIgnoreCase("Counterparty")) {
                        penaltyBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (penaltyBorn.equalsIgnoreCase("anchor")) {
                        penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                } else if (productName.equalsIgnoreCase("Dealer Invoice Finance")) {
                    facilityName = "REGPBD";
                    beneficiaryPayout = "REPAY_FROM_ANCHOR";
                    repymentFrom = "REPAY_FROM_VENDOR_DEALER";
                    if (interestBorn.equalsIgnoreCase("Counterparty")) {
                        interestBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (interestBorn.equalsIgnoreCase("anchor")) {
                        interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    if (penaltyBorn.equalsIgnoreCase("Counterparty")) {
                        penaltyBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (penaltyBorn.equalsIgnoreCase("anchor")) {
                        penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                } else if (productName.equalsIgnoreCase("Vendor Purchase Order Finance")) {
                    facilityName = "DF";
                    beneficiaryPayout = "REPAY_FROM_VENDOR_DEALER";
                    repymentFrom = "REPAY_FROM_ANCHOR";
                    if (interestBorn.equalsIgnoreCase("Counterparty")) {
                        interestBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (interestBorn.equalsIgnoreCase("anchor")) {
                        interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    if (penaltyBorn.equalsIgnoreCase("Counterparty")) {
                        penaltyBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (penaltyBorn.equalsIgnoreCase("anchor")) {
                        penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }

                } else if (productName.equalsIgnoreCase("Vendor Invoice Finance")) {
                    facilityName = "VF";
                    beneficiaryPayout = "REPAY_FROM_VENDOR_DEALER";
                    repymentFrom = "REPAY_FROM_ANCHOR";
                    if (interestBorn.equalsIgnoreCase("Counterparty")) {
                        interestBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (interestBorn.equalsIgnoreCase("anchor")) {
                        interestBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                    if (penaltyBorn.equalsIgnoreCase("Counterparty")) {
                        penaltyBorne = "INTREST_CHARGE_TO_VENDOR_OR_DEALER";
                    } else if (penaltyBorn.equalsIgnoreCase("anchor")) {
                        penaltyBorne = "INTREST_CHARGE_TO_ANCHOR";
                    }
                }
                LOGGER.info("facilityName ---- "+facilityName);
                Long anchorAppId = null;
                String productCode = "";
                while (rs2.next()) {
                    try {
                        AcctCode = rs2.getString(1);
                        productCode = rs2.getString(2);
                        anchorAppId = rs2.getLong(3);
                        LOGGER.info("AcctCode --- " + AcctCode);
                        LOGGER.info("productCode --- " + productCode);
                        if (facilityName.equals(productCode)) {
                            AccountCode = AcctCode;
                            break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        assert rs2.isClosed();
                        rs2.close();
                    }
                }
                LOGGER.info("AccountCode ---- "+AccountCode);
// String graceDay = "3";
// String graceDay = rs5.getString(7);//jdbc
                String graceDay = rs5.getString(21);
                String grace = rs5.getString(21); //Grace Period->TermSheet
// String tenor = rs5.getString(6);
                String tenor = rs5.getString(6);//door to door
// String invoiceFunding = rs5.getString(12);
                String invoiceFunding = rs5.getString(23);//invoice_funding
// String applInterest = "DISCRATETY_REARENDED"; //Apply interest-TermSheet
                String applInterest1 = rs5.getString(20); //applInterest
                String applInterest = null;
                if (applInterest1.equalsIgnoreCase("In-Arrears")) {
                    applInterest = "DISCRATETY_FRONTENDED";
                } else if (applInterest1.equalsIgnoreCase("Upfront")) {
                    applInterest = "DISCRATETY_REARENDED";
                }
// String invoiceFunding = "100"; //TermSheet

// String tenor = "120";//door to door
// String LSTSD_MINIMUM_INTEREST_APPL = "10";
                assert rs5.isClosed();
                rs5.close();


//                ResultSet rs3 = st1.executeQuery("SELECT * FROM cp_limit_eligibility_details WHERE app_id = " + id
//                + " AND product = '" + productName + "'");
//                if (rs3.next()) {
//                    String cmpd = rs3.getString(14);
//// String cmpd = "Yes";
//                    if (cmpd.equalsIgnoreCase("Yes")) {
//                        cmpdInt = "CMPD_INT_YES";
//                    } else if (cmpd.equalsIgnoreCase("No")) {
//                        cmpdInt = "CMPD_INT_NO";
//                    }
//                    String cmpdOvd = rs3.getString(15);
//// String cmpdOvd = "Yes";
//                    if (cmpdOvd.equalsIgnoreCase("Yes")) {
//                        cmpdOvdInt = "CMPD_INT_YES";
//                    } else if (cmpdOvd.equalsIgnoreCase("No")) {
//                        cmpdOvdInt = "CMPD_INT_NO";
//                    }
//                    fundinglimit = rs3.getString(20);//Proposed Limit
// fundinglimit = "10000000";
//                    Payment = rs3.getString(12); //Tenure
// Payment = "120";
//                    assert rs3.isClosed();
//                    rs3.close();
//                }
                cmpdInt = "CMPD_INT_YES";
                cmpdOvdInt = "CMPD_INT_NO";

                ResultSet rs6 = st1.executeQuery("SELECT * FROM anchor_program WHERE app_id = " + anchorAppId +
                        " AND sub_product = '" + productName + "'");
                while (rs6.next()) {
                    try {
                        Payment = String.valueOf(rs6.getDouble(36)); //Tenure
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        assert rs6.isClosed();
                        rs6.close();
                    }
                }
// String interestBorne = "INTREST_CHARGE_TO_ANCHOR"; //TermSheet
// String penaltyBorne = "INTREST_CHARGE_TO_ANCHOR"; //TermSheet
// String repymentFrom = "REPAY_FROM_ANCHOR"; //TermSheet
// String beneficiaryPayout = "REPAY_FROM_ANCHOR"; //OpsMaker

// -------------------Default Values-----------
                BigInteger cpid = cifId;
                String corporate = companyName;
                String corporateName = corporate + "_corporate_name";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                Date date = new Date();
                String CurrentDate = format.format(date);
                String reqtAllow = "1";
                String Supply = "0";
                String fundingCcy = "INR";
                String invoiceFundingCcy = "INR";
                String applFeq = "DISCFREQ_MONTHLY";
                String penalty = "PENAL_INT_CHARGE_INT_RATE_PEN";
                String repyment = "PROCESS_TYPE_AUTO";
                String tpCode = "TP-" + cifId + facilityName;
                String status = "P";
                String recovery = "PROCESS_TYPE_MANUAL";
                String thirdCode = "C" + cifId + facilityName;
                String acctCode = "CP" + cifId + facilityName;
                String LSTSD_DUE_DATE_CAL = "CALC_DUE_DT_FROM_FROM_INV_DATE";

                String saveAddress = String.format("INSERT INTO LOT_STG_TP_SETUP_DTL " +
                                " (LSTSD_CORPORATE_NAME,LSTSD_CORPORATE_PROGRAM_NAME," +
                                " LSTSD_SCF_FACILITY_NAME,LSTSD_FUNDING_LIMIT,LSTSD_FUNDING_CCY,LSTSD_GRACE_PRD_FOR_BLK_LIMIT," +
                                " LSTSD_INVOICE_FUNDING_CCY," +
                                " LSTSD_PAYMENT_TERMS,LSTSD_GRACE_DAYS,LSTSD_STOP_SUPPLY_INDCATOR,LSTSD_APPL_OF_INTEREST,LSTSD_APPL_FREQUENCY," +
                                " LSTSD_INTEREST_BORNE_BY,LSTSD_PENALTY_CHARGING_METH,LSTSD_PENALTY_BORNE_BY, " +
                                " LSTSD_REPYMNT_OPTION,LSTSD_REPYMNT_FROM,LSTSD_RECOVERY_OF_OVERDUES,LSTSD_DISBMT_REQT_ALLOWED,LSTSD_BENEFICIARY_PAYOUT_TO,LSTSD_ACCT_CODE,LSTSD_TP_CODE,LSTSD_INTF_STATUS,LSTSD_INTF_DATE,LSTSD_INVOICE_FUNDING,THIRDPARTY_ACCT_CODE,CMPD_INT,CMPD_OVD_INT,DTD_TENOR,LSTCD_CPRTY_ACCT_CODE,CIF_ID)" +
                                " values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d')",
                        corporate, corporateName, facilityName, fundinglimit, fundingCcy, grace, invoiceFundingCcy, Payment, graceDay, Supply, applInterest, applFeq,
                        interestBorne, penalty, penaltyBorne, repyment, repymentFrom, recovery, reqtAllow, beneficiaryPayout, AccountCode, tpCode, status, CurrentDate, invoiceFunding, thirdCode, cmpdInt, cmpdOvdInt, tenor, acctCode, cpid);
                stmt.executeUpdate(saveAddress);
                LOGGER.info("******Saved Successfully*****" + AccountCode);
            }

            ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_TP_SETUP_DTL WHERE CIF_ID = " + cifId + " AND LSTSD_ACCT_CODE = '" + AccountCode + "'");
            int count = 0;
            JSONObject obj = new JSONObject();
            JSONArray array = new JSONArray();
            while (rs.next()) {
//                String acctCode = "VCPL" + cifId;
                count++;
                int cpId = rs.getInt(1);
                String cpName = rs.getString(2);
                String cpNameProgram = rs.getString(3);
                String facilityname = rs.getString(4);
                String fundingLimit = rs.getString(7);
                String fundingCcy = rs.getString(8);
                String gracePrd = rs.getString(13);
                String invoiceFunding = rs.getString(14);
                String paymentTerms = rs.getString(16);
                String graceDays = rs.getString(18);
                String dueDate = rs.getString(19);
                String supply = rs.getString(24);
                String applyInt = rs.getString(32);
                String applyFeq = rs.getString(33);
                String intBorne = rs.getString(34);
                String interest = rs.getString(35);
                String penality = rs.getString(36);
                String penalityBorne = rs.getString(37);
                String RepaymentOption = rs.getString(40);
                String RepaymentForm = rs.getString(41);
                String Recovery = rs.getString(44);
                String reqtAllowed = rs.getString(45);
                String beneficiary = rs.getString(47);
                String acctCode = rs.getString(52);
                String tpCode = rs.getString(53);
                String status = rs.getString(54);
                String date = rs.getString(55);
                String invoiceFundings = rs.getString(57);
                String thirdParty = rs.getString(58);
                String cmpd = rs.getString(59);
                String cmpdOvd = rs.getString(60);
                String tenor1 = rs.getString(61);
                String cpAcctCode = rs.getString(62);
                obj.put("acctCode", acctCode);
                obj.put("productCode", facilityName);
                array.put(obj);
                JSONObject obj2 = new JSONObject();
                int cifId1 = rs.getInt(64);
                String save = String.format("INSERT INTO LOT_STG_TP_SETUP_DTL " +
                                " (LSTSD_TPSDID,LSTSD_CORPORATE_NAME,LSTSD_CORPORATE_PROGRAM_NAME," +
                                " LSTSD_SCF_FACILITY_NAME,LSTSD_FUNDING_LIMIT,LSTSD_FUNDING_CCY,LSTSD_GRACE_PRD_FOR_BLK_LIMIT,LSTSD_INVOICE_FUNDING_CCY," +
                                " LSTSD_PAYMENT_TERMS,LSTSD_GRACE_DAYS,LSTSD_DUE_DATE_CAL,LSTSD_STOP_SUPPLY_INDCATOR,LSTSD_APPL_OF_INTEREST,LSTSD_APPL_FREQUENCY,LSTSD_INTEREST_BORNE_BY,LSTSD_MINIMUM_INTEREST_APPL,LSTSD_PENALTY_CHARGING_METH,LSTSD_PENALTY_BORNE_BY," +
                                " LSTSD_REPYMNT_OPTION,LSTSD_REPYMNT_FROM,LSTSD_RECOVERY_OF_OVERDUES,LSTSD_DISBMT_REQT_ALLOWED,LSTSD_BENEFICIARY_PAYOUT_TO,LSTSD_ACCT_CODE,LSTSD_TP_CODE,LSTSD_INTF_STATUS,LSTSD_INTF_DATE,LSTSD_INVOICE_FUNDING,THIRDPARTY_ACCT_CODE,CMPD_INT,CMPD_OVD_INT,DTD_TENOR,LSTCD_CPRTY_ACCT_CODE,CIF_ID)" +
                                " values('%d','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d')", cpId, cpName, cpNameProgram, facilityname, fundingLimit, fundingCcy, gracePrd, invoiceFunding, paymentTerms, graceDays, dueDate, supply, applyInt, applyFeq,
                        intBorne, interest, penality, penalityBorne, RepaymentOption, RepaymentForm, Recovery, reqtAllowed, beneficiary, acctCode, tpCode, status, date,
                        invoiceFundings, thirdParty, cmpd, cmpdOvd, tenor1, cpAcctCode,cifId1);
                st2.executeUpdate(save);
                LOGGER.info("Data Copied From Sql 1.Cp-Details Master" + count);
                obj2.put("cpCodesList", array);
                return obj2.toString();
// Map<String,Object> objectMap = jdbcCall.execute();
// return objectMap;
            }
            con.close();
            con2.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}