package org.vcpl.triton.report.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vcpl.triton.report.service.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/reports")
public class TritonReport {

    @Autowired
    private ReportService reportService;

//    @Autowired
//    private AWSDBConfiguration awsdbConfiguration;

    @ApiOperation("Reports")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/fullReport",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> downloadFullReport() throws IOException {
//        awsdbConfiguration.tritonProdDataSource();
        Workbook workbook = new HSSFWorkbook();
        workbook = reportService.downloadFullReport(workbook);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Triton_Report.xls");

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
