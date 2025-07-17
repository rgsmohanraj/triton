package org.vcpl.triton.rbac.service;

import io.jsonwebtoken.Claims;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vcpl.triton.login.model.IntrospectResponse;
import org.vcpl.triton.login.model.TokenRequest;
import org.vcpl.triton.login.service.LoginService;
import org.vcpl.triton.rbac.entity.PermissionEntity;
import org.vcpl.triton.rbac.repository.KeycloakGroupRepository;
import org.vcpl.triton.rbac.repository.PermissionRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Jwts;
import org.vcpl.triton.security.config.DatasourceForDbConnect;


public class PermissionService implements IPermisssion {


    public PermissionService(DatasourceForDbConnect data){
        this.clientSec = data.getOauth2ClientCredentialsClientSecret();
    }


//    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
    private String clientSec;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KeycloakGroupRepository keycloakGroupRepository;

    @Override
    public List<PermissionEntity> getPermissionListByRole(List<?> role) {
        return this.permissionRepository.findByRole(role);
    }

    public boolean validation(String token) throws UnsupportedEncodingException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attr.getResponse();
        if(!token.equals("null")) {
            TokenRequest tokenRequest = new TokenRequest();
            token = token.contains("Bearer ") ? token.replaceAll("Bearer ", "") : token;
            tokenRequest.setToken(token);
            ResponseEntity<IntrospectResponse> introspect = loginService.introspect(tokenRequest);
//            if (introspect.getBody().getActive()) {
                    String jwt = this.loginService.decodeJWT(token);
                    JSONObject json = new JSONObject(jwt);
                    List<?> role = this.loginService.getRole(json);
                    if (role != null) {
                        List<PermissionEntity> permissionEntities = permissionRepository.getPermissionList(role);
                        if (permissionEntities.size() > 0) {
                            return true;
                        } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            return false;
                        }
                    } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return false;
                    }
//            } else {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return false;
//            }
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    public Claims tokenValidation(String token) throws Exception {
        try{
            Claims claims = Jwts.parser().setSigningKey(clientSec).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }


    public boolean validation(String token, String roles) throws UnsupportedEncodingException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attr.getResponse();
        if (!token.equals("null")) {
            List<String> accessRole = List.of(roles.split(","));
            try {
                TokenRequest tokenRequest = new TokenRequest();
                token = token.contains("Bearer ") ? token.replaceAll("Bearer ", "") : token;
                tokenRequest.setToken(token);
                ResponseEntity<IntrospectResponse> introspect = loginService.introspect(tokenRequest);
//                if (introspect.getBody().getActive()) {
                    String jwt = this.loginService.decodeJWT(token);
                    JSONObject json = new JSONObject(jwt);
                    List<?> role = this.loginService.getRole(json);
                    if (role != null) {
                        ArrayList<String> duplicates = new ArrayList<String>();
                        for (String item : accessRole) {
                            if (role.contains(item)) {
                                duplicates.add(item.toUpperCase().trim());
                            }
                        }
                        List<PermissionEntity> permissionEntities = permissionRepository.getPermissionList(duplicates);
                        if (permissionEntities.size() > 0) {
                            return true;
                        } else {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                            return false;
                        return false;
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
//                        return false;
                    }
//                } else {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    return false;
////                            return true;
//                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                e.printStackTrace();
                return false;
//                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
//            return false;
        }
    }
}