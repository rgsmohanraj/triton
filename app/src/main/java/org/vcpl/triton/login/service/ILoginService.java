package org.vcpl.triton.login.service;

import org.springframework.http.ResponseEntity;
import org.vcpl.triton.login.model.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface ILoginService {

    ResponseEntity<LoginResponse> login(LoginRequest loginrequest);

    ResponseEntity<Response> logout(TokenRequest request);

    ResponseEntity<IntrospectResponse> introspect(TokenRequest request);

    String doLogin(String username, String password, HttpServletResponse response);

    String getBase64Encoded(String clientId, String clientSec);

    String decodeJWT(String accessToken) throws UnsupportedEncodingException;
}
