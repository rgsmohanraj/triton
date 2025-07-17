package org.vcpl.triton.dms.docManagement.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.vcpl.triton.dms.docManagement.data.DocumentReportsData;
import org.vcpl.triton.dms.docManagement.service.DocumentReportsService;
import org.vcpl.triton.dms.docValidation.DocValidationMasterData;
import org.vcpl.triton.dms.docValidation.DocValidationService;
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

import static java.net.HttpURLConnection.HTTP_OK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "DMS_Document_Reports")
@RestController
@RequestMapping("dms")
@Validated
public class DocumentReportsController {

    @Value("${fileLimit}")
    private Integer fileLimit;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private DocumentReportsService documentReportsService;

    @Autowired
    private DocValidationService docValidationService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentReportsController.class);

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/uploadFile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("documentReportsData") String jsonDocumentReportsData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {

        try {
            if (permissionService.validation(token, "OPERATION MAKER,BUSINESS,CPA,CREDIT COMMITTEE")) {
                Gson gson = new Gson();
                DocumentReportsData documentReportsData = gson.fromJson(jsonDocumentReportsData, DocumentReportsData.class);
                logger.info(" => URL : /uploadFile | OPERATION : POST | Id : {}",documentReportsData.getAppId());
                String[] myArr = {"pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg", "zip"};
                List<String> myEx = Arrays.asList(myArr);
                String fileName = file.getOriginalFilename();
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i + 1);
                }
                if (myEx.contains(extension.toLowerCase())) {
                    if (!file.isEmpty()) {
                        String result = documentReportsService.saveFile(documentReportsData, file);
                        if (result.equals("success")) {
                            return new ResponseEntity<Object>(responseControllerService.getBody(OK, "successfully Uploaded", "SCF001", result), new HttpHeaders(), OK);
                        } else if (result.equals("fileLimitFailed")) {
                            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "You can upload only " + fileLimit + " files", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                        } else if (result.equals("sameFile")) {
                            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Same file can't upload "+"'"+file.getOriginalFilename()+"'", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Empty file not Allowed "+"'"+file.getOriginalFilename()+"'", "SCF002", fileName), new HttpHeaders(), BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, "Upload Correct Format file " + myEx, "SCF003", fileName, "Only Allowed Formats" + myEx), new HttpHeaders(), BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //    @ApiOperation("Document Reports")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/download/{filename}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> downloadFolder(@PathVariable("filename") String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        byte[] bytes = documentReportsService.downloadFolder(filename);
        return ResponseEntity.status(HTTP_OK).headers(headers).body(bytes);
    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/download/{appId}/{docType}/{docList}/{filename}")
    // produces = MediaType.)
    public ResponseEntity downloadFile(@PathVariable("appId") Long appId, @PathVariable("docType") String docType, @PathVariable("docList") String docList, @PathVariable("filename") String fileName, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        File file = documentReportsService.downloadFile(appId, docType, docList, fileName);
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

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/deleteFile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteS3File(@Valid @RequestBody DocumentReportsData documentReportsData,@RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" => URL : /deleteS3File | OPERATION : DELETE | Id : {}",documentReportsData.getAppId());
        String result = documentReportsService.deleteS3File(documentReportsData);
        if(result.equals("Success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK, result, "SCF001",result), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST, result, "SCF001",result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getAllFiles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllFiles() {
        return documentReportsService.listAllFiles();
    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customerDocReports",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerFileList(@RequestParam("appId") Long appId) {
        return documentReportsService.customerDocReports(appId);
    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documentReports",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String documentReports(@RequestParam("appId") Long appId) {
        return documentReportsService.documentReports(appId);
    }

//    @ApiOperation("Document Reports")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/tempDocumentReports",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String tempDocumentReports(@RequestParam("appId") Long appId) {
//        return documentReportsService.tempDocumentReports(appId);
//    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getRenewalEnhancementDocReports",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRenewalEnhancementDocReports(@RequestParam("custId") Long custId) {
        return documentReportsService.getRenewalEnhancementDocReports(custId);
    }

//    @ApiOperation("Document Reports")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/getTempRenewalEnhancementDocReports",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getTempRenewalEnhancementDocReports(@RequestParam("custId") Long custId) {
//        return documentReportsService.getTempRenewalEnhancementDocReports(custId);
//    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/docValidation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> docValidation(@RequestBody DocValidationMasterData docValidationMasterData) {
        String result = docValidationService.documentValidation(docValidationMasterData);
        if(result.equals("success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK,result, "SCF001", result), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("Document Reports")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/conDocValidation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> conDocValidation(@RequestBody DocValidationMasterData docValidationMasterData) {
        String result = docValidationService.conDocValidation(docValidationMasterData);
        if(result.equals("success")){
            return new ResponseEntity<Object>(responseControllerService.getBody(OK,result, "SCF001", result), new HttpHeaders(), OK);
        }else {
            return new ResponseEntity<Object>(responseControllerService.getBody(BAD_REQUEST,result, "SCF002", result), new HttpHeaders(), BAD_REQUEST);
        }
    }

    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/assessmentCheck",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String assessmentCheck(@RequestParam("constitution") String constitution, @RequestParam("appId") Long appId) {
        return documentReportsService.assessmentCheck(constitution,appId);
    }


    @ApiOperation("proposedProducts")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteCreditDocuments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCreditDocuments(@RequestParam("appId") Long appId) {
        return documentReportsService.deleteCreditDocuments(appId);
    }

}
