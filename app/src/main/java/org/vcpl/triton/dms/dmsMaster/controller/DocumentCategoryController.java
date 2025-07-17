package org.vcpl.triton.dms.dmsMaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentCategoryEntity;
import org.vcpl.triton.dms.dmsMaster.service.DocumentCategoryService;

import java.util.List;
import java.util.Optional;

@Api(tags="DMS_Masters")
@RestController
@RequestMapping("dms")
@Validated
public class DocumentCategoryController {
    @Autowired
    DocumentCategoryService documentCategoryService;

    @ApiOperation("Document Category")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documentCategory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentCategoryEntity> getDocumentCategory(){
        return documentCategoryService.getAllCategory();
    }

//    @ApiOperation("Document Category")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/documentCategory/{dcId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<DocumentCategoryEntity> getDocumentCategoryById(@PathVariable(value = "dcId") Long dcId){
        return documentCategoryService.getDocumentCategoryById(dcId);
    }

//    @ApiOperation("Document Category")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/documentCategory/{dtId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public  DocumentCategoryEntity createDocumentCategory(@PathVariable(value = "dtId") Long dtId,@RequestBody DocumentCategoryEntity documentCategoryEntity){
        return  documentCategoryService.createDocumentCategory(dtId,documentCategoryEntity);
    }

//    @ApiOperation("Document Category")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/documentCategory/{dcId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentCategoryEntity updateDocumentCategory(@PathVariable(value = "dcId") Long dcId,@RequestBody DocumentCategoryEntity documentCategoryEntity){
        return documentCategoryService.updateDocumentCategory(dcId, documentCategoryEntity);
    }

//    @ApiOperation("Document Category")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/documentCategory/{dcId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDocumentCategoryById(@PathVariable(value = "dcId") long dcId){
        return documentCategoryService.deleteDocumentCategoryById(dcId);
    }
}
