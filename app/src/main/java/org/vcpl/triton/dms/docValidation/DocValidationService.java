package org.vcpl.triton.dms.docValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.DocumentReportsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocValidationService {

    @Autowired
    private DocumentTypeRepository documentTypesRepository;

    @Autowired
    private DocumentReportsRepository documentReportRepository;

    @Autowired
    private DocumentReportsRepository documentReportsRepository;

    @Value("${fileLimit}")
    private Integer fileLimit;

    List<DocumentListEntity> anchorOpsMakerMandatory = new ArrayList<>();
    List<DocumentListEntity> cpUploadDataKycMandatory = new ArrayList<>();
    List<DocumentListEntity> cpUploadDataCreditMandatory = new ArrayList<>();
    List<DocumentListEntity> cpCamMandatory = new ArrayList<>();
    List<DocumentListEntity> cpOpsMakerMandatory = new ArrayList<>();

    public String documentCheck(Long appId,Long docTypeId,Long docCategoryId,Long docListId,String fileName) {
        List<DocumentReportsEntity> document = documentReportsRepository.documentCheck(appId, docTypeId, docCategoryId,docListId);
        if (document.size() != 0 && document.size() < fileLimit) {
            String bool = "success";
            for (DocumentReportsEntity documentReportsEntity : document) {
                if (documentReportsEntity.getFileName().equals(fileName)) {
                    bool="sameFile";
                    break;
                } else {
                    bool = "success";
                }
            }
            return bool;
        }else if (document.size() >= fileLimit){
            return "fileLimitFailed";
        }else{
            return "success";
        }

    }

    public String documentValidation(DocValidationMasterData docValidationMasterData){
        String result;
        if(docValidationMasterData.getAssessmentType() != null && !docValidationMasterData.getAssessmentType().equals("none")) {
            getDocumentList(docValidationMasterData);
            result = validateDocuments(docValidationMasterData.getCustomerType(), docValidationMasterData.getAppId());
        }else {
            result = "Kindly select assessment type";
        }
        return result;
    }

    public void getDocumentList(DocValidationMasterData docValidationMasterData){
        anchorOpsMakerMandatory.clear();
        cpUploadDataKycMandatory.clear();
        cpUploadDataCreditMandatory.clear();
        cpCamMandatory.clear();
        cpOpsMakerMandatory.clear();
        List<DocumentTypeEntity> documentListEntities = documentTypesRepository.findAll(Sort.by(Sort.Direction.ASC, "sequenceNo"));
        for (var docType : documentListEntities) {
            if(docType.type==1){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkMandatory(docType.type,docList, docValidationMasterData)){
                            anchorOpsMakerMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==2){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkMandatory(docType.type,docList, docValidationMasterData)){
                            cpUploadDataKycMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==3){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkMandatory(docType.type,docList, docValidationMasterData)){
                            cpUploadDataCreditMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==4){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkMandatory(docType.type,docList, docValidationMasterData)){
                            cpCamMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==5){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkMandatory(docType.type,docList, docValidationMasterData)){
                            cpOpsMakerMandatory.add(docList);
                        }
                    }
                }
            }
        }
    }

    public Boolean checkMandatory(int key,DocumentListEntity documentListEntity, DocValidationMasterData docValidationMasterData){
        Boolean flag = false;
        switch (key){
            case 1:
                if(documentListEntity.getMandatory() == 1 && documentListEntity.getDeferral() == 0){
                    flag = true;
                }else if(documentListEntity.getMandatory() == 0 && documentListEntity.getDeferral() == 0){
                    flag = false;
                }else if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                        }
                    }
                }
                return flag;

            case 2:
                if(docValidationMasterData.getConstitution().equals(documentListEntity.getDocumentCategoryEntity().getDisplayName())) {
                    if(documentListEntity.getMandatory() == 1 && documentListEntity.getDeferral() == 0){
                        flag = true;
                    }else if(documentListEntity.getMandatory() == 0 && documentListEntity.getDeferral() == 0){
                        flag = false;
                    }else if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                        for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                            if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                                if(docValidData.getMandatory()){
                                    flag = true;
                                }else{
                                    flag = false;
                                }
                                break;
                            }
                        }
                    }
                }
                return flag;

            case 3:
                if(docValidationMasterData.getConstitution().equals(documentListEntity.getDocumentCategoryEntity().getCategory()) &&  docValidationMasterData.getAnchorType().equals(documentListEntity.getDocumentCategoryEntity().getDisplayName()) && docValidationMasterData.getAssessmentType().equals(documentListEntity.getCategory())) {
                    if (documentListEntity.getMandatory() == 1 && documentListEntity.getDeferral() == 0) {
                        flag = true;
                    } else if (documentListEntity.getMandatory() == 0 && documentListEntity.getDeferral() == 0) {
                        flag = false;
                    } else if (documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0) {
                        for (DocValidationData docValidData : docValidationMasterData.getDocValidationData()) {
                            if (docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())) {
                                if(docValidData.getMandatory()) {
                                    flag = true;
                                } else {
                                    flag = false;
                                }
                                break;
                            }
                        }
                    }
                }
                return flag;

            case 4:
                if(documentListEntity.getMandatory() == 1 && documentListEntity.getDeferral() == 0){
                    flag = true;
                }else if(documentListEntity.getMandatory() == 0 && documentListEntity.getDeferral() == 0){
                    flag = false;
                }else if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            break;
                        }
                    }
                }
                return flag;

            case 5:
                if(documentListEntity.getMandatory() == 1 && documentListEntity.getDeferral() == 0){
                    flag = true;
                }else if(documentListEntity.getMandatory() == 0 && documentListEntity.getDeferral() == 0){
                    flag = false;
                }else if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            break;
                        }
                    }
                }
                return flag;

        }
        return flag;
    }

    public String validateDocuments(int key,long appId){
        String result;
        Boolean flag = true;
        Boolean flag1 = true;
        List<DocumentReportsEntity> documentReportEntities = documentReportRepository.findCustomerDocReports(appId);
        switch (key) {
            case 1:
                for(var manDoc : anchorOpsMakerMandatory){
                    for(var document : documentReportEntities){
                        if(manDoc.getId().equals(document.getDocumentListEntity().getId())){
                            flag=false;
                            break;
                        }else{
                            flag=true;
                        }
                    }
                    if(flag){
                        return "Kindly Upload "+manDoc.getDisplayName();
                    }
                }
                result = "success";
                break;
            case 2:
                for(var manDoc : cpUploadDataCreditMandatory){
                    for(var document : documentReportEntities){
                        if(manDoc.getId().equals(document.getDocumentListEntity().getId())){
                            flag1=false;
                            break;
                        }else{
                            flag1=true;
                        }
                    }
                    if(flag1){
                        return "Kindly Upload "+manDoc.getDisplayName();
                    }
                }
                for(var manDoc : cpUploadDataKycMandatory){
                    for(var document : documentReportEntities){
                        if(manDoc.getId().equals(document.getDocumentListEntity().getId())){
                            flag=false;
                            break;
                        }else{
                            flag=true;
                        }
                    }
                    if(flag){
                        return "Kindly Upload "+manDoc.getDisplayName();
                    }
                }
                result = "success";
                break;
            case 4:
                for(var manDoc : cpCamMandatory){
                    for(var document : documentReportEntities){
                        if(manDoc.getId().equals(document.getDocumentListEntity().getId())){
                            flag=false;
                            break;
                        }else{
                            flag=true;
                        }
                    }
                    if(flag){
                        return "Kindly Upload "+manDoc.getDisplayName();
                    }
                }
                result = "success";
                break;
            case 5:
                for(var manDoc : cpOpsMakerMandatory){
                    for(var document : documentReportEntities){
                        if(manDoc.getId().equals(document.getDocumentListEntity().getId())){
                            flag=false;
                            break;
                        }else{
                            flag=true;
                        }
                    }
                    if(flag){
                        return "Kindly Upload "+manDoc.getDisplayName();
                    }
                }
                result = "success";
                break;
            default:
                result = "Invalid Parameter";
        }
        return result;
    }

    // Only Conditional Mandatory
    public String conDocValidation(DocValidationMasterData docValidationMasterData){
        getConDocList(docValidationMasterData);
        String result = validateDocuments(docValidationMasterData.getCustomerType(), docValidationMasterData.getAppId());
        return result;
    }

    public void getConDocList(DocValidationMasterData docValidationMasterData){
        anchorOpsMakerMandatory.clear();
        cpUploadDataKycMandatory.clear();
        cpUploadDataCreditMandatory.clear();
        cpCamMandatory.clear();
        cpOpsMakerMandatory.clear();
        List<DocumentTypeEntity> documentListEntities = documentTypesRepository.findAll(Sort.by(Sort.Direction.ASC, "sequenceNo"));
        for (var docType : documentListEntities) {
            if(docType.type==1){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkConDocMandatory(docType.type,docList, docValidationMasterData)){
                            anchorOpsMakerMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==2){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkConDocMandatory(docType.type,docList, docValidationMasterData)){
                            cpUploadDataKycMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==3){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkConDocMandatory(docType.type,docList, docValidationMasterData)){
                            cpUploadDataCreditMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==4){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkConDocMandatory(docType.type,docList, docValidationMasterData)){
                            cpCamMandatory.add(docList);
                        }
                    }
                }
            }
            if(docType.type==5){
                for(var docCategory : docType.getDocumentCategoryEntities()){
                    for(var docList : docCategory.getDocumentListEntities()){
                        if(checkConDocMandatory(docType.type,docList, docValidationMasterData)){
                            cpOpsMakerMandatory.add(docList);
                        }
                    }
                }
            }
        }
    }

    public Boolean checkConDocMandatory(int key,DocumentListEntity documentListEntity, DocValidationMasterData docValidationMasterData){
        Boolean flag = false;
        switch (key){
            case 1:
                if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                        }
                    }
                }
                return flag;

            case 2:
                if(docValidationMasterData.getConstitution().equals(documentListEntity.getDocumentCategoryEntity().getDisplayName())) {
                    if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                        for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                            if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                                if(docValidData.getMandatory()){
                                    flag = true;
                                }else{
                                    flag = false;
                                }
                                break;
                            }
                        }
                    }
                }
                return flag;

            case 3:
                if(docValidationMasterData.getConstitution().equals(documentListEntity.getDocumentCategoryEntity().getCategory()) &&  docValidationMasterData.getAnchorType().equals(documentListEntity.getDocumentCategoryEntity().getDisplayName()) && docValidationMasterData.getAssessmentType().equals(documentListEntity.getCategory())) {
                    if (documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0) {
                        for (DocValidationData docValidData : docValidationMasterData.getDocValidationData()) {
                            if (docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())) {
                                if(docValidData.getMandatory()) {
                                    flag = true;
                                } else {
                                    flag = false;
                                }
                                break;
                            }
                        }
                    }
                }
                return flag;

            case 4:
                if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            break;
                        }
                    }
                }
                return flag;

            case 5:
                if(documentListEntity.getMandatory() == 2 && documentListEntity.getDeferral() == 0){
                    for(DocValidationData docValidData : docValidationMasterData.getDocValidationData()){
                        if( docValidData.getDocTypeId().equals(documentListEntity.getDocumentCategoryEntity().getDocumentTypeEntity().getId()) && docValidData.getDocCategoryId().equals(documentListEntity.getDocumentCategoryEntity().getId()) && docValidData.getDocListId().equals(documentListEntity.getId())){
                            if(docValidData.getMandatory()){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            break;
                        }
                    }
                }
                return flag;

        }
        return flag;
    }

}
