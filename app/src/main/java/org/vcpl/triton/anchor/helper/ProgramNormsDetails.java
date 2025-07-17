package org.vcpl.triton.anchor.helper;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;
import org.vcpl.triton.anchor.service.AnchorFileUploadService;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgramNormsDetails {

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


    public List<ProgramNormsEntity> convertExcelToListOfProgramNorms(InputStream inputStream) throws IOException, ParseException {
        try {
            logger.info(" | URL | Logging to ProgramNormsService | OPERATION | " + "Message: Logging to ProgramNormsService");
            List<ProgramNormsEntity> list = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Program Norms");
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

                ProgramNormsEntity programDetailsEntity = new ProgramNormsEntity();


                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            programDetailsEntity.setFundingType(cell.getStringCellValue());
                            break;

                        case 1:
                            programDetailsEntity.setOverallProgLimit(new BigDecimal(cell.getNumericCellValue()));
                            break;

                        case 2:
                            programDetailsEntity.setRegularProgLimit(new BigDecimal(cell.getNumericCellValue()));
                            break;

                        case 3:
                            programDetailsEntity.setAdhocProgLimit(new BigDecimal(cell.getNumericCellValue()));
                            break;

                        case 4:
                            programDetailsEntity.setDate(cell.getDateCellValue(), null);
                            break;

                        case 5:
                            programDetailsEntity.setCpMaxLimit((Double) cell.getNumericCellValue());
                            break;

                        case 6:
                            programDetailsEntity.setCpMinLimit((Double) cell.getNumericCellValue());
                            break;

                        case 7:
                            programDetailsEntity.setPricingRoiMax((Double) cell.getNumericCellValue());
                            break;

                        case 8:
                            programDetailsEntity.setPricingRoiMin((Double) cell.getNumericCellValue());
                            break;


                        case 9:
                            programDetailsEntity.setInterestAppTye(cell.getStringCellValue());
                            break;

                        case 10:
                            programDetailsEntity.setSubProduct(cell.getStringCellValue());
                            break;

                        case 11:
                            programDetailsEntity.setInterestOwnerShip(cell.getStringCellValue());
                            break;

                        case 12:
                            programDetailsEntity.setPenalInterestOwnerShip(cell.getStringCellValue());
                            break;


                        case 13:
                            programDetailsEntity.setInterestPaymentFrequency(cell.getStringCellValue());
                            break;


                        case 14:
                            programDetailsEntity.setPenalInterest((Double) cell.getNumericCellValue());
                            break;


                        case 15:
                            programDetailsEntity.setTenure(BigDecimal.valueOf((Double) cell.getNumericCellValue()));
                            break;

                        case 16:
                            programDetailsEntity.setInvoiceAgeing(BigDecimal.valueOf((Double) cell.getNumericCellValue()));
                            break;

                        case 17:
                            programDetailsEntity.setDoorToDoor(BigDecimal.valueOf((Double) cell.getNumericCellValue()));
                            break;

                        case 18:
                            programDetailsEntity.setFundingPercent((Double) cell.getNumericCellValue());
                            break;


                        case 19:
                            programDetailsEntity.setSecurityCovgPrimary(cell.getStringCellValue());
                            break;

                        case 20:
                            programDetailsEntity.setSecurityCovgSecondry(cell.getStringCellValue());
                            break;

                        case 21:
                            programDetailsEntity.setProcessingFeesMin((Double) cell.getNumericCellValue());
                            break;

                        case 22:
                            programDetailsEntity.setProcessingFeesMax((Double) cell.getNumericCellValue());
                            break;

                        case 23:
                            programDetailsEntity.setInterestCalculation(cell.getStringCellValue());
                            break;

                        case 24:
                            programDetailsEntity.setCounterPartyGracePeriod((Double) cell.getNumericCellValue());
                            break;

                        case 25:
                            programDetailsEntity.setMaxDrawdown((Double) cell.getNumericCellValue());
                            break;

                        case 26:
                            programDetailsEntity.setStopSupplyTriggerDays((Double) cell.getNumericCellValue());
                            break;

//                    case 24:
//                        programDetailsEntity.setRepaymentNature(cell.getStringCellValue());
//                        break;

                        case 27:
                            programDetailsEntity.setRepaymentType(cell.getStringCellValue());
                            break;

                        case 28:
                            programDetailsEntity.setProgramType(cell.getStringCellValue());
                            break;

                        case 29:
                            programDetailsEntity.setTransactionType(cell.getStringCellValue());
                            break;

                        case 30:
                            programDetailsEntity.setInterimReviewFrequency((Double) cell.getNumericCellValue());
                            break;

                        case 31:
                            programDetailsEntity.setDate(null, cell.getDateCellValue());
                            break;

                        case 32:
                            programDetailsEntity.setCmpdInt(cell.getStringCellValue());
                            break;

                        case 33:
                            programDetailsEntity.setCmpOvdInt(cell.getStringCellValue());
                            break;

//                    case 34:
//                        programDetailsEntity.setRatePenalInterest((int)cell.getNumericCellValue());
//                        break;

                        case 34:
                            programDetailsEntity.setInterestRate((Double) cell.getNumericCellValue());
                            break;

                        case 35:
                            if((int) cell.getNumericCellValue() > 0){
                                programDetailsEntity.setProductExpiry((int) cell.getNumericCellValue());
                                break;
                            }else{
                                return null;
                            }
                        default:
                            break;
                    }
                    cid++;
                }
                BigDecimal overAllProgLimit = programDetailsEntity.getOverallProgLimit();
                BigDecimal regProgLimit = programDetailsEntity.getRegularProgLimit();
                BigDecimal adhocProgLimit = programDetailsEntity.getAdhocProgLimit();
                BigDecimal tenure = programDetailsEntity.getTenure();
                BigDecimal invoiceAgeing = programDetailsEntity.getInvoiceAgeing();
                BigDecimal doorToDoor = programDetailsEntity.getDoorToDoor();

                if ((overAllProgLimit.compareTo(regProgLimit.add(adhocProgLimit)) < 0) || ((doorToDoor.compareTo(tenure.add(invoiceAgeing))) > 0)
                        || (programDetailsEntity.getSubProduct().equalsIgnoreCase("Anchor Sales Bill Discounting") ||
                        programDetailsEntity.getSubProduct().equalsIgnoreCase("Anchor Purchase Bill Discounting"))
                        && (programDetailsEntity.getInterestOwnerShip().equalsIgnoreCase("Counterparty") ||
                        programDetailsEntity.getPenalInterestOwnerShip().equalsIgnoreCase("Counterparty"))) {
                    return null;
                } else {
                    list.add(programDetailsEntity);
                }
            }
            return validate(list);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Error Message:While Reading Program norms sheet ::"+ex.getMessage());
        }
        return null;
    }


    private List<ProgramNormsEntity> validate(@Valid List<ProgramNormsEntity> programNormsEntities)
    {
//         List<ProgramNormsEntity> programNormsEntities1 = programNormsEntities;
         return programNormsEntities;
    }
}
