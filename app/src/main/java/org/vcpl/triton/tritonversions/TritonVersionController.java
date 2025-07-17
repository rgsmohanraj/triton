package org.vcpl.triton.tritonversions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@Api(tags="tritonVersion")
@RestController
@RequestMapping("anchor")
@Validated
public class TritonVersionController {


    @Autowired
    private  TritonVersionService tritonVersionService;

//    @ApiOperation("tritonVersion")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/tritonVersions",
            produces = "application/json; charset=utf8")
    public String upload() throws IOException {
        return tritonVersionService.getFile();
    }

}
