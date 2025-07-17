package org.vcpl.triton.login.controller;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.vcpl.triton.login.model.LoginRequest;
import org.vcpl.triton.login.service.LoginService;

import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value="/doLogin",method = RequestMethod.POST)
    public String doLogin(@RequestBody LoginRequest login, HttpServletResponse response) throws OAuthSystemException, OAuthProblemException {
        logger.info("Login Initialization [Username : {}]",login.getUsername());
        return loginService.doLogin(login.getUsername(), login.getPassword(), response);
    }
}

