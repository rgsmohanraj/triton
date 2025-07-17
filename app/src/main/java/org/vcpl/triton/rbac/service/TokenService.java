package org.vcpl.triton.rbac.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.login.model.IntrospectResponse;
import org.vcpl.triton.login.model.TokenRequest;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.rbac.data.TokenData;
import org.vcpl.triton.rbac.entity.TokenEntity;
import org.vcpl.triton.rbac.repository.TokenRepository;


import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;


public class TokenService implements IToken {

    public  TokenService(DatasourceForDbConnect data){
        this.authURL = data.getKeycloakAuthServerUrl();
    }

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    LoginService loginService;

//    @Value("${keycloak.auth-server-url}")
    private  String authURL;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private  final List<String> allowedIsses = Collections.singletonList(authURL+"/realms/ThinkIAM");

    @Override
    public TokenEntity saveToken(TokenData tokenData) throws UnsupportedEncodingException {
        return this.tokenRepository.save(transform(tokenData));
    }

    public String getTokenDetailsByBrowserId(String browserId,HttpServletResponse response) {
       TokenEntity token =  this.tokenRepository.getTokenDetailsByBrowserId(browserId);
       JSONObject json= new JSONObject();
       if(!browserId.equals(null)) {
           try {
               TokenRequest tokenRequest = new TokenRequest();
               if (token != null) {
                   tokenRequest.setToken(token.getRefreshToken());
                   ResponseEntity<IntrospectResponse> responseResponseEntity = this.loginService.introspect(tokenRequest);
                   if (responseResponseEntity.getBody().getActive()) {
                       String jwt = this.loginService.decodeJWT(token.getJwtToken());
                       json = new JSONObject(jwt);
                       List<?> role = loginService.getRole(json);
                       response.setHeader("Authorization", token.getJwtToken());
                       //                    String str =String.valueOf(json.get("resource_access"));
                       if (role != null && role.contains("SCF_Triton_Admin")) {
                           json.put("access_token", token.getJwtToken());
                           json.put("refresh_token", token.getRefreshToken());
                           json.put("message", "Successfully Login");
                           json.put("status", true);
                           json.put("browserId", browserId);
                       } else {
                           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                           tokenRequest.setToken(token.getRefreshToken());
                           loginService.logout(tokenRequest);
                           json.put("status", false);
                           json.put("message", "You do not have permission to access this application. Please contact your system administrator.");
                       }
                       json.toString();
                   } else {
                       response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                       json.put("status", false);
                   }
               } else {
                   response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                   json.put("status", false);
               }
           } catch (Exception ex) {
               response.setStatus(HttpServletResponse.SC_FORBIDDEN);
               ex.printStackTrace();
           }
       }else{
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           json.put("status", false);
       }
        return json.toString();
    }

    private TokenEntity transform(TokenData tokenData) throws UnsupportedEncodingException {
        TokenEntity entity = new TokenEntity();
        entity.setBrowserId(tokenData.gen());
        entity.setJwtToken(tokenData.getJwtToken());
        entity.setFlag(1);
        entity.setRefreshToken(tokenData.getRefreshToken());
//        entity.setTokenCreationSecond(getTokenTiming(tokenData.getJwtToken())[0]);
        entity.setTokenExpireTime(getTokenTiming(tokenData.getJwtToken()));
        return entity;
    }

    public Timestamp getTokenTiming(String token) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        String jwt = this.loginService.decodeJWT("Bearer "+token);
        json=new JSONObject(jwt);
        Long[] arr= new Long[2];
        arr[0]=((Number) json.get("exp")).longValue();
        arr[1]= ((Number) json.get("iat")).longValue();
        long expTime = arr[0]-arr[1];
        Timestamp time= new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expTime));
        return time;
    }

//    ____________________________________________________________________________


    public String getKeycloakCertificateUrl(DecodedJWT token) {
        return token.getIssuer() + "/protocol/openid-connect/certs";
    }

    public RSAPublicKey loadPublicKey(DecodedJWT token) throws JwkException, MalformedURLException {

        final String url = getKeycloakCertificateUrl(token);
        JwkProvider provider = new UrlJwkProvider(new URL(url));
        return (RSAPublicKey) provider.get(token.getKeyId()).getPublicKey();
    }

    /**
     * Validate a JWT token
     * @param token
     * @return decoded token
     */


//    public DecodedJWT validate(String token) {
//        try {
//            final DecodedJWT jwt = JWT.decode(token);
//            if (!allowedIsses.contains(jwt.getIssuer())) {
//                throw new InvalidParameterException(String.format("Unknown Issuer %s", jwt.getIssuer()));
//            }
//
//            RSAPublicKey publicKey = loadPublicKey(jwt);
//            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer(jwt.getIssuer())
//                    .build();
//
//            verifier.verify(token);
//            return jwt;
//
//        } catch (Exception e) {
//            logger.error("Failed to validate JWT", e);
//            e.printStackTrace();
//            throw new InvalidParameterException("JWT validation failed: " + e.getMessage());
//        }
//    }



    public Boolean validate(String token) {
        try {
            final DecodedJWT jwt = JWT.decode(token);
            if (!allowedIsses.contains(jwt.getIssuer())) {
                throw new InvalidParameterException(String.format("Unknown Issuer %s", jwt.getIssuer()));
            }

            RSAPublicKey publicKey = loadPublicKey(jwt);
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwt.getIssuer())
                    .build();

            verifier.verify(token);
            return true;

        } catch (Exception e) {
            logger.error("Failed to validate JWT"+ e.getMessage());
            e.printStackTrace();
//            throw new InvalidParameterException("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

}
