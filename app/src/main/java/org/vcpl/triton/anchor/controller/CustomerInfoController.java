package org.vcpl.triton.anchor.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vcpl.triton.anchor.data.CustomerInfoData;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsPage;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsSearchCriteria;
import org.vcpl.triton.anchor.service.CustomerInfoService;
import org.vcpl.triton.rbac.service.PermissionService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@Api(tags = "anchorCustomer")
@RestController
@RequestMapping("anchor")
@Validated
public class CustomerInfoController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private PermissionService permissionService;

//    @ApiOperation("anchorCustomer")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/customerInfoFile_product",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerInfoEntity> getAllProduct(@RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            logger.info(" | URL | /customerInfoFile_product | OPERATION | " + "GET CustomerInfo");
            return this.customerInfoService.getAllProduct();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    @ApiOperation("anchorCustomer")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customerInfoDetail",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomerInfo(@Valid @RequestBody CustomerInfoData customerInfoData, @RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        if (permissionService.validation(token, "CPA,BUSINESS")) {
            logger.info(" | URL | /customerInfoDetail | OPERATION | " + "POST CustomerInfo");
            String message = "";
            boolean status=false;
            try {
                List<CustomerInfoEntity> responseEntites = customerInfoService.customerDetails(customerInfoData);
                if (responseEntites == null || responseEntites.size() == 0) {
                    message = "New Customer";
                    status=true;
                } else {
                    JSONArray array = new JSONArray();
                    for (CustomerInfoEntity c : responseEntites) {
                        JSONObject json1 = new JSONObject();
                        json1.put("customerName", c.getCustomerName());
                        json1.put("product", c.getProduct());
                        json1.put("pan", c.getPan());
                        json1.put("cin", c.getCin());
                        json1.put("custId",c.getId());
                        json1.put("dedupeStatus", c.getDedupeStatus());
                        array.put(json1);
                    }
                    message="Already Existing Customer";
                    status=true;
                    json.put("customerInfoEntity", array);
                }
            }catch (Exception ex){
                message="Some Technical Error, Please Contact Admin";
                ex.printStackTrace();
                status=false;
            }
            json.put("message",message);
            json.put("status",status);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return json.toString();
    }

    @ApiOperation("anchorCustomer")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customerInfoSearch",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String customerInfoSearch(@RequestParam("query") String query,@RequestParam("type") Integer type,@RequestParam("stage") String stage,@RequestParam("status") String status,@RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) throws UnsupportedEncodingException {
        if (permissionService.validation(token)) {
            logger.info(" | URL | /customerInfoSearch | OPERATION | " + "GET CustomerInfoSearch");
            String lst = customerInfoService.searchCustomerInfo(query,type,stage,status);
            //        Gson gson = new Gson();
            //        CustomerSearchData customerSearchData = gson.fromJson(lst,CustomerSearchData.class);
            //        return  customerSearchData;
            return lst;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }

    //    @ApiOperation("anchorCustomer")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/customerInfoDeDupe",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerInfoEntity>> deDupeCustomerInfo(@RequestParam("query") String query) {
        logger.info(" | URL | /customerInfoDeDupe | OPERATION | " + "GET customerInfoDeDupe");
        return ResponseEntity.ok(customerInfoService.deDupeCustomerInfo(query));
    }


    //    @ApiOperation("anchorCustomer")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/customerInfo/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping("/customerInfo/{id}")
    public CustomerInfoEntity findByIdCustomerInfo(@PathVariable long id) {
        return customerInfoService.getCustomerInfoById(id);
    }


        @ApiOperation("anchorCustomer")
        @RequestMapping(
            method = RequestMethod.PUT,
            value = "/customerInfo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerInfoEntity updateCustomerInfo(@Valid @RequestBody CustomerInfoData customerInfoData) {
        CustomerInfoEntity customerInfoEntity = customerInfoService.updateCustomerInfo(customerInfoData);
        return customerInfoEntity;
    }


    //    @ApiOperation("anchorCustomer")
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/customerInfoDetails/filters",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CustomerInfoEntity>> getCustomerInfo(CustomerInfoDetailsPage customerInfoDetailsPage,
                                                                    CustomerInfoDetailsSearchCriteria customerInfoDetailsSearchCriteria) {
        return new ResponseEntity<>(customerInfoService.getCustomer(customerInfoDetailsPage, customerInfoDetailsSearchCriteria), HttpStatus.OK);
    }


    //    @ApiOperation("anchorCustomer")
//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/deDupeCustomerInfo",
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deDupeCustomerInfo(@Valid @RequestBody CustomerInfoData customerInfoData) {
        JSONObject json = new JSONObject();

        List<CustomerInfoEntity> responseEntites = customerInfoService.customerDetails(customerInfoData);
        String message = "";
        if (responseEntites.size() == 0) {
            return "Successfully saved";
        } else {
            JSONArray array = new JSONArray();
            for (CustomerInfoEntity c : responseEntites) {
                JSONObject json1 = new JSONObject();
                json1.put("customerName", c.getCustomerName());
                json1.put("product", c.getProduct());
                json1.put("pan", c.getPan());
                json1.put("cin", c.getCin());
                json1.put("status", c.getDedupeStatus());
                array.put(json1);
            }
            json.put("CustomerInfoEntity", array);
        }
        return json.toString();
    }

    @ApiOperation("anchorCustomer")
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/setExpiryDate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void setExpiryDate(@RequestHeader(value = "Authorization", defaultValue = "null") String token, HttpServletResponse response) {
        customerInfoService.updateExpiryDate();
//        return customerInfoEntity;
    }
}
