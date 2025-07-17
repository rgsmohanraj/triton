package org.vcpl.triton.maker.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vcpl.triton.maker.model.Anchor;
import org.vcpl.triton.maker.service.Maker;


@Api(tags="MakerController")
@RestController
@RequestMapping("maker")
public class MakerController {

    @Autowired
    Maker maker;


    @ApiOperation("greet the user")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/greeting",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String greeting() {
        return "Hi hello i am maker controller";
    }


    @ApiOperation("save the anchor")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean save(String name) {
        return maker.save(name);
    }


    @ApiOperation("get the anchor")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/get/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Anchor getAnchor(@PathVariable(value = "name")String name) {
        return maker.get(name);
    }
}
