package org.vcpl.triton.storeprocedure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.vcpl.triton.security.config.AWSDBConfiguration;

import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CompanyMasterService {

    @Autowired
    AWSDBConfiguration jdbcConfig;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String getCompanyMasterDetails() throws SQLException, ClassNotFoundException {

        Connection con = jdbcConfig.lmsDataSource().getConnection();
        ResultSet rs = con.prepareStatement("SELECT * FROM LOT_STG_COMP_MASTER").executeQuery();
        StringBuilder sb = new StringBuilder();
        while (rs.next()){
            sb.append(rs.getInt(1)+"  "+rs.getString(2)+ " ").append("\n");
        }
        return  sb.toString();

//        String query = "SELECT * FROM LOT_STG_COMP_MASTER";
//        Class.forName("oracle.jdbc.driver.OracleDriver");
//        Connection con = DriverManager.getConnection(url, uname, pass);
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery(query);
//        int count = 0;
//        String name = null;
//        while (rs.next()) {
//            count++;
//            name = rs.getString("LSTCM_COMP_NAME");
//        }
//        st.close();
//        con.close();
//        return "Company Name from oracle Database " + name;
    }

    public String anchorCompanyMasterDetails(BigInteger cifId) throws SQLException {
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Connection con2 = jdbcConfig.lmsDataSource().getConnection();

        try {
            Statement st1 = con.createStatement();
            Statement st2 = con2.createStatement();
            ResultSet rs = st1.executeQuery("SELECT * FROM LOT_STG_COMP_MASTER WHERE LSTCM_CIF_ID = "+cifId);
            int count = 0;
            while (rs.next()) {
                count++;
                long masterId = rs.getInt(1);
                String status = rs.getString(26);
                String compName = rs.getString(2);
                String compNameLocal = rs.getString(5);
                String compShortName = rs.getString(3);
                String country = rs.getString(6);
                String date = rs.getString(27);
                String kyc = rs.getString(8);
                String shortNameLocal = rs.getString(5);
                String sqlQuery = String.format("INSERT INTO LOT_STG_COMP_MASTER (LSTCM_CIF_ID,LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL, LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) values (%d,'%s','%s','%s','%s','%s','%s','%s','%s')", masterId, compName, compShortName, compNameLocal, shortNameLocal, country, kyc, status, date);
                st2.executeUpdate(sqlQuery);
                return compName;
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


//
//    public Map<String, Object> anchorCompanyMaster(long id) throws SQLException {
//        Connection con = jdbcConfig.tritonDataSource().getConnection();
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT C.customer_name FROM customer_info C,application_details_info A WHERE C.id = A.cust_id AND A.id =" + id);
//        String sql = "INSERT INTO LOT_STG_COMP_MASTER" + " (LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL," +
//                "                                     LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s')";
//        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement(sql,
//                        Statement.RETURN_GENERATED_KEYS);
//                String customerName = rs.getString(1);
//                String country = "IND";
//                String kyc = "yes";
//                String status = "P";
//                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
//                Date date = new Date();
//                String CurrentDate = format.format(date);
//                ps.setString(1,customerName);
//                ps.setString(2,customerName);
//                ps.setString(3,customerName);
//                ps.setString(4,customerName);
//                ps.setString(5,country);
//                ps.setString(6,kyc);
//                ps.setString(7,status);
//                ps.setString(8,CurrentDate);
//                return ps;
//            }
//        },holder);
//        int generatedUserId = holder.getKey().intValue();
//        return null;
//    }


//        public Object anchorCompanyMasters(long id) throws ClassNotFoundException, SQLException {
//
//        Connection con = jdbcConfig.tritonDataSource().getConnection();
//        Statement stmt = con.createStatement();
//        try {
//            ResultSet rs = stmt.executeQuery("SELECT C.customer_name FROM customer_info C,application_details_info A WHERE C.id = A.cust_id AND A.id =" + id);
//            int count = 0;
//            if (rs.next()) {
//                count++;
//                String customerName = rs.getString(1);
//                String country = "IND";
//                String kyc = "yes";
//                String status = "P";
//                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
//                Date date = new Date();
//                String CurrentDate = format.format(date);
//                String sqlQuery = String.format(("INSERT INTO LOT_STG_COMP_MASTER " +
//                        "                (LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL, " +
//                        "                LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) " +
//                        "                values('%s','%s','%s','%s','%s','%s','%s','%s')"), customerName, customerName, customerName, customerName, country, kyc, status, CurrentDate);
//                stmt.executeUpdate(sqlQuery);
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            con.close();
//        }
//        return null;
//    }


    public BigInteger anchorCompanyMaster(long id) throws SQLException {
        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Statement stmt = con.createStatement();
        String INSERT_MESSAGE_SQL = "INSERT INTO LOT_STG_COMP_MASTER (LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL," +
                "                   LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) values(?,?,?,?,?,?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection-> {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.executeQuery("SELECT C.customer_name FROM customer_info C,application_details_info A WHERE C.id = A.cust_id AND A.id =" + id);
            int count = 0;
            if (rs.next()) {
                count++;
                String customerName = rs.getString(1);
                String country = "IND";
                String kyc = "yes";
                String status = "P";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                Date date = new Date();
                String CurrentDate = format.format(date);
                ps.setString(1, customerName);
                ps.setString(2, customerName);
                ps.setString(3, customerName);
                ps.setString(4, customerName);
                ps.setString(5, country);
                ps.setString(6, kyc);
                ps.setString(7, status);
                ps.setString(8,CurrentDate);
            }
            return ps;
        }, keyHolder);
        return (BigInteger) keyHolder.getKey();
    }





    public BigInteger cpCompanyMasters(long id) throws ClassNotFoundException, SQLException {

        Connection con = jdbcConfig.tritonDataSource().getConnection();
        Statement stmt = con.createStatement();
        try{
        String INSERT_MESSAGE_SQL = "INSERT INTO LOT_STG_COMP_MASTER " +
                "                                   (LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL, " +
                "                                   LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) " +
                "                                    values(?,?,?,?,?,?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection-> {
          PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL,Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.executeQuery("SELECT company_name FROM cp_basic_details  WHERE app_id = " + id);
            int count = 0;
            if (rs.next()) {
                count++;
                String customerName = rs.getString(1);
//                String customerName = "Continental Tyres Company";
                String country = "IND";
                String kyc = "Yes";
                String status = "P";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                Date date = new Date();
                String CurrentDate = format.format(date);
                ps.setString(1, customerName);
                ps.setString(2, customerName);
                ps.setString(3, customerName);
                ps.setString(4, customerName);
                ps.setString(5, country);
                ps.setString(6, kyc);
                ps.setString(7, status);
                ps.setString(8, CurrentDate);
            }
            return ps;
        }, keyHolder);
    return (BigInteger) keyHolder.getKey();

//            ResultSet resultSet = stmt.executeQuery("SELECT * FROM LOT_STG_COMP_MASTER WHERE LSTCM_COMP_NAME = " +customerName);
//            while (resultSet.next()) {
//                int masterId = resultSet.getInt(1);
//                return result;
//            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.close();
        }

        return null;
    }



