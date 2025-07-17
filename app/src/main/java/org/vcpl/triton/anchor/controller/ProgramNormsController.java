package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.ProgramNormsData;
import org.vcpl.triton.anchor.data.ProgramNormsMasterData;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.service.ProgramNormsService;
import org.vcpl.triton.rbac.service.PermissionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@Api(tags = "anchorProgramNorms")
@RestController
@RequestMapping("anchor")
@Validated
public class ProgramNormsController {

    private static final Logger logger = LoggerFactory.getLogger(ProgramNormsController.class);

    @Autowired
    private ProgramNormsService programDetailsService;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("anchorProgramNorms")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/anchorPrograms",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgramNormsEntity saveProgramDetails(@Valid @RequestBody ProgramNormsData programNormsData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorPrograms | OPERATION | " + "POST ProgramNorms");
        if (permissionService.validation(token)) {
            ProgramNormsEntity programDetailsEntity = programDetailsService.saveProgramDetails(programNormsData);
            return programDetailsEntity;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorProgramsFile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ProgramNormsEntity> findByIdAnchorAddress(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorProgramsFile/{id} | OPERATION | " + "GETByFid ProgramNorms");
        if (permissionService.validation(token)) {
            return programDetailsService.getProgarmDetails(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/programsDetailsByCustId/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findByCustIdAnchorProgram(@PathVariable long id, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" | URL | /anchorProgramsFile/{id} | OPERATION | " + "GETByFid ProgramNorms");
        if (permissionService.validation(token)) {
            return programDetailsService.getProgarmDetailsByCustId(id);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    //    @ApiOperation("anchorProgramNorms")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/anchorPrograms/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgramNormsEntity findByIdProgramDetails(@PathVariable long id) {
        return programDetailsService.getProgramDetailsById(id);
    }


    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/anchorPrograms/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgramNormsEntity ProgramDetails(@PathVariable long id, @RequestParam String productName) {
        ProgramNormsEntity programNormsEntity =  programDetailsService.findById(id,productName);
        return programNormsEntity;
    }




    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/anchorPrograms",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> updateProgramDetails(@Valid @RequestBody ProgramNormsMasterData programNormsMasterData) {
        return programDetailsService.updateProgramDetails(programNormsMasterData);
    }

    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorProgramsDetails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> saveAnchorProgram(@Valid @RequestBody ProgramNormsMasterData programNormsMasterData) {
        return programDetailsService.saveAnchorProgram(programNormsMasterData);
    }

    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/anchorProgramNorms/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAnchorProgramNorms(@PathVariable long id){
        return  programDetailsService.deleteProgramNorms(id);
    }

    /*
    Renewal Enhancement Anchor Flow.
    Delete API to check the program norms against old app id.
     */
    @ApiOperation("anchorProgramNorms")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/programCondition/{custId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findProgramCondition(@RequestBody ProgramNormsData programNormsData, @PathVariable("custId") Long custId, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info(" => URL : /programCondition/{id} | OPERATION : DELETE | Id :",programNormsData.getAppId());
        if (permissionService.validation(token)) {
            return programDetailsService.getCondition(programNormsData.getId(),programNormsData.getSubProduct(),custId);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    //    @ApiOperation("anchorProgramNorms")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/programDetailsFile_product",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProgramNormsEntity> getAllProduct() {
        return this.programDetailsService.getAllProduct();
    }


}
