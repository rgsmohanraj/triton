package org.vcpl.triton.dms.dmsMaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.dms.dmsMaster.service.DocumentListService;

import javax.validation.Valid;
import java.util.List;

@Api(tags="DMS_Masters")
@RestController
@RequestMapping("dms")
@Validated
public class DocumentListController {

    @Autowired
    DocumentListService documentListService;

    @ApiOperation("Document List")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documentList",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentListEntity> getAllDocumentList(){
        return documentListService.getAllDocumentList();
    }

//    @ApiOperation("Document List")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/documentList/{dlId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentListEntity findByDocumentListId(@PathVariable("dlId") Long dlId) {
        return documentListService.findByDocumentListId(dlId);
    }

//    @ApiOperation("Document List")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/documentList/{dcId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentListEntity saveDocumentList(@PathVariable Long dcId,@Valid @RequestBody DocumentListEntity documentListEntity){
        return documentListService.saveDocumentList(dcId,documentListEntity);
    }

//    @ApiOperation("Document List")
//    @RequestMapping(
//            method = RequestMethod.PUT,
//            value = "/documentList/{dlId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentListEntity updateDocumentList(@PathVariable Long dlId, @Valid @RequestBody DocumentListEntity documentListEntity) {
        return documentListService.updateDocumentList(documentListEntity,dlId);
    }

//    @ApiOperation("Document List")
//    @RequestMapping(
//            method = RequestMethod.DELETE,
//            value = "/documentList/{dlId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDocumentList(@PathVariable Long dlId){
        return documentListService.deleteDocumentList(dlId);
    }
}
