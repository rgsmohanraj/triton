package org.vcpl.triton.notification.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.notification.util.EmailUtil;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("notification/email")
@Api(tags="NotificationService")
public class NotificationController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailUtil emailUtil;

   /* @GetMapping
    @ApiOperation("Email Sender")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/send/{userEmail}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendEmail(@PathVariable String userEmail) throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("message", emailService.sendEmail(userEmail));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
*/
    @GetMapping
    @ApiOperation("Email Sender")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/send/{stageId}/{anchorName}/{userEmail}/{nextApprover}/{action}/{stage}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendEmail(@PathVariable Long stageId,@PathVariable String anchorName,@PathVariable String userEmail,@PathVariable String nextApprover,@PathVariable Long action,@PathVariable String stage) throws MessagingException{
        Map<String, Object> map = new HashMap<>();
        map.put("message", emailUtil.sendEmail(stageId,anchorName,userEmail,nextApprover,action,stage));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestParam String to,@RequestParam String sub, @RequestParam String body) {
        emailService.sendSimpleEmail(to, sub, body);
    }
}

