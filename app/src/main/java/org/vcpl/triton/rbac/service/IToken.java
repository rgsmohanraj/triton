package org.vcpl.triton.rbac.service;

import com.auth0.jwk.JwkException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.vcpl.triton.rbac.data.TokenData;
import org.vcpl.triton.rbac.entity.TokenEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.AccessDeniedException;
import java.security.interfaces.RSAPublicKey;

public interface IToken {

    TokenEntity saveToken(TokenData tokenData) throws UnsupportedEncodingException;

    String getTokenDetailsByBrowserId(String browserId, HttpServletResponse response) throws UnsupportedEncodingException;

//    DecodedJWT validate(String token);
    Boolean validate(String token);

    RSAPublicKey loadPublicKey(DecodedJWT token) throws JwkException, MalformedURLException;

    String getKeycloakCertificateUrl(DecodedJWT token);

}
