package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeneficiaryDetails {

    public static boolean checkExcelFormat(MultipartFile file)
    {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }else {
            return false;
        }
    }



    public  List<BeneficiaryEntity> convertExcelToListOfBeneficiary(InputStream inputStream, CustomerInfoEntity customerInfoEntity) throws IOException {
        List<BeneficiaryEntity> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheet("anchor_beneficiary");
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

                BeneficiaryEntity beneficiaryEntity = new BeneficiaryEntity();


                while (cells.hasNext())
                {
                    Cell cell = cells.next();

                    switch (cid)
                    {
                        case 0:
                            beneficiaryEntity.setBenType(cell.getStringCellValue());
                            break;

                        case 1:
                            beneficiaryEntity.setBenName(cell.getStringCellValue());
                            break;

                        case 2:
                            beneficiaryEntity.setBankName(cell.getStringCellValue());
                            break;

                        case 3:
                            beneficiaryEntity.setBankAcctNumber(cell.getStringCellValue());
                            break;

                        case 4:
                            beneficiaryEntity.setBankifscCode(cell.getStringCellValue());
                            break;

                        case 5:
                            beneficiaryEntity.setBankBranchName(cell.getStringCellValue());
                            break;


                        default:
                            break;
                    }
                    cid++;
                }
                list.add(beneficiaryEntity);
            }
        return validate(list);
    }

    private List<BeneficiaryEntity> validate(@Valid List<BeneficiaryEntity> beneficiaryEntities)
    {
        return beneficiaryEntities;
    }
}
