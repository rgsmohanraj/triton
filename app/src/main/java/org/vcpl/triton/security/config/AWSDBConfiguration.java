package org.vcpl.triton.security.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vcpl.triton.dms.docManagement.service.DocumentReportsService;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentMasterService;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentReportsService;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.rbac.service.GroupService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.rbac.service.TokenService;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Order(1)
@ComponentScan({"org.vcpl.triton.*"})
@EnableJpaRepositories(basePackages = "org.vcpl.triton.*")
@EntityScan(basePackages = "org.vcpl.triton")
@EnableTransactionManagement
public class AWSDBConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AWSDBConfiguration.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.secret.key}")
    private String secretKey;
    @Value("${aws.secret.name}")
    private String secretName;

//    AWS Connection
    @Bean
    @Primary
    public DataSource tritonDataSource() {
        final DatasourceForDbConnect credentials = buildSecretManager();
        return DataSourceBuilder.create()
                .url(credentials.getDatasource())
                .username(credentials.getDatasourceUsername())
                .password(credentials.getDatasourcePassword())
                .driverClassName(credentials.getDatasourceDriverClassName())
                .build();
    }

    // Maria Local Connection
//    @Bean
//    @Primary
//    public DataSource tritonDataSource() {
//        final DatasourceForDbConnect credentials = buildSecretManager();
//        return DataSourceBuilder.create()
//                .url("jdbc:mariadb://zeus-uat.col81animvpb.ap-south-1.rds.amazonaws.com:3571/triton_dev?sessionVariables=sql_mode=''")
//                .username("vcpl_tritonadmin")
//                .password("$51.6lAP}WeKS%P8")
//                .driverClassName("org.mariadb.jdbc.Driver")
//                .build();
//    }

    @Bean
    public DataSource lmsDataSource() {
       final DatasourceForDbConnect credentials1 = buildSecretManager();
        return DataSourceBuilder.create().url(credentials1.getDatasource1())
                .username(credentials1.getDatasource1Username())
                .password(credentials1.getDatasource1Password())
                .driverClassName(credentials1.getDatasource1DriverClassName())
                .build();
    }


    @Bean
    public DataSource storeProcedure() {
        final DatasourceForDbConnect credentials2 = buildSecretManager();
        return DataSourceBuilder.create().url(credentials2.getDatasource2url())
                .username(credentials2.getDatasource2Username())
                .password(credentials2.getDatasource2Password())
                .driverClassName(credentials2.getDatasource2DriverClassName())
                .build();
    }

    @Bean
    @Primary
    public JdbcTemplate JdbcTemplateTriton(@Qualifier("tritonDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate JdbcTemplateLms(@Qualifier("lmsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate JdbcStoreProcedure(@Qualifier("storeProcedure") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DatasourceForDbConnect buildSecretManager() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        String secret;
        AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;
        try {
            getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);
            if (getSecretValueResult.getSecretString() != null) {
                secret = getSecretValueResult.getSecretString();
                DatasourceForDbConnect datasourceForDbConnect = mapper.readValue(secret, DatasourceForDbConnect.class);
                return datasourceForDbConnect;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        DatasourceForDbConnect data = buildSecretManager();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername(data.getMailUsername());
        mailSender.setPassword(data.getMailPassword());
        mailSender.setProtocol("smtp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.ssl.trust", data.getMailPropertiesSslTrust());
        props.put("mail.tls", data.getMailPropertiesTls());
        props.put("mail.smtp.starttls.enable",data.getMailPropertiesStarttlsEnable());
        props.put("smtp.starttls.required", data.getMailPropertiesStarttlsRequired());
        props.put("mail.smtp.auth",data.getMailPropertiesAuth());
        props.put("spring.mail.username",data.getMailUsername());

        return mailSender;
    }

    @Bean
    public OAuthClientRequest getKeyClockCredentials() throws OAuthSystemException {
        DatasourceForDbConnect data = buildSecretManager();

        OAuthClientRequest request =OAuthClientRequest.tokenLocation(data.getKeycloakTokenUri())
                .setGrantType(GrantType.PASSWORD).setPassword(data.getKeycloakPassword()).setUsername(data.getKeycloakUsername()).setClientId(data.getOauth2ClientCredentialsClientId())
                .setClientSecret(data.getSecret())
                .buildBodyMessage();


        return request;
    }

//    @Bean
//    protected Keycloak keycloak() {
//        return KeycloakBuilder.builder()
//                .grantType(OAuth2Constants.PASSWORD)
//                .realm("ThinkIAM")
//                .clientId("admin-cli")
//                .username("admin")
//                .password("xWFdGE#U7@4QX*xq")
//                .serverUrl("http://10.100.10.32:8080/auth")
//                .build();
//    }


    @Bean
    public LoginService loginService(){
        DatasourceForDbConnect data = buildSecretManager();
        return new LoginService(data);
    }


    @Bean
    public TokenService tokenService(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new TokenService(data);
    }

    @Bean
    public GroupService groupService(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new GroupService(data);
    }

    @Bean
    public DocumentReportsService dmsBucket1(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new DocumentReportsService(data,s3());
    }

    @Bean
    public OtherDocumentReportsService dmsBucket2(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new OtherDocumentReportsService(data,s3());
    }

    @Bean
    public OtherDocumentMasterService dmsBucket3(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new OtherDocumentMasterService(data,s3());
    }


    @Bean
    public APISecurityConfig apiSecurityConfig(){
        DatasourceForDbConnect data = buildSecretManager();
      return new APISecurityConfig(data);
    }

    @Bean
    public PermissionService permissionService(){
        DatasourceForDbConnect data = buildSecretManager();
        return  new PermissionService(data);
    }

    @Bean
    public EmailService emailService(){
        DatasourceForDbConnect data = buildSecretManager();
        return new EmailService(data);
    }

//    @Bean
//    public DocumentReportService documentReportService(){
//        DatasourceForDbConnect data = buildSecretManager();
//        return  new DocumentReportService(data);
//    }

//    @Bean
//    public S3Config s3Config(){
//        DatasourceForDbConnect data = buildSecretManager();
//        System.out.println("*****data*******  :"+data.getAccessKey() +"****"+data.getSecret());
//        return  new S3Config(data);
//    }


    @Bean
    public AmazonS3 s3(){
        DatasourceForDbConnect data = buildSecretManager();
        AWSCredentials awsCredentials = new BasicAWSCredentials(data.getAccessKey(),data.getSecret());
        return AmazonS3ClientBuilder.standard().withRegion(data.getRegion()).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }




}
