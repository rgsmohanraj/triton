package org.vcpl.triton.storeprocedure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.vcpl.triton.security.config.AWSDBConfiguration;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Service
public class CompanyBankService {

    @Autowired
    AWSDBConfiguration jdbcConfig;

    public String companyBankDetails() throws SQLException, ClassNotFoundException {

        Connection con = jdbcConfig.lmsDataSource().getConnection();
        ResultSet rs = con.prepareStatement("SELECT * FROM LOT_STG_COMP_BANK_DTL").executeQuery();
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append(rs.getInt(1) + "  " + rs.getString(2) + " ").append("\n");
        }
        return sb.toString();
    }

    public Map<String, Object> anchorCompanyBankDetails (BigInteger cifId,long id,String customerName) throws SQLException {

        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();

        Statement stmt = con.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM anchor_beneficiary WHERE app_id = "+id);
        if (rs1.next()) {
//            String bankId = "";
            String bankName = rs1.getString(10);
            String bankBranch = rs1.getString(8);
            String bankFullName = customerName ;
            String acctType = "BANK_ACCTY_CURRENT";
            String accountNo = rs1.getString(2);
            String currency = "INR";
            String status = "P";
            String saveAddress = String.format("Insert into LOT_STG_COMP_BANK_DTL (LSCBD_BANK_NAME,LSCBD_BANK_BRANCH,LSCBD_BANK_FULL_NAME,LSCBD_ACCT_TYP,LSCBD_ACCOUNT_NO,LSCBD_CURRENCY,LSCBD_INTF_STATUS,LSCBD_CIF_ID) " +
                    "Values ('%s','%s','%s','%s','%s','%s','%s',%d)", bankName, bankBranch, bankFullName, acctType, accountNo, currency, status, cifId);
            stmt.executeUpdate(saveAddress);
        }

            Statement st1 = con.createStatement();
            Statement st2 = con2.createStatement();
            ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_COMP_BANK_DTL WHERE LSCBD_CIF_ID = "+cifId);
            int count = 0;
            while (rs.next()) {
                count++;
                int bankId = rs.getInt(1);
                String bankName = rs.getString(2);
                String bankBranch = rs.getString(3);
                String bankFullName = rs.getString(4);
                String bankFullBranch = rs.getString(5);
                String acctType = rs.getString(6);
                String accNo = rs.getString(7);
                String paymentChannel = rs.getString(8);
                String currency = rs.getString(9);
                String paymentAcct = rs.getString(10);
                String payoutAcct = rs.getString(11);
                String bankRpt = rs.getString(12);
                String clearingCode = rs.getString(13);
                String swiftCode = rs.getString(14);
                String abaCode = rs.getString(15);
                String rtgsCode = rs.getString(16);
                String neftCode = rs.getString(17);
                String micrCode = rs.getString(18);
                String add1 = rs.getString(19);
                String add2 = rs.getString(20);
                String add3 = rs.getString(21);
                String status = rs.getString(22);
                String date = rs.getString(23);
                String error = rs.getString(24);
                int CifId = rs.getInt(25);
                String compCode = rs.getString(26);
                String sqlQuery = String.format("Insert into LOT_STG_COMP_BANK_DTL (LSCBD_COMP_BANK_ID,LSCBD_BANK_NAME,LSCBD_BANK_BRANCH,LSCBD_BANK_FULL_NAME,LSCBD_BANK_FULL_BRANCH,LSCBD_ACCT_TYP,LSCBD_ACCOUNT_NO,LSCBD_PAYMENT_CHANNEL,LSCBD_CURRENCY,LSCBD_PAYMENT_ACCT,LSCBD_CUSTOMER_PAYOUT_ACCT,LSCBD_AUTHORIZATION_BANK_RPT,LSCBD_CLEARING_CODE,LSCBD_SWIFT_CODE,LSCBD_ABA_CODE,LSCBD_RTGS_CODE,LSCBD_NEFT_CODE,LSCBD_MICR_CODE,LSCBD_ADD1,LSCBD_ADD2,LSCBD_ADD3,LSCBD_INTF_STATUS,LSCBD_INTF_DATE,LSCBD_INTF_ERROR,LSCBD_CIF_ID,LSCBD_COMP_CODE) Values (%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s')", bankId, bankName, bankBranch, bankFullName, bankFullBranch, acctType, accNo, paymentChannel, currency, paymentAcct, payoutAcct, bankRpt, clearingCode, swiftCode, abaCode, rtgsCode, neftCode, micrCode, add1, add2, add3, status, date, error, CifId, compCode);
                st2.executeUpdate(sqlQuery);
            }
            con.close();
            con2.close();
            return null;
    }

    public Map<String, Object> cpCompanyBankDetails (BigInteger cifId,long id,String customerName) throws SQLException {

        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_comp_mst_wrapper");

        Statement stmt = con.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM anchor_beneficiary WHERE app_id = "+id);
        if (rs1.next()) {
//            String bankId = "";
            String bankName = rs1.getString(10);
            String bankBranch = rs1.getString(8);
            String bankFullName = customerName ;
            String acctType = "BANK_ACCTY_CURRENT";
            String accountNo = rs1.getString(2);
            String currency = "INR";
            String status = "P";
            String saveAddress = String.format("Insert into LOT_STG_COMP_BANK_DTL (LSCBD_BANK_NAME,LSCBD_BANK_BRANCH,LSCBD_BANK_FULL_NAME,LSCBD_ACCT_TYP,LSCBD_ACCOUNT_NO,LSCBD_CURRENCY,LSCBD_INTF_STATUS,LSCBD_CIF_ID) " +
                    "Values ('%s','%s','%s','%s','%s','%s','%s',%d)", bankName, bankBranch, bankFullName, acctType, accountNo, currency, status, cifId);
            stmt.executeUpdate(saveAddress);
        }

        Statement st1 = con.createStatement();
        Statement st2 = con2.createStatement();
        ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_COMP_BANK_DTL WHERE LSCBD_CIF_ID = "+cifId);
        int count = 0;
        while (rs.next()) {
            count++;
            int bankId = rs.getInt(1);
            String bankName = rs.getString(2);
            String bankBranch = rs.getString(3);
            String bankFullName = rs.getString(4);
            String bankFullBranch = rs.getString(5);
            String acctType = rs.getString(6);
            String accNo = rs.getString(7);
            String paymentChannel = rs.getString(8);
            String currency = rs.getString(9);
            String paymentAcct = rs.getString(10);
            String payoutAcct = rs.getString(11);
            String bankRpt = rs.getString(12);
            String clearingCode = rs.getString(13);
            String swiftCode = rs.getString(14);
            String  abaCode = rs.getString(15);
            String  rtgsCode = rs.getString(16);
            String neftCode = rs.getString(17);
            String micrCode = rs.getString(18);
            String add1 = rs.getString(19);
            String add2 = rs.getString(20);
            String add3 = rs.getString(21);
            String status = rs.getString(22);
            String date = rs.getString(23);
            String error = rs.getString(24);
            int CifId = rs.getInt(25);
            String compCode = rs.getString(26);
            String sqlQuery = String.format("Insert into LOT_STG_COMP_BANK_DTL (LSCBD_COMP_BANK_ID,LSCBD_BANK_NAME,LSCBD_BANK_BRANCH,LSCBD_BANK_FULL_NAME,LSCBD_BANK_FULL_BRANCH,LSCBD_ACCT_TYP,LSCBD_ACCOUNT_NO,LSCBD_PAYMENT_CHANNEL,LSCBD_CURRENCY,LSCBD_PAYMENT_ACCT,LSCBD_CUSTOMER_PAYOUT_ACCT,LSCBD_AUTHORIZATION_BANK_RPT,LSCBD_CLEARING_CODE,LSCBD_SWIFT_CODE,LSCBD_ABA_CODE,LSCBD_RTGS_CODE,LSCBD_NEFT_CODE,LSCBD_MICR_CODE,LSCBD_ADD1,LSCBD_ADD2,LSCBD_ADD3,LSCBD_INTF_STATUS,LSCBD_INTF_DATE,LSCBD_INTF_ERROR,LSCBD_CIF_ID,LSCBD_COMP_CODE) Values (%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s')", bankId, bankName, bankBranch, bankFullName, bankFullBranch, acctType, accNo, paymentChannel, currency, paymentAcct, payoutAcct, bankRpt, clearingCode, swiftCode, abaCode, rtgsCode, neftCode, micrCode, add1, add2, add3, status, date, error, CifId, compCode);
            st2.executeUpdate(sqlQuery);

//                Map<String,Object> objectMap =  jdbcCall.execute();
//                return  objectMap;
        }
        con.close();
        con2.close();
        return null;
    }

    public String saveCompanyBank() throws ClassNotFoundException, SQLException {
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Statement stmt = con.createStatement();
        int result = stmt.executeUpdate("Insert into LOT_STG_COMP_BANK_DTL (LSCBD_BANK_NAME,LSCBD_BANK_BRANCH,LSCBD_BANK_FULL_NAME,LSCBD_BANK_FULL_BRANCH,LSCBD_ACCT_TYP,LSCBD_ACCOUNT_NO,LSCBD_PAYMENT_CHANNEL,LSCBD_CURRENCY,LSCBD_PAYMENT_ACCT,LSCBD_CUSTOMER_PAYOUT_ACCT,LSCBD_AUTHORIZATION_BANK_RPT,LSCBD_CLEARING_CODE,LSCBD_SWIFT_CODE,LSCBD_ABA_CODE,LSCBD_RTGS_CODE,LSCBD_NEFT_CODE,LSCBD_MICR_CODE,LSCBD_ADD1,LSCBD_ADD2,LSCBD_ADD3,LSCBD_INTF_STATUS,LSCBD_INTF_DATE,LSCBD_INTF_ERROR,LSCBD_CIF_ID,LSCBD_COMP_CODE) " +
                " values ('380','0159950','Sundaram Ltd',null,'BANK_ACCTY_CURRENT','897234628734',null,'INR',null,null,null,null,null,null,null,null,null,null,null,null,'P',null,null,'500012',null)");
    if (result > 0) {
            System.out.println("Successfully inserted");
        con.close();
            return "Successfully inserted";
        } else {
            System.out.println("insertion Failed");
        con.close();
            return "insertion Failed";
        }
    }


}