//    public String saveCompanyMasterData(CompanyMasterData companyMasterData) throws ClassNotFoundException, SQLException {
//        Class.forName("oracle.jdbc.driver.OracleDriver");
//        Connection connection = DriverManager.getConnection(url, uname, pass);
//        Statement stmt = connection.createStatement();
//        int id = companyMasterData.getCifId();
//        String compName = companyMasterData.getCompName() ;
//        String compShotName = companyMasterData.getCompShortName();
//        String compNameLocal = companyMasterData.getCompNameLocal();
//        String shotNameLocal = companyMasterData.getShortNameLocal();
//        String country = companyMasterData.getCountry();
//        String kyc = companyMasterData.getKyc();
//        String status = companyMasterData.getIntfStatus();
//        String date = companyMasterData.getIntfDate();
//        String sqlQuery = String.format("INSERT INTO LOT_STG_COMP_MASTER (LSTCM_CIF_ID,LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL, LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) values (%d,'%s','%s','%s','%s','%s','%s','%s','%s')",id,compName,compShotName,compNameLocal,shotNameLocal,country,kyc,status,date);
//        stmt.executeUpdate(sqlQuery);
//        return  "insertion Successfully";
//        }
//
//
//
//    public String saveCompanyMaster(CompanyMasterData companyMasterData) {
//        String sql = "INSERT INTO LOT_STG_COMP_MASTER(LSTCM_CIF_ID,LSTCM_COMP_NAME,LSTCM_COMP_SHORT_NAME,LSTCM_COMP_NAME_LOCAL,LSTCM_SHORT_NAME_LOCAL,LSTCM_COUNTRY,LSTCM_KYC,LSCM_INTF_STATUS,LSCM_INTF_DATE) values(?,?,?,?,?,?,?,?,?)";
//        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection)
//                    throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(sql,
//                        Statement.RETURN_GENERATED_KEYS);
//                ps.setLong(1, companyMasterData.getCifId());
//                ps.setString(2, companyMasterData.getCompName());
//                ps.setString(3, companyMasterData.getCompShortName());
//                return ps;
//            }
//        }, holder);
//        return "";
//    }

//    public List<CompanyMasterData> allUsers() {
//        String GET_USERS_QUERY = "SELECT * FROM LOT_STG_COMP_MASTER";
//        return jdbcTemplateOracle.query(GET_USERS_QUERY, (rs, rowNum) -> {
//            return new CompanyMasterData(rs.getInt("LSTCM_CIF_ID"), rs.getString("LSTCM_COMP_NAME"), rs.getString("LSTCM_COMP_SHORT_NAME"), rs.getString("LSTCM_COMP_NAME_LOCAL"), rs.getString("LSTCM_SHORT_NAME_LOCAL"), rs.getString("LSTCM_COUNTRY"), rs.getString("LSTCM_KYC"), rs.getString("lstcm_kyc"), rs.getString("LSCM_INTF_STATUS"));
//        });
//    }


//    public Map<String, Object> saveDetails(){
//        List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
//        JdbcTemplate jdbc = jdbcConfig.JdbcStoreProcedure(jdbcConfig.storeProcedure());
//
//        return jdbc.call(new CallableStatementCreator() {
//            @Override
//            public CallableStatement createCallableStatement(Connection con) throws SQLException {
//                CallableStatement cs = con.prepareCall("");
//
//            }
//        },parameters);
//    }

}





