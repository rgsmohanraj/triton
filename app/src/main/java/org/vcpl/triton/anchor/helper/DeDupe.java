package org.vcpl.triton.anchor.helper;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.controller.AnchorFileUploadController;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.validation.ResponseControllerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
public class DeDupe {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private ResponseControllerService responseControllerService;

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadController.class);

    public List<String> convertExcelToListOfDeDupe(InputStream inputStream,String ddanchorName,String ddpan,String ddcin) throws IOException {
        try {
            logger.info(" | URL | convertExcelToListOfDeDupe| OPERATION | " + "message:Checking De-dupe Condition ");
            List<String> response = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Customer Information");
            Row row;
            DataFormatter dataFormater = new DataFormatter();
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);

                String cusName = dataFormater.formatCellValue(row.getCell(0)).toString().trim();
                String pan = dataFormater.formatCellValue(row.getCell(1)).toString().trim();
                String cin = dataFormater.formatCellValue(row.getCell(2)).toString().trim();
                if (!cusName.equals(ddanchorName.trim())) {
                    response.add("Please Enter valid AnchorName in Excel");
                }
                if (!ddcin.equals(cin.trim())) {
                    response.add("Please Enter valid CIN Number in Excel");
                }
                if (!ddpan.equals(pan.trim())) {
                    response.add("Please Enter valid PAN Number in Excel");
                }
                List<CustomerInfoEntity> customerInfoEntities = this.customerInfoRepository.customerInfoDetails(pan, cin);

                if (customerInfoEntities.size() > 0) {
                    if (cin.isEmpty() && customerInfoEntities.size() > 0) {
//                    response.add("  ");
                    } else {
                        response.add("Already Existing Customer");
                    }
                }
            }
            return response;
        } catch (IOException e) {
            logger.error(" | URL | convertExcelToListOfDeDupe| OPERATION | " + "Error message:Error While Checking De-dupe Condition ");
            e.printStackTrace();
        }
        return null;
    }


//    public ResponseEntity convertExcelToListOfDeDupe(InputStream inputStream,String ddanchorName,String ddpan,String ddcin) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        XSSFSheet sheet = workbook.getSheet("Customer Information");
//        int rowNumber = 0;
//        Iterator<Row> iterator = sheet.iterator();
//        while (iterator.hasNext()) {
//            Row row = iterator.next();
//
//            if (rowNumber == 0) {
//                rowNumber++;
//                continue;
//            }
//            Iterator<Cell> cells = row.iterator();
//
//            int cid = 0;
//
//
//            while (cells.hasNext()) {
//                Cell cell = cells.next();
//
//                switch (cid) {
//                    case 0:
//                        String customName = cell.getStringCellValue();
//                        if(customName.equals(ddanchorName)) {
//                            break;
//                        }else {
//                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter valid AnchorName in Excel");
//                        }
//
//                    case 1:
//                        String pan = cell.getStringCellValue();
//                        if(pan.equals(ddpan)){
//                        ResponseEntity responseEntity2 = DeDupe(pan);
//                        Object msg1 = responseEntity2.getBody();
//                        if(msg1.equals("Already Existing Customer")){
//                            cid = 4;
//                            return responseEntity2;
//                        }else {
//                            break;
//                        }
//                        }
//                        else {
//                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter valid PAN Number in Excel");
//
//                        }
//
//                    case 2:
//                        String cin = cell.getStringCellValue();
//                        if(cin.equals(ddcin)) {
//                            ResponseEntity responseEntity3 = DeDupe(cin);
//                            Object msg2 = responseEntity3.getBody();
//                            if (msg2.equals("Already Existing Customer")) {
//                                cid = 4;
//                                return responseEntity3;
//                            } else {
//                                return responseEntity3;
//                            }
//                        }else {
//                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter valid CIN Number in Excel");
//                        }
//
//                    default:
//                        break;
//                }
//                cid++;
//                if (cid >= 4) {
//
//                    break;
//                }
//            }
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Existing Customer");
//    }


    public ResponseEntity<Object> DeDupe(String customerName) {
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.deDupeCustomerInfo(customerName);
        if (customerInfoEntities.size() >0) {
            return ResponseEntity.status(BAD_REQUEST).body("Already Existing Customer");
        } else {
            return ResponseEntity.status(OK).body("New Anchor");
        }

    }
}
