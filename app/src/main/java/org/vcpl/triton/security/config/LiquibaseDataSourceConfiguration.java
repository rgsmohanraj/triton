//package org.vcpl.triton.security.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//@Configuration
//@Component
//public class LiquibaseDataSourceConfiguration {
//
//    private static final Logger LOG = LoggerFactory.getLogger(LiquibaseDataSourceConfiguration.class);
//
//    @Autowired
//    private AWSDBConfiguration liquibaseDataSourceProperties;
//
//    @LiquibaseDataSource
//    @Bean
//    public DataSource liquibaseDataSource() throws SQLException {
//        DataSource ds =  DataSourceBuilder.create()
//                .url("jdbc:mariadb://zeus-uat.col81animvpb.ap-south-1.rds.amazonaws.com:3571/triton_uat?sessionVariables=sql_mode=''")
//                .username("vcpl_tritonadmin")
//                .password("Cis2Z$6A0WL22L69")
//                .driverClassName("org.mariadb.jdbc.Driver")
//                .build();
//        if (ds instanceof org.apache.tomcat.jdbc.pool.DataSource) {
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setInitialSize(1);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMaxActive(2);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMaxAge(1000);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMinIdle(0);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMinEvictableIdleTimeMillis(60000);
//        } else {
//            // warnings or exceptions, whatever you prefer
//        }
//
//        LOG.info("Initialized a datasource for {}", ds.getConnection());
//        return ds;
//    }
//
//
//
//}
