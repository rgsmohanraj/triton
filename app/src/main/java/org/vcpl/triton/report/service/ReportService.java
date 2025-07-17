package org.vcpl.triton.report.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.repository.ProposedProductsRepository;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;
import org.vcpl.triton.workflow.repository.WFApprovalStatusRepository;
import org.vcpl.triton.workflow.service.WorkflowStageService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private WFApprovalStatusRepository wfApprovalStatusRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WorkflowStageService workflowStageService;

    @Autowired
    private ProposedProductsRepository proposedProductsRepository;

    public Workbook downloadFullReport(Workbook workbook){
        Sheet anchorSheet = workbook.createSheet("Anchor");
        Sheet cpSheet=workbook.createSheet("Counterparty");

//        CellStyle style=workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
//        style.setFont(boldFont);
//        style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle coloredBackgroundStyle = workbook.createCellStyle();
        coloredBackgroundStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        coloredBackgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        coloredBackgroundStyle.setFont(boldFont);


        Row anchorRow = anchorSheet.createRow(0);
        anchorRow.createCell(0).setCellStyle(coloredBackgroundStyle);
        anchorRow.getCell(0).setCellValue("S.NO");
        anchorRow.createCell(1).setCellStyle(coloredBackgroundStyle);
        anchorRow.getCell(1).setCellValue("Anchor Name");
        anchorRow.createCell(2).setCellStyle(coloredBackgroundStyle);
        anchorRow.getCell(2).setCellValue("Current Owner");
        anchorRow.createCell(3).setCellStyle(coloredBackgroundStyle);
        anchorRow.getCell(3).setCellValue("Stage Completion");
        anchorRow.createCell(4).setCellStyle(coloredBackgroundStyle);
        anchorRow.getCell(4).setCellValue("Status");


        Row cpRow=cpSheet.createRow(0);

        cpRow.createCell(0).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(0).setCellValue("S.NO");
        cpRow.createCell(1).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(1).setCellValue("Counterparty Name");
        cpRow.createCell(2).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(2).setCellValue("Anchor Name");
        cpRow.createCell(3).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(3).setCellValue("Current Owner");
        cpRow.createCell(4).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(4).setCellValue("Stage Completion");
        cpRow.createCell(5).setCellStyle(coloredBackgroundStyle);
        cpRow.getCell(5).setCellValue("Status");



        int anchorCnt=0;
        int cpCnt=0;
        List<Object[]> customerInfos=customerInfoRepository.getAllActiveData();
        for (Object[] customerInfo : customerInfos) {
            try {
                anchorRow = anchorSheet.createRow(anchorCnt + 1);
                cpRow = cpSheet.createRow(cpCnt + 1);
                BigInteger temp= (BigInteger) customerInfo[1];
                long appId=temp.longValueExact();
                WFApprovalStatusEntity currentOwner = wfApprovalStatusRepository.findByApplicationId(appId);
                if (customerInfo[3].equals(1)) {
                    anchorRow.createCell(0).setCellValue(anchorCnt + 1);
                    anchorRow.createCell(1).setCellValue(customerInfo[0].toString());
                    if(currentOwner!=null) {
                        anchorRow.createCell(2).setCellValue(this.emailService.emailRemoveSpaceAndSpacelChar(currentOwner.getApproverInfo()));
                        String stageValue = getCurrentStageValue(currentOwner.getStage().getStageId());
                        anchorRow.createCell(3).setCellValue(stageValue.equals("6")&&currentOwner.getStatus()==2||currentOwner.getStatus()==0?stageValue+"/6":stageValue+"/5");
                        anchorRow.createCell(4).setCellValue(currentOwner.getStatus()==2?"Onboarded":"In-Progress");
                    }
                    anchorCnt++;
                } else if (customerInfo[3].equals(2)) {
                    cpRow.createCell(0).setCellValue(cpCnt + 1);
                    cpRow.createCell(1).setCellValue(customerInfo[0].toString());
                    Optional<ProposedProductsEntity> proposeList = proposedProductsRepository.getEntityListByAppId(currentOwner.getAppId().getId());
                    cpRow.createCell(2).setCellValue(proposeList.get().getCustomerInfoEntity().getCustomerName());
                    if(currentOwner!=null) {
                        cpRow.createCell(3).setCellValue(this.emailService.emailRemoveSpaceAndSpacelChar(currentOwner.getApproverInfo()));
                    }
                    String stageValue = getCurrentStageValue(currentOwner.getStage().getStageId());
                    cpRow.createCell(4).setCellValue(stageValue.equals("12")&&currentOwner.getStatus()==2||currentOwner.getStatus()==0?stageValue+"/12":stageValue+"/11");
                    cpRow.createCell(5).setCellValue(currentOwner.getStatus()==2?"Onboarded":"In-Progress");
                    cpCnt++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return workbook;
    }

    public String getCurrentStageValue(String stageId){
        return stageId.replaceAll("[a-zA-Z]", "");
    }
}
