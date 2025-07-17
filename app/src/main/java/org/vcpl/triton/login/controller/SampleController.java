package org.vcpl.triton.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vcpl.triton.login.model.*;
import org.vcpl.triton.login.service.LoginService;

@RestController
@RequestMapping("/auth")
public class SampleController {

    @Autowired
    LoginService loginservice;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginrequest) {
        return loginservice.login(loginrequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout (@RequestBody TokenRequest token) {
        return loginservice.logout(token);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody TokenRequest token) {
        return loginservice.introspect(token);
    }

}
