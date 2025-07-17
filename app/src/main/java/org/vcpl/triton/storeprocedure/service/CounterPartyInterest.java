package org.vcpl.triton.storeprocedure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Service
public class CounterPartyInterest {

    @Autowired
    private AWSDBConfiguration jdbcConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterPartyInterest.class);

    public Map<String, Object> counterPartyInterest(BigInteger cifId,String cpAcctCode, String productCode,
                                                    Long appId, Long cust_id, String productName) throws ClassNotFoundException, SQLException {

    Connection con = jdbcConfig.tritonDataSource().getConnection();
    Connection con2 = jdbcConfig.lmsDataSource().getConnection();
    JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
        Statement st2 = con2.createStatement();
        Statement st1 = con.createStatement();
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_comp_mst_wrapper");

//        ResultSet rs1 = st1.executeQuery("SELECT * FROM anchor_program WHERE app_id = ";

        ResultSet rs1 = st1.executeQuery("SELECT * FROM cp_term_sheet WHERE app_id = " + appId + " AND cust_id ="+cust_id+
                " AND product = '" + productName + "'");
        String floatingRate = "10";
        if (rs1.next()) {
            floatingRate = rs1.getString(7); //Interest rate
        }
        assert rs1.isClosed();
        rs1.close();
        Statement stmt = con.createStatement();
    //        long cifId = id;
        BigInteger id = cifId;
        String ratetype = "RATETYPE_FIXED";
//        String floatingRate = "10" ;
        String stus = "P";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Date curtdate = new Date();
        String CurrentDate = format.format(curtdate);
        String tpsdcode = cpAcctCode;
        String tpcode = "TP-"+cifId+productCode;
    String saveAddress = String.format("INSERT INTO LOT_STG_TP_INTEREST_DTL " +
            "               (LSTCD_RATE_TYPE,LSTCD_ACTUAL_FLOATING_RATE,LSTID_INTF_STATUS,LSTID_INTF_DATE,LSTCD_TPSD_CODE,LSTCD_TP_CODE)" +
            "               values('%s','%s','%s','%s','%s','%s')", ratetype, floatingRate, stus, CurrentDate, tpsdcode, tpcode);
        stmt.executeUpdate(saveAddress);
        try {
        ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_TP_INTEREST_DTL WHERE LSTCD_TP_CODE = '" + tpcode + "'");
        int count = 0;
        while (rs.next()) {
            count++;
            int cpId = rs.getInt(1);
            String rateType = rs.getString(2);
            String actualFloating = rs.getString(11);
            String status = rs.getString(12);
            String date = rs.getString(13);
            String code = rs.getString(17);
            String tpCode = rs.getString(16);

            String save = String.format("INSERT INTO LOT_STG_TP_INTEREST_DTL (LSTCD_TPID,LSTCD_RATE_TYPE,LSTCD_ACTUAL_FLOATING_RATE,LSTID_INTF_STATUS,LSTID_INTF_DATE,LSTCD_TPSD_CODE,LSTCD_TP_CODE)"+
                            "               values(%d,'%s','%s','%s','%s','%s','%s')", cpId, rateType, actualFloating, status, date, code, tpCode);
            st2.executeUpdate(save);
            LOGGER.info("Data Copied From Sql 2.CP Interest" + count);


//                Map<String,Object> objectMap =  jdbcCall.execute();
//                return  objectMap;
        }
    }catch (Exception e)
    {
        e.printStackTrace();
    }finally {
        con.close();
        con2.close();
    }
        return null;
}
}
