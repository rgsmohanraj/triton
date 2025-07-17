package org.vcpl.triton.storeprocedure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.vcpl.triton.security.config.AWSDBConfiguration;

import java.math.BigInteger;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class CompanyAddressService {


    @Autowired
     private AWSDBConfiguration jdbcConfig;


    String url = "jdbc:oracle:thin:@172.27.0.73:1522:VCUATDB";
    String uname = "VIVRITI_DEV";
    String pass = "VIVRITI_DEV";

    public String getUser() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM LOT_STG_COMP_ADDR";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        int count = 0;
        String country = null;
        String state = null;
        String city = null;
        while (rs.next()) {
            count++;
            country = rs.getString("LSCA_COUNTRY");
            state = rs.getString("LSCA_STATE");
            city = rs.getString("LSCA_CITY");
        }
        st.close();
        con.close();
        return "Company Name from oracle Database " + country + state + city;
    }

    public String saveCompanyAddress(long id) throws ClassNotFoundException, SQLException, ParseException {

//        Connection con = jdbcConfig.tritonDataSource().getConnection();
//        Statement stmt = con.createStatement();
//        long cifId = id;
//        String save = String.format("INSERT INTO LOT_STG_COMP_ADDR " +
//                "               (LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
//                "               LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)" +
//                "               values('ADDR_HEAD','IND','TN','CHENNAI','P','01-02-2023',%d)",cifId);
//        stmt.executeUpdate(save);
//        return "Successfully inserted";
        try {
            LocalDate now = LocalDate.now();
//        String date = now.format(DateTimeFormatter.ofPattern("dd-mm-yy"));
            String oneYearAfter = now.plusYears(1).toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(oneYearAfter);
//            String str = formatter.format(oneYearAfter);
//            String date = oneYearAfter.format(DateTimeFormatter.ofPattern(formatter));


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> anchorCompanyAddress(BigInteger cifId,long id) throws ClassNotFoundException, SQLException {

        Connection con1 = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        try{
        Statement stmt1 = con1.createStatement();
            Statement stmt2 = con2.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM anchor_address WHERE app_id = "+id);
            if (rs1.next()) {
            String addType = "ADDR_HEAD";
            String country = rs1.getString(5);
            String state = rs1.getString(7);
            String city = rs1.getString(4);
            String Status = "P";
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
            Date date = new Date();
            String CurrentDate = format.format(date);
            BigInteger CifId = cifId;

            String save = String.format("INSERT INTO LOT_STG_COMP_ADDR (LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
                            "                  LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)values('%s','%s','%s','%s','%s','%s',%d)",
                    addType, country, state, city, Status, CurrentDate, CifId);
            stmt1.executeUpdate(save);
        }
            assert  rs1.isClosed() :"Result set must be closed implicitly";
            rs1.close();
            ResultSet rs = stmt1.executeQuery("SELECT * FROM LOT_STG_COMP_ADDR WHERE LSCA_CIF_ID = "+cifId);
            if (rs.next()) {
                int addId = rs.getInt(1);
                String type = rs.getString(2);
                String country = rs.getString(9);
                String state = rs.getString(10);
                String city = rs.getString(11);
                String status = rs.getString(19);
                String date = rs.getString(20);
                int cifId1 = rs.getInt(22);
                String save = String.format("INSERT INTO LOT_STG_COMP_ADDR " +
                        "               (LSCA_STG_ADDR_ID,LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
                        "               LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)" +
                        "               values(%d,'%s','%s','%s','%s','%s','%s',%d)",addId, type, country, state, city, status, date, cifId1);
                stmt2.executeUpdate(save);
                assert  rs1.isClosed() :"Result set must be closed implicitly";
                rs.close();
                assert stmt2.isClosed():"Statement must be closed";

            }
            return null;
        }catch (Exception e)
        {
           e.printStackTrace();
        }finally {
            con1.close();
        }
        return null;
    }


    public Map<String, Object> anchorMasterDetails(BigInteger cifId) throws SQLException {
        Connection con1 = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        Statement stmt1 = con1.createStatement();
        Statement stmt2 = con2.createStatement();
        ResultSet rs = stmt1.executeQuery("SELECT * FROM LOT_STG_COMP_ADDR WHERE LSCA_CIF_ID = "+cifId);
        while (rs.next()) {
            int addId = rs.getInt(1);
            String type = rs.getString(2);
            String country = rs.getString(9);
            String state = rs.getString(10);
            String city = rs.getString(11);
            String status = rs.getString(19);
            String date = rs.getString(20);
            int cifId1 = rs.getInt(22);
            String save = String.format("INSERT INTO LOT_STG_COMP_ADDR " +
                    "               (LSCA_STG_ADDR_ID,LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
                    "               LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)" +
                    "               values(%d,'%s','%s','%s','%s','%s','%s',%d)",addId, type, country, state, city, status, date, cifId1);
            stmt2.executeUpdate(save);
        }
        return null;
    }



    public Map<String, Object> cpCompanyAddress(BigInteger cifId,long id) throws ClassNotFoundException, SQLException {

//        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();
        JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc).withCatalogName("ksf_uls_intf_pkg").withProcedureName("sp_comp_mst_wrapper");

        try{
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con2.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM cp_basic_details WHERE app_id = "+id);
            int count1 = 0;
            if (rs1.next())
            {
                count1++;
                String addType = "ADDR_HEAD";
                String country = "IND";
                String state = rs1.getString(13);
                String city = rs1.getString(3);
                String Status = "P";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                Date date = new Date();
                String CurrentDate = format.format(date);

                String save = String.format("INSERT INTO LOT_STG_COMP_ADDR (LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
                                "                  LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)values('%s','%s','%s','%s','%s','%s',%d)",
                        addType,country,state,city,Status,CurrentDate,cifId);
                stmt1.executeUpdate(save);

            }
            ResultSet rs = stmt1.executeQuery("SELECT * FROM LOT_STG_COMP_ADDR WHERE LSCA_CIF_ID = "+cifId);
            int count = 0;
            while (rs.next()) {
                count++;
                int addId = rs.getInt(1);
                String type = rs.getString(2);
                String country = rs.getString(9);
                String state = rs.getString(10);
                String city = rs.getString(11);
                String status = rs.getString(19);
                String date = rs.getString(20);
                int cifId1 = rs.getInt(22);
                String save = String.format("INSERT INTO LOT_STG_COMP_ADDR " +
                        "               (LSCA_STG_ADDR_ID,LSCA_ADDR_TYP,LSCA_COUNTRY,LSCA_STATE," +
                        "               LSCA_CITY,LSCA_INTF_STATUS,LSCA_INTF_DATE,LSCA_CIF_ID)" +
                        "               values(%d,'%s','%s','%s','%s','%s','%s',%d)",addId, type, country, state, city, status, date, cifId1);
                stmt2.executeUpdate(save);

//                Map<String,Object> objectMap =  jdbcCall.execute();
//                return  objectMap;
            }
            con.close();
            con2.close();
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
