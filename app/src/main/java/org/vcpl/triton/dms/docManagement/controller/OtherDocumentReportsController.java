package org.vcpl.triton.dms.docManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.dms.docManagement.configuration.LocalDateTypeAdapter;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentReportsData;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;
import org.vcpl.triton.dms.docManagement.service.IOtherDocumentReports;
import org.vcpl.triton.dms.docManagement.service.OtherDocumentReportsService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.keycloak.util.JsonSerialization.mapper;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "DMS_Other_Document_Deferral_Reports")
@RestController
@RequestMapping("dms")
@Validated
public class OtherDocumentReportsController {
    @Autowired
    IOtherDocumentReports documentReports;

    @Value("${fileLimit}")
    private Integer fileLimit;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private OtherDocumentReportsService otherDocumentReportsService;

    private static final Logger logger = LoggerFactory.getLogger(OtherDocumentReportsController.class);

    @ApiOperation("Other Document Deferral Report")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/otherUploadFile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("otherDocumentReportsData") String jsonOtherDocumentReportsData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            if (permissionService.validation(token, "OPERATION MAKER,BUSINESS,CPA,CREDIT COMMITTEE")) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
                Gson gson = gsonBuilder.create();
                OtherDocumentReportsData otherDocumentReportsData = gson.fromJson(jsonOtherDocumentReportsData, OtherDocumentReportsData.class);
                String[] myArr = {"pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg", "zip"};
                List<String> myEx = Arrays.asList(myArr);
                String fileName = file.getOriginalFilename();
                if (!file.isEmpty()) {
                    JSONObject json1 =new JSONObject();
                    JSONObject json = otherDocumentReportsService.saveFile(otherDocumentReportsData, file);
                    String result = json.getString("message");
                    JSONArray array=new JSONArray();
                    json1.put("otherDocId",array);
                    array.put(json);
                    if (result.equals("success")) {
                        return new ResponseEntity<Object>(responseControllerService.getBody(OK, "successfully Uploaded", "SCF001", array.toString()), new HttpHeaders(), OK);
                    } else if(result.equals("incorrectFormat")){
                        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Upload Correct Format file " + myEx, "SCF003", fileName, "Only Allowed Formats" + myEx), new HttpHeaders(), BAD_REQUEST);
                    }else if (result.equals("fileLimitFailed")) {
                        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "You can upload only " + fileLimit + " files", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                    } else if (result.equals("sameFile")) {
                        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Same file can't upload", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                    }else {
                        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, result, "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Empty file not Allowed", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("Other Document Deferral Report")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getOtherMaster/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<OtherDocumentMasterEntity> getOtherMaster(@PathVariable("id") Long id) {
        return otherDocumentReportsService.getOtherMaster(id);
    }

    @ApiOperation("Other Document Deferral Report")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/otherDownload/{appId}/{docType}/{docList}/{docName}/{filename}")
    public ResponseEntity downloadFile(@PathVariable("appId") Long appId, @PathVariable("docType") String docType, @PathVariable("docList") String docList, @PathVariable("docName") String docName, @PathVariable("filename") String fileName, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        File file = otherDocumentReportsService.downloadFile(appId, docType, docList,docName, fileName);
        Path path = Paths.get(file.getAbsolutePath());
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation("Other Document Deferral Report")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/deleteOtherFile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteS3File(@Valid @RequestBody OtherDocumentReportsData otherDocumentReportsData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" => URL : /deleteOtherFile | OPERATION : DELETE | Id : {}",otherDocumentReportsData.getAppId());
        String result = otherDocumentReportsService.deleteS3File(otherDocumentReportsData);
        if(result.equals("Success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, result, "SCF001",result), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, result, "SCF001",result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Other Document Deferral Report")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customerOtherDocReports",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerFileList(@RequestParam("appId") Long appId) {
        return otherDocumentReportsService.customerDocReports(appId);
    }
}
