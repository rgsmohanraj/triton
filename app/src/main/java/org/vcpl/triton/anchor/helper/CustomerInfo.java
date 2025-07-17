package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;
import org.vcpl.triton.validation.ResponseControllerService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Service
public class CustomerInfo {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadService.class);

    @Autowired
    private ResponseControllerService responseControllerService;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }


    public CustomerInfoEntity convertExcelToListOfCustomerInfo(InputStream inputStream) throws IOException {
        try{
            logger.info(" | URL | Logging to CustomerInfo | OPERATION | " + "Message: Reading Customer Info");
        CustomerInfoEntity customerInfoEntity = new CustomerInfoEntity();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("Customer Information");
        int rowNumber = 0;
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();

            if (rowNumber == 1) {
                Iterator<Cell> cells = row.iterator();

                int cid = 0;


                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            customerInfoEntity.setCustomerName(cell.getStringCellValue().trim());
                            break;

                        case 1:
                            customerInfoEntity.setPan(cell.getStringCellValue().trim());
                            break;

                        case 2:
                            customerInfoEntity.setCin(cell.getStringCellValue().trim());
                            break;

                        default:
                            break;
                    }
                    cid++;
                }
            }

            rowNumber++;

        }
        customerInfoEntity.setDedupeStatus("Active");
        customerInfoEntity.setProduct("SCF");
        return validate(customerInfoEntity);
    }catch (Exception ex){
            ex.printStackTrace();
            logger.error("Error Message: Error while reading Customer Info :: "+ex.getMessage());
        }
        return null;
    }

    private CustomerInfoEntity validate(@Valid CustomerInfoEntity customerInfoEntity) {
        return customerInfoEntity;
    }

}
