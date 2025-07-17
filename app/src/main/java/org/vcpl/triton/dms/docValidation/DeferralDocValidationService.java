package org.vcpl.triton.dms.docValidation;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsData;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsMasterData;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.DocumentReportsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeferralDocValidationService {

    @Autowired
    private DocumentTypeRepository documentTypesRepository;

    @Autowired
    private DocumentReportsRepository documentReportRepository;

    List<DocumentListEntity> anchorDeferralList = new ArrayList<>();
    List<DocumentListEntity> cpDeferralList = new ArrayList<>();

    public String deferralDocCheck(DeferralReportsMasterData deferralReportsMasterData) {
        getDocumentList(deferralReportsMasterData.getDocProductCheck(),deferralReportsMasterData.getConstitution());
        if(deferralReportsMasterData.getDeferralReportsDataList().size() > 0) {
            return validateDocuments(deferralReportsMasterData);
        }else{
            return "success";
        }
    }

    public void getDocumentList(Boolean flag,String constitution){
        anchorDeferralList.clear();
        cpDeferralList.clear();
        List<DocumentTypeEntity> documentTypeEntities = documentTypesRepository.findAll(Sort.by(Sort.Direction.ASC, "sequenceNo"));
        for (var docType : documentTypeEntities) {
            if(docType.type==1){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(docList.getDeferral() == 1 && docList.getType() == 0){
                            anchorDeferralList.add(docList);
                        }else if(docList.getDeferral() == 2 && flag && docList.getType() == 0){
                            if(docList.getId().equals(20161L)){
                                String cons = constitution.replace(" ","");
                                if(cons.equals("PrivateCompany") || cons.equals("PublicCompany-Listed") || cons.equals("PublicCompany-Unlisted")){
                                    anchorDeferralList.add(docList);
                                }
                            }else {
                                anchorDeferralList.add(docList);
                            }
                        }
                    }
                }
            }
            if(docType.type==5){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if((docList.getDeferral() == 1 || docList.getDeferral() == 2) && docList.getType() == 0){
                            cpDeferralList.add(docList);
                        }
                    }
                }
            }
        }
    }

    private String validateDocuments(DeferralReportsMasterData deferralReportsMasterData) {
        String result;
        Boolean flag1 = true;
        List<DocumentReportsEntity> documentReportEntities = documentReportRepository.findCustomerDocReports(deferralReportsMasterData.getAppId());
        switch (deferralReportsMasterData.getCustomerType()){
            case 1:
                for(var manDef : anchorDeferralList) {
                    Boolean flag = true;
                    for (var document : documentReportEntities) {
                        if (manDef.getId().equals(document.getDocumentListEntity().getId())) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        Boolean defFlag = true;
                        for(DeferralReportsData deferral : deferralReportsMasterData.getDeferralReportsDataList()){
                            if (manDef.getId().equals(deferral.getDocListId())){
                                JSONObject containerObject = new JSONObject(deferral);
                                if(!containerObject.has("status")){
                                    return "Kindly Select deferral status for "+manDef.getDisplayName();
                                }else {
                                    if(deferral.getStatus() == 2){
                                        if(manDef.getDeferral() == 2){
                                            if(!containerObject.has("documentId")){
                                                return "Kindly Enter "+manDef.getDisplayName()+" ID";
                                            }else if((deferral.getDocumentId()).replaceAll("\\s", "") == "" || deferral.getDocumentId() == null || !deferral.getDocumentId().matches("^[a-zA-Z0-9]+$")){
                                                return "Kindly Enter Valid "+manDef.getDisplayName()+" ID";
                                            }
                                            if(!containerObject.has("docCompletionDate")){
                                                return "Kindly select "+manDef.getDisplayName()+" completed date";
                                            }
                                        }else if(manDef.getMandatory() == 1){
                                            return "Kindly upload document for "+manDef.getDisplayName();
                                        }
                                    }
                                }
                                if(!containerObject.has("initialTime") || deferral.getInitialTime() == null || deferral.getInitialTime().equals("")){
                                    if(deferral.getStatus() == 0){
                                        return "Kindly Select deferral date for "+manDef.getDisplayName();
                                    }
                                }
                                if(manDef.getType() == 1){
                                    if(!containerObject.has("documentName")){
                                        return "Kindly enter deferral document name for "+manDef.getDisplayName();
                                    }
                                }
                                defFlag = false;
                            }
                        }
                        if(defFlag && manDef.getMandatory() != 0){
                            return "Kindly Select "+manDef.getDisplayName()+" deferral type";
                        }
                    }else{
                        Boolean check = true;
                        for(DeferralReportsData deferral : deferralReportsMasterData.getDeferralReportsDataList()){
                            if(deferral.getDocListId().equals(manDef.getId())){
                                if(deferral.getStatus() == 0){
                                    return "Document uploaded so change the deferral status for "+manDef.getDisplayName();
                                }else{
                                    if(deferral.getStatus() == 2 && manDef.getDeferral() == 2){
                                        JSONObject containerObject = new JSONObject(deferral);
                                        if(!containerObject.has("documentId")){
                                            return "Kindly Enter "+manDef.getDisplayName()+" ID";
                                        }else if((deferral.getDocumentId()).replaceAll("\\s", "") == "" || deferral.getDocumentId() == null || !deferral.getDocumentId().matches("^[a-zA-Z0-9]+$")){
                                            return "Kindly Enter Valid "+manDef.getDisplayName()+" ID";
                                        } if(!containerObject.has("docCompletionDate")){
                                            return "Kindly select "+manDef.getDisplayName()+" completed date";
                                        }
                                    }
                                }
                                check = false;
                                break;
                            }
                        }
                        if(check && manDef.getMandatory() != 0){
                            return "Kindly select deferral type for "+manDef.getDisplayName();
                        }
                    }
                }
                for(DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()){
                    JSONObject containerObject = new JSONObject(deferralReportsMasterData);
                    if(deferralReportsData.getStatus() == 0 && !containerObject.has("rmName")){
                        return "Kindly select the RM Name";
                    }
                }
                result = "success";
                break;
            case 2:
                for(var manDef : cpDeferralList) {
                    Boolean flag = true;
                    for (var document : documentReportEntities) {
                        if (manDef.getId().equals(document.getDocumentListEntity().getId())) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        Boolean defFlag = true;
                        for(DeferralReportsData deferral : deferralReportsMasterData.getDeferralReportsDataList()){
                            if (manDef.getId().equals(deferral.getDocListId())){
                                JSONObject containerObject = new JSONObject(deferral);
                                if(!containerObject.has("status")){
                                    return "Kindly Select deferral status for "+manDef.getDisplayName();
                                }else {
                                    if(deferral.getStatus() == 2){
                                        if(manDef.getDeferral() == 2){
                                            if(!containerObject.has("documentId")){
                                                return "Kindly Enter "+manDef.getDisplayName()+" ID";
                                            }else if((deferral.getDocumentId()).replaceAll("\\s", "") == "" || deferral.getDocumentId() == null || !deferral.getDocumentId().matches("^[a-zA-Z0-9]+$")){
                                                return "Kindly Enter Valid "+manDef.getDisplayName()+" ID";
                                            }
                                            if(!containerObject.has("docCompletionDate")){
                                                return "Kindly select "+manDef.getDisplayName()+" completed date";
                                            }
                                        }else if(manDef.getMandatory() == 1){
                                            return "Kindly upload document for "+manDef.getDisplayName();
                                        }
                                    }
                                }
                                if(!containerObject.has("initialTime") || deferral.getInitialTime() == null || deferral.getInitialTime().equals("")){
                                    if(deferral.getStatus() == 0){
                                        return "Kindly Select deferral date for "+manDef.getDisplayName();
                                    }
                                }
                                if(manDef.getType() == 1){
                                    if(!containerObject.has("documentName")){
                                        return "Kindly enter deferral document name for "+manDef.getDisplayName();
                                    }
                                }
                                defFlag = false;
                            }
                        }
                        if(defFlag && manDef.getMandatory() != 0){
                            return "Kindly Select "+manDef.getDisplayName()+" deferral type";
                        }
                    }else{
                        Boolean check = true;
                        for(DeferralReportsData deferral : deferralReportsMasterData.getDeferralReportsDataList()){
                            if(deferral.getDocListId().equals(manDef.getId())){
                                if(deferral.getStatus() == 0){
                                    return "Document uploaded so change the deferral status for "+manDef.getDisplayName();
                                }else{
                                    if(deferral.getStatus() == 2 && manDef.getDeferral() == 2){
                                        JSONObject containerObject = new JSONObject(deferral);
                                        if(!containerObject.has("documentId")){
                                            return "Kindly Enter "+manDef.getDisplayName()+" ID";
                                        }else if((deferral.getDocumentId()).replaceAll("\\s", "") == "" || deferral.getDocumentId() == null || !deferral.getDocumentId().matches("^[a-zA-Z0-9]+$")){
                                            return "Kindly Enter Valid "+manDef.getDisplayName()+" ID";
                                        } if(!containerObject.has("docCompletionDate")){
                                            return "Kindly select "+manDef.getDisplayName()+" completed date";
                                        }
                                    }
                                }
                                check = false;
                                break;
                            }
                        }
                        if(check && manDef.getMandatory() != 0){
                            return "Kindly select deferral type for "+manDef.getDisplayName();
                        }
                    }
                }
                for(DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()){
                    JSONObject containerObject = new JSONObject(deferralReportsMasterData);
                    if(deferralReportsData.getStatus() == 0 && !containerObject.has("rmName")){
                        return "Kindly select the RM Name";
                    }
                }
                result = "success";
                break;
            default:
                result = "Invalid Parameter";
        }
        return result;
    }


    public String deferralDoc(DeferralReportsMasterData deferralReportsMasterData) {
        String file = "true";
        for (DeferralReportsData deferralReportsData : deferralReportsMasterData.getDeferralReportsDataList()) {
            if (deferralReportsData.getStatus() == 2) {
                if(deferralReportsData.getDeferralType() == 2) {
                    String DocumentName = deferralReportsData.getDocumentName();
                    if (deferralReportsData.getDocListId() == 20160) {
                        if (deferralReportsData.getDocumentId() != null && (!deferralReportsData.getDocumentId().isEmpty())) {
                            file = "true";
                        } else {
                           return file = "Please Enter " + DocumentName + " Id";
                        }
                        if (deferralReportsData.getDocCompletionDate() != null) {
                            file = "true";
                        } else {
                            file = "Please Enter " + DocumentName + " Date Of Completion";
                        }
                    }if(deferralReportsData.getDocListId() == 20161){
                        if (deferralReportsData.getDocumentId() != null  && (!deferralReportsData.getDocumentId().isEmpty())) {
                            file = "true";
                        } else {
                           return file = "Please Enter " + DocumentName + " Id";
                        }
                        if (deferralReportsData.getDocCompletionDate() != null) {
                            file = "true";
                        } else {
                           return file = "Please Enter " + DocumentName + " Date Off Completion";
                        }
                    }
                }
            }
        }
        return file;
    }

}
