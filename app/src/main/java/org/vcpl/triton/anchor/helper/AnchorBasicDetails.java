package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnchorBasicDetails {

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


    public List<AnchorBasicEntity> convertExcelToListOfAnchorBasic(InputStream inputStream, ApplicationEntity applicationEntity,CustomerInfoEntity customerInfoEntity) throws IOException, ParseException {
        try{

            logger.info(" | URL | Logging to Anchor Basic Details | OPERATION | " + "Message: Logging to Anchor Basic Details");
        List<AnchorBasicEntity> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Basic Details");
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

                AnchorBasicEntity anchorBasicDetailsEntity = new AnchorBasicEntity();


                while (cells.hasNext())
                {
                    Cell cell = cells.next();

                    switch (cid)
                    {

                        case 0:
                            anchorBasicDetailsEntity.setIndustry(cell.getStringCellValue().trim());
                            break;

                        case 1:
                            anchorBasicDetailsEntity.setSector(cell.getStringCellValue().trim());
                            break;

                        case 2:
                            anchorBasicDetailsEntity.setActivity(cell.getStringCellValue().trim());
                            break;

                        case 3:
                            anchorBasicDetailsEntity.setTestIncorpDate(cell.getDateCellValue());
                            break;

                        case 4:
                            anchorBasicDetailsEntity.setConstitution(cell.getStringCellValue().trim());
                            break;

                        default:
                            break;
                    }
                    cid++;
                }
                anchorBasicDetailsEntity.setApplicationEntity(applicationEntity);
                anchorBasicDetailsEntity.setAnchorName(customerInfoEntity.getCustomerName());
                anchorBasicDetailsEntity.setCin(customerInfoEntity.getCin());
                anchorBasicDetailsEntity.setPan(customerInfoEntity.getPan());
                anchorBasicDetailsEntity.setBusinessExpiry("12");
                list.add(anchorBasicDetailsEntity);
            }
        return  validate(list);
    }catch (Exception ex){
            ex.printStackTrace();
            logger.error("Error Message:Error While Logging to Anchor Basic Details"+ex.getMessage());
        }
        return null;
    }

    private List<AnchorBasicEntity> validate(@Valid List<AnchorBasicEntity> anchorBasicEntities)
    {
        return anchorBasicEntities;
    }

}
