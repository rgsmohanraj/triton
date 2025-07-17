package org.vcpl.triton.storeprocedure.service;


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
import java.util.Map;

@Service
public class AnchorInterestService {

    @Autowired
    private AWSDBConfiguration jdbcConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnchorInterestService.class);


    public  String anchorInterest() throws SQLException {
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Statement stmt = con.createStatement();
        int result = stmt.executeUpdate("INSERT INTO LOT_STG_ACCT_INTEREST_DTL (LSAID_RATE_TYPE,LSAID_PENALITY_SPREAD,LSAID_ACTUAL_FLOATING_RATE,LSAID_INTF_STATUS,LSAID_INTF_DATE,LSAID_ACCT_CODE) Values ('RATETYPE_FIXED','100','10','P','03-FEB-23','S062302000000118')");
        if (result > 0) {
            LOGGER.info("Successfully inserted data into LOT_STG_ACCT_INTEREST_DTL");
            con.close();
            return "Successfully inserted";
        } else {
            LOGGER.info("insertion Failed data in LOT_STG_ACCT_INTEREST_DTL");
            con.close();
            return "insertion Failed";
        }
    }

    public Map<String ,Object> saveAnchorInterest(BigInteger cifId,long id,String accountCode, String productCode) throws SQLException {

        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        Statement st2 = con2.createStatement();
        Statement stTest = con.createStatement();
        Statement st1 = con.createStatement();
        Statement st3 = con.createStatement();

        String programcode="";
        if (productCode.equalsIgnoreCase("ANCSBD")) {
            programcode = "Anchor Sales Bill Discounting";
        } else if (productCode.equalsIgnoreCase("ANCPBD")) {
            programcode = "Anchor Purchase Bill Discounting";
        } else if (productCode.equalsIgnoreCase("REGPIF")){
            programcode = "Dealer Purchase Order Finance";
        }else if (productCode.equalsIgnoreCase("REGPBD")){
            programcode = "Dealer Invoice Finance";
        }else if (productCode.equalsIgnoreCase("DF")){
            programcode = "Vendor Purchase Order Finance";
        }else if (productCode.equalsIgnoreCase("VF")){
            programcode = "Vendor Invoice Finance";
        }
        try {
            String sqlquery= "SELECT * FROM anchor_program WHERE app_id = "+id +
                    " AND sub_product = '"+programcode+"'";
            ResultSet rs1 = st1.executeQuery("SELECT * FROM anchor_program WHERE app_id = "+id +
                    " AND sub_product = '"+programcode+"'");
            int count = 0;
            while (rs1.next()) {
                count++;
                String rateType = "RATETYPE_FIXED";
                String penalitySpread = rs1.getString(23);//Rate_penal_interest
                String floatingRate = rs1.getString(17);//interest_rate
                String status = "P";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                Date date = new Date();
                String currentDate = format.format(date);
                String acctCode = accountCode;

                String save = String.format("INSERT INTO LOT_STG_ACCT_INTEREST_DTL (LSAID_RATE_TYPE,LSAID_PENALITY_SPREAD,LSAID_ACTUAL_FLOATING_RATE,LSAID_INTF_STATUS,LSAID_INTF_DATE,LSAID_ACCT_CODE) " +
                        "                 VALUES ('%S','%S','%S','%S','%S','%S')", rateType, penalitySpread, floatingRate, status, currentDate, acctCode);
                stTest.executeUpdate(save);
//                assert  rs1.isClosed();
//                rs1.close();
//                assert st1.isClosed():"Statement must be closed";
                LOGGER.info("Interest Details Saved in Triton DataBase");
            }
            String sql = String.format(("SELECT * FROM LOT_STG_ACCT_INTEREST_DTL WHERE LSAID_ACCT_CODE = '%s'"), accountCode);
            ResultSet rs2 = st1.executeQuery(sql);
            int count1 = 0;
            while (rs2.next()) {
                count1++;
                int instId = rs2.getInt(1);
                String RateType = rs2.getString(2);
                String Penality = rs2.getString(5);
                String Rate = rs2.getString(11);
                String Status = rs2.getString(12);
                String Date = rs2.getString(13);
                String Code = rs2.getString(15);
                String Save = String.format("INSERT INTO LOT_STG_ACCT_INTEREST_DTL (LSAID_AID,LSAID_RATE_TYPE,LSAID_PENALITY_SPREAD,LSAID_ACTUAL_FLOATING_RATE,LSAID_INTF_STATUS,LSAID_INTF_DATE,LSAID_ACCT_CODE)" +
                                "VALUES (%d,'%s','%s','%s','%s','%s','%s')",
                        instId, RateType, Penality, Rate, Status, Date, Code);
                st2.executeUpdate(Save);
//                assert  rs.isClosed();
//                rs.close();
//                assert st2.isClosed():"Statement must be closed";
                LOGGER.info("Interest Details Saved in Oracle DataBase");
            }
        }catch (Exception E){
            E.printStackTrace();
        }finally {
            con.close();
            con2.close();
        }
        return null;
    }


}
