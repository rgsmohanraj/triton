package org.vcpl.triton.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResponseControllerService {

    public Map<String, Object> getBody(HttpStatus status, String message, String returnCode, long entityId) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("entityId",entityId);

        return body;
    }

    public Map<String, Object> getBody(HttpStatus status, String message, String returnCode, List<Long> entityIds) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("entityIds",entityIds);

        return body;
    }

    public Map<String, Object> getBody(HttpStatus status, String message, String returnCode, String fileName) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("FileName",fileName);
        return body;
    }

    public Map<String, Object> getBody(HttpStatus status, String message, String returnCode, String fileName,String formetList) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("FileName",fileName);
        body.put("FormetList",formetList);
        return body;
    }


    public Map<String, Object> getEntityBody(HttpStatus status, String message, String returnCode, List<Integer> entityIds) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("entityIdList", entityIds.toString());

        return body;
    }

    public Map<String, Object> getEntityBodyWithLimit(HttpStatus status, String message, String returnCode, String availableLimit) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("Available Limit", availableLimit);

        return body;
    }

    public Map<String, Object> getFileUploadBody(HttpStatus status, String message, String returnCode, String fileNames) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("FileNames",fileNames);

        return body;
    }


}
