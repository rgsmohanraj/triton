package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.AnchorAuthorizedEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnchorAuthorized {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadService.class);

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }


    public List<AnchorAuthorizedEntity> convertExcelToListOfAnchorAuthorized(InputStream inputStream, ApplicationEntity applicationEntity) throws IOException {
        try{
            logger.info(" | URL | Logging to Anchor Address | OPERATION | " + "Message: Logging to Anchor Address");
        List<AnchorAuthorizedEntity> list = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("Auth Signatory");
        int rowNumber = 0;
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();

            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = row.iterator();

            int cid = 0;

            AnchorAuthorizedEntity anchorAuthorizedEntity = new AnchorAuthorizedEntity();


            while (cells.hasNext()) {
                Cell cell = cells.next();

                switch (cid) {
                    case 0:
                        anchorAuthorizedEntity.setType(cell.getStringCellValue().trim());
                        break;

                    case 1:
                        anchorAuthorizedEntity.setName(cell.getStringCellValue().trim());
                        break;

                    case 2:
                        double mobileNo = cell.getNumericCellValue();
                        long l = (new Double(mobileNo)).longValue();
                        String mobile = String.valueOf(l).trim();
                        anchorAuthorizedEntity.setMobile(mobile);
//                            anchorGstEntity.setPincode(cell.getStringCellValue());
                        break;

                    case 3:
                        anchorAuthorizedEntity.setEmailId(cell.getStringCellValue().trim());
                        break;

                    case 4:
                        anchorAuthorizedEntity.setIndemnityDoc(cell.getStringCellValue().trim());
                        break;

                    default:
                        break;
                }
                cid++;
            }
            anchorAuthorizedEntity.setApplicationEntity(applicationEntity);
            list.add(anchorAuthorizedEntity);
        }
        return validate(list);
    }catch (Exception ex){
        ex.printStackTrace();
            logger.error("Error Message:Error While Logging to Anchor Address :: "+ex.getMessage());
        }
        return null;
    }

    private List<AnchorAuthorizedEntity> validate(@Valid List<AnchorAuthorizedEntity> anchorAuthorizedEntities) {
        return anchorAuthorizedEntities;
    }
}
