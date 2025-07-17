package org.vcpl.triton.anchor.controller;


import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.data.DedupeAnchorData;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.helper.CustomerInfo;
import org.vcpl.triton.anchor.helper.DeDupe;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;
import org.vcpl.triton.dms.docManagement.data.DocumentReportsData;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;


@Api(tags="anchorFileUpload")
@RestController
@RequestMapping("anchor")
@Validated
public class AnchorFileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadController.class);

    @Autowired
    private AnchorFileUploadService anchorFileUploadService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private DeDupe deDupe;

    @Autowired
    private PermissionService permissionService;


    //    @Transactional(rollbackOn = ConstraintViolationException.class)
    @ApiOperation("anchorFileUpload")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorFileUpload",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> upload(@RequestParam("file") MultipartFile file, @RequestParam("dedupeData") String dedupeData,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws IOException, ParseException {
        logger.info(" | URL | /anchorFileUpload | OPERATION | " + "POST anchorFileUpload");
        try {
            Gson gson = new Gson();
            DedupeAnchorData dedupeAnchorData = gson.fromJson(dedupeData, DedupeAnchorData.class);
            Map<String, List<Object>> map = new HashMap<>();
            try {
                if (permissionService.validation(token, "CPA")) {
                    if (CustomerInfo.checkExcelFormat(file)) {
                        List<String> responseEntites = deDupe.convertExcelToListOfDeDupe(file.getInputStream(), dedupeAnchorData.getAnchorName(), dedupeAnchorData.getPan(), dedupeAnchorData.getCin());

                        if (responseEntites.size() == 0) {
                            return anchorFileUploadService.save(file, dedupeAnchorData.getApprover(), response);
                        } else {
                            map.put("message", Collections.singletonList(responseEntites));
                            map.put("status", Collections.singletonList(false));
                            //                response.setStatus(HttpServletResponse.SC_CONFLICT);
                        }
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            } catch (Exception e) {
                logger.error("Exception occured while anchorFileUpload "+e.getMessage());
                e.printStackTrace();
            }
            return map;

        } catch (Exception e) {
            logger.error("Exception occured while anchorFileUpload "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
//    @ApiOperation("anchorFileUpload")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorDetails/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<CustomerInfoEntity> getCustomerById(@PathVariable(value = "id") Long id,@RequestHeader(value="Authorization",defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorDetails/{id} | OPERATION | " + "GetById anchorFileUpload");
        if(permissionService.validation(token)) {
            return anchorFileUploadService.getCustomerById(id);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return Optional.empty();
    }
}
