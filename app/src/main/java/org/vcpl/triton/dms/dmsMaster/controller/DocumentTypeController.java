package org.vcpl.triton.dms.dmsMaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;
import org.vcpl.triton.dms.dmsMaster.service.DocumentTypeService;

import java.util.List;
import java.util.Optional;

@Api(tags="DMS_Masters")
@RestController
@RequestMapping("dms")
@Validated
public class DocumentTypeController {
    @Autowired
    DocumentTypeService documentTypeService;

    @ApiOperation("Document Type")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documentType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentTypeEntity> getDocumentType(){
        return documentTypeService.getDocumentType();
    }

//    @ApiOperation("Document Type")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/documentType/{dtId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<DocumentTypeEntity> getDocumentTypeById(@PathVariable(value = "dtId") Long dtId){
        return documentTypeService.getDocumentTypeById(dtId);
    }

//    @ApiOperation("Document Type")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/documentType",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentTypeEntity saveDocumentType(@RequestBody DocumentTypeEntity documentTypeEntity){
        return documentTypeService.saveDocumentType(documentTypeEntity);
    }

//    @ApiOperation("Document Type")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/documentType/{dtId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @PutMapping("/documentType/{dtId}")
    public DocumentTypeEntity updateDocumentType(@PathVariable(value = "dtId")Long dtId, @RequestBody DocumentTypeEntity documentTypeEntity){
        return documentTypeService.updateDocumentType(dtId, documentTypeEntity);
    }

//    @ApiOperation("Document Type")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/documentType/{dtId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @DeleteMapping("/documentType/{dtId}")
    public ResponseEntity<Object> deleteDocumentTypeById(@PathVariable(value = "dtId") long dtId){
        return documentTypeService.deleteDocumentTypeById(dtId);
    }
}
