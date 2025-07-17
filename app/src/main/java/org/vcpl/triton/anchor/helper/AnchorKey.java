package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnchorKey {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadService.class);

    public static boolean checkExcelFormat(MultipartFile file)
    {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }else {
            return false;
        }
    }


    public  List<AnchorKeyEntity> convertExcelToListOfAnchorKey(InputStream inputStream, ApplicationEntity applicationEntity) throws IOException {
        try{
            logger.info(" | URL | Logging to Anchor Key | OPERATION | " + "Message: Logging to Anchor Key");

        List<AnchorKeyEntity> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Key Person");

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

                AnchorKeyEntity anchorKeyEntity = new AnchorKeyEntity();


                while (cells.hasNext())
                {
                    Cell cell = cells.next();

                    switch (cid)
                    {
                        case 0:
                            anchorKeyEntity.setType(cell.getStringCellValue().trim());
                            break;

                        case 1:
                            anchorKeyEntity.setName(cell.getStringCellValue().trim());
                            break;

                        case 2:
                            anchorKeyEntity.setEmailId(cell.getStringCellValue().trim());
                            break;

                        case 3:
//                            String mobile = String.valueOf(cell.getNumericCellValue()).substring(0,String.valueOf(cell.getNumericCellValue()).length()-2);
//                            anchorKeyEntity.setMobile(mobile);
//                            break;

                        double mobileNo = cell.getNumericCellValue();
                        long l = (new Double (mobileNo)).longValue();
                        String mobile = String.valueOf(l).trim();
                        anchorKeyEntity.setMobile(mobile);
//                            anchorGstEntity.setPincode(cell.getStringCellValue());
                        break;

                        default:
                            break;
                    }
                    cid++;
                }
                anchorKeyEntity.setApplicationEntity(applicationEntity);
                list.add(anchorKeyEntity);
            }

        return validate(list);
    }catch (Exception ex){
            ex.printStackTrace();
            logger.error("Error Message:Error While Logging to Anchor Key :: "+ex.getMessage());
        }
        return null;
    }

    private List<AnchorKeyEntity> validate(@Valid List<AnchorKeyEntity> anchorKeyEntities)
    {
        return anchorKeyEntities;
    }

}
