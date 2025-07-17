package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnchorAddress {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadService.class);

    @Autowired
    private CustomerInfoRepository customerInfoRepository;


    public static boolean checkExcelFormat(MultipartFile file)
    {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }else {
            return false;
        }
    }


    public  List<AnchorAddressEntity> convertExcelToListOfAnchorAddress(InputStream inputStream, ApplicationEntity applicationEntity) throws IOException {
        try{
            logger.info(" | URL | Logging to Anchor Address | OPERATION | " + "Message: Logging to Anchor Address");

        List<AnchorAddressEntity> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Registered Address");
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

                AnchorAddressEntity anchorAddressDetailsEntity = new AnchorAddressEntity();

                while (cells.hasNext())
                {
                    Cell cell = cells.next();

                    switch (cid)
                    {
                        case 0:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                int add = ((int) cell.getNumericCellValue());
                                anchorAddressDetailsEntity.setAddressLine1(String.valueOf(add));
                            }else if(cell.getCellType() == CellType.STRING) {
                                anchorAddressDetailsEntity.setAddressLine1(cell.getStringCellValue().trim());
                            }
                            break;

                        case 1:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                int add = ((int) cell.getNumericCellValue());
                                anchorAddressDetailsEntity.setAddressLine2(String.valueOf(add));
                            }else if(cell.getCellType() == CellType.STRING) {
                                anchorAddressDetailsEntity.setAddressLine2(cell.getStringCellValue().trim());
                            }
                            break;

                        case 2:
                            anchorAddressDetailsEntity.setCity(cell.getStringCellValue().trim());
                            break;

                        case 3:
                            anchorAddressDetailsEntity.setState(cell.getStringCellValue().trim());
                            break;

                        case 4:
                            anchorAddressDetailsEntity.setCountry(cell.getStringCellValue().trim());
                            break;

                        case 5:
                            long pinCode=(long)cell.getNumericCellValue();
                            anchorAddressDetailsEntity.setPinCode(String.valueOf(pinCode));

                            break;

                        default:
                            break;
                    }
                    cid++;
                }

                anchorAddressDetailsEntity.setApplicationEntity(applicationEntity);
                list.add(anchorAddressDetailsEntity);
            }
        return validate(list);
    }catch (Exception ex){
        ex.printStackTrace();
            logger.error("Error Message: Error while reading Anchor Address :: "+ex.getMessage());
        }
        return null;
    }

    private List<AnchorAddressEntity> validate(@Valid List<AnchorAddressEntity> anchorAddressEntity)
    {
        return anchorAddressEntity;
    }

}
