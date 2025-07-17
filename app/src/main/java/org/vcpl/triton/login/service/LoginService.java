package org.vcpl.triton.login.service;


import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.vcpl.triton.login.model.*;
import org.vcpl.triton.rbac.data.TokenData;
import org.vcpl.triton.rbac.entity.TokenEntity;
import org.vcpl.triton.rbac.repository.TokenRepository;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LoginService implements ILoginService{


    public LoginService(DatasourceForDbConnect data){
        this.authURL = data.getKeycloakAuthServerUrl();
        this.clientId = data.getOauth2ClientCredentialsClientId();
        this.grantType = data.getOauth2ClientCredentialsAuthorizationGrantType();
        this.clientSec = data.getOauth2ClientCredentialsClientSecret();
        this.tokenUrl = data.getKeycloakTokenUri();
    }

    @Autowired
    private TokenRepository tokenRepository;

//    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUrl;

//    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}")
    private String clientId;

//    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
    private String clientSec;

//    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}")
    private String grantType;

//    @Value("${keycloak.auth-server-url}")
    private String authURL;


    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public ResponseEntity<LoginResponse> login(LoginRequest loginrequest) {
        try {
            logger.info(" login method ");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSec);
            map.add("grant_type", grantType);
            map.add("username", loginrequest.getUsername());
            map.add("password", loginrequest.getPassword());

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM_DEV/protocol/openid-connect/token", httpEntity, LoginResponse.class);
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM/protocol/openid-connect/token", httpEntity, LoginResponse.class);

            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Login Service ---- ",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public ResponseEntity<Response> logout(TokenRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSec);
        map.add("refresh_token", request.getToken());


        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Response> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM_DEV/protocol/openid-connect/logout", httpEntity, Response.class);
        ResponseEntity<Response> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM/protocol/openid-connect/logout", httpEntity, Response.class);

        Response res = new Response();
        if(response.getStatusCode().is2xxSuccessful()) {
//            this.tokenRepository.deleteRefreshToken(request.getToken());
            res.setMessage("Logged out successfully");
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


    public ResponseEntity<IntrospectResponse> introspect(TokenRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        clientId="ThinkIAM_DEV";
//        clientSec="26oI8R0akiapY0htXcZY6lQts7qS4kYy";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSec);
        map.add("token", request.getToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<IntrospectResponse> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM_DEV/protocol/openid-connect/token/introspect", httpEntity, IntrospectResponse.class);
        ResponseEntity<IntrospectResponse> response = restTemplate.postForEntity(authURL+"/realms/ThinkIAM/protocol/openid-connect/token/introspect", httpEntity, IntrospectResponse.class);
        return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
    }

    public String doLogin(String username,String password,HttpServletResponse response){
        logger.info("Login Initialization [Username : {}]",username);
        JSONObject json=new JSONObject();
        String finaltoken;
        String bearer;

//        tokenUrl = "http://10.100.10.32:8080/auth/realms/ThinkIAM_DEV/protocol/openid-connect/token";
//        clientId = "ThinkIAM_DEV";
//        clientSec = "26oI8R0akiapY0htXcZY6lQts7qS4kYy";

//        String enCode=getBase64Encoded(clientId,clientSec);
        try {
            OAuthClient client= new OAuthClient(new URLConnectionClient());

            OAuthClientRequest request=OAuthClientRequest.tokenLocation(tokenUrl)
                    .setGrantType(GrantType.PASSWORD).setPassword(password).setUsername(username).setClientId(clientId)
                    .setClientSecret(clientSec)
                    .buildBodyMessage();
            OAuthJSONAccessTokenResponse oAuthResponse=client.accessToken(request, OAuth.HttpMethod.POST,OAuthJSONAccessTokenResponse.class);
            request.addHeader("Authorization","Bearer "+oAuthResponse.getAccessToken());
            finaltoken=oAuthResponse.getAccessToken();
            String refreshToken=oAuthResponse.getRefreshToken();
            bearer="Bearer "+finaltoken;
            try {
                String jwt=decodeJWT(finaltoken);
                json=new JSONObject(jwt);
                List<?> role = getRole(json);
                response.setHeader("Authorization",bearer);
                json.put("access_token",bearer);
                json.put("refresh_token",refreshToken);
//                String str =String.valueOf(json.get("resource_access"));

                if(json!=null && role!=null && role.contains("SCF_Triton_Admin")){
                        JSONArray array1 = new JSONArray();
                        for(int i=0;i<role.size();i++){
                            try {
                                String lead = role.get(i).toString();
                               if(lead.contains("_LEAD")){
                                   array1.put(role.get(i));
                               }
                            }catch (IndexOutOfBoundsException e){
                                e.printStackTrace();
                                continue;
                            }
                        }
                    json.put("lead",array1);
                    json.put("status",true);
                    TokenData data=new TokenData();
                    TokenEntity tokenData = new TokenEntity();
                    tokenData.setJwtToken(finaltoken);
                    tokenData.setRefreshToken(refreshToken);
                    tokenData.setFlag(1);
                    tokenData.setBrowserId(data.gen());
                    TokenEntity tokenEntity = this.tokenRepository.save(tokenData);
                    json.put("browserId",tokenEntity.getBrowserId());
                    json.put("message","Successfully logged in");
                }else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    TokenRequest token = new TokenRequest();
                    token.setToken(refreshToken);
                    logout(token);
                    json=new JSONObject();
                    json.put("status",false);
                    json.put("message","You do not have permission to access this application. Please contact your system administrator.");
                }
                json.toString();

            }catch(Exception e) {
                logger.error("Exception occured while applicationDetails to: {} , due to: {}",e.getMessage());
            }
        }catch(Exception oAuthProblemException){
            oAuthProblemException.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            json.put("status",false);
            if(oAuthProblemException.getMessage().contains("Invalid user credentials")) {
                json.put("message", "Invalid User Credentials");
            }else{
                json.put("message", "Please Contact Admin");
            }
            logger.error("Exception occurred while login, due to : {}",oAuthProblemException.getMessage());
        }
        logger.debug(" doLogin method " + json.toString());
        return json.toString();
    }

    public String getBase64Encoded(String clientId, String clientSec) {
        return java.util.Base64.getEncoder().encodeToString((clientId+":"+clientSec).getBytes(StandardCharsets.UTF_8));
    }

    public String decodeJWT(String accessToken) throws UnsupportedEncodingException {
        String payload=accessToken.split("\\.")[1];
        return new String( Base64.decodeBase64(payload),"UTF-8");
    }

    public static List<?> getRole(JSONObject json) {
        try {
            JSONObject role = (JSONObject) json.get("resource_access");
//            role = (JSONObject) role.get("ThinkIAM_DEV");
            role = (JSONObject) role.get("ThinkIAM");
            String roles = role.get("roles").toString();
            String[] rol = roles.substring(1, roles.length() - 1).replaceAll("\"", "").split(",");
            return Stream.of(rol).map(Object::toString).collect(Collectors.toList());
        }catch(JSONException e){
            if(e.getMessage().contains("JSONObject["+"ThinkIAM"+"]"+"not found")){
                return null;
            }
            e.printStackTrace();
        }
        return null;
    }
}
