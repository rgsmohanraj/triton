package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnchorGst {

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


    public List<AnchorGstEntity> convertExcelToListOfAnchorGst(InputStream inputStream, ApplicationEntity applicationEntity) throws IOException {
        try {

            logger.info(" | URL | Logging to Anchor GST | OPERATION | " + "Message: Logging to Anchor GST");
        List<AnchorGstEntity> list = new ArrayList<>();


            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("GST");
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

                AnchorGstEntity anchorGstEntity = new AnchorGstEntity();



                while (cells.hasNext())
                {
                    Cell cell = cells.next();

                    switch (cid)
                    {
                        case 0:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                int add = ((int) cell.getNumericCellValue());
                                anchorGstEntity.setAddressLine1(String.valueOf(add));
                            }else if(cell.getCellType() == CellType.STRING) {
                                anchorGstEntity.setAddressLine1(cell.getStringCellValue().trim());
                            }
                            break;

                        case 1:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                int add = ((int) cell.getNumericCellValue());
                                anchorGstEntity.setAddressLine2(String.valueOf(add));
                            }else if(cell.getCellType() == CellType.STRING) {
                                anchorGstEntity.setAddressLine2(cell.getStringCellValue().trim());
                            }
                            break;

                        case 2:
                            anchorGstEntity.setCity(cell.getStringCellValue().trim());
                            break;

                        case 3:
                            anchorGstEntity.setState(cell.getStringCellValue().trim());
                            break;

                        case 4:
                            anchorGstEntity.setCountry(cell.getStringCellValue().trim());
                            break;

                        case 5:
//                            String pin = String.valueOf(cell.getNumericCellValue()).substring(0,String.valueOf(cell.getNumericCellValue()).length()-2);
//                            anchorGstEntity.setPincode(pin);
////                            anchorGstEntity.setPincode(cell.getStringCellValue());
//                            break;
                        double pin = cell.getNumericCellValue();
                        long l = (new Double (pin)).longValue();
                        String pincode = String.valueOf(l).trim();
                       anchorGstEntity.setPincode(pincode);
                        break;

                        case 6:
                            anchorGstEntity.setGstNumber(cell.getStringCellValue().trim());
                            break;

                        case 7:
                            anchorGstEntity.setGstAcctHolderName(cell.getStringCellValue().trim());
                            break;

                        default:
                            break;
                    }
                    cid++;
                }
                anchorGstEntity.setApplicationEntity(applicationEntity);
                list.add(anchorGstEntity);
            }
        return validate(list);
    }catch (Exception ex){
        ex.printStackTrace();
        logger.error(" Error Message:Error While Logging to Anchor GST :: "+ex.getMessage());
        }
        return null;
    }
    private List<AnchorGstEntity> validate(@Valid List<AnchorGstEntity> anchorGstEntities)
    {
        return anchorGstEntities;
    }

}
