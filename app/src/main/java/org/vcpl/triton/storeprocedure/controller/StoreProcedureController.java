package org.vcpl.triton.storeprocedure.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.storeprocedure.service.AnchorMasterService;
import org.vcpl.triton.storeprocedure.service.CounterPartyMaster;
import org.vcpl.triton.storeprocedure.service.StoreProcedureService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Api(tags="Jdbc Connection")
@RestController
@Validated
@RequestMapping
public class StoreProcedureController {

    @Autowired
    private StoreProcedureService storeProcedureService;

    @Autowired
    private CounterPartyMaster counterPartyMaster;

    @Autowired
    private AnchorMasterService anchorMasterService;

    @ApiOperation("Jdbc Connection")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/masterStoreProcedure/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> companyInterest(@PathVariable long id) throws SQLException, ClassNotFoundException {
        return storeProcedureService.anchorStoreProcedure(id);
    }


    @ApiOperation("Jdbc Connection")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cpMasterStoreProcedure/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> cpCompanyInterest(@PathVariable long id) throws SQLException, ClassNotFoundException {
        return storeProcedureService.cpStoreProcedure(id);
    }

    @ApiOperation("Jdbc Connection")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cpMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String cpMaster(@RequestParam BigInteger cifId,@RequestParam long id,@RequestParam String companyName,@RequestParam long cust_id) throws SQLException, ClassNotFoundException {
        return counterPartyMaster.saveCpMasterDetails(cifId,id,companyName,cust_id,null);
    }

    @ApiOperation("Jdbc Connection")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/anchorMaster",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String anchorMaster(@RequestParam BigInteger cifId, @RequestParam long id, @RequestParam String companyName) throws SQLException, ClassNotFoundException {
        return anchorMasterService.saveAnchorMaster(cifId,id,companyName);
    }
}
