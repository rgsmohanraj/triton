package org.vcpl.triton.dms.docValidation;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsData;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentData;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentMasterData;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;
import org.vcpl.triton.dms.docManagement.repository.OtherDocumentReportsRepository;

import java.util.Collection;
import java.util.List;

@Component
public class OtherDocValidationService {

    @Value("${fileLimit}")
    private Integer fileLimit;

    @Autowired
    private OtherDocumentReportsRepository otherDocumentReportsRepository;

    public String otherDocCheck(OtherDocumentMasterData otherDocumentMasterData) {
        String result = "success";
        Boolean flag = false;
        JSONObject mainContainer = new JSONObject(otherDocumentMasterData);
        Collection<OtherDocumentReportsEntity> otherDocumentReportsEntities = otherDocumentReportsRepository.findCustomerDocReports(otherDocumentMasterData.getAppId());
        for(OtherDocumentData otherDocumentData : otherDocumentMasterData.getOtherDocumentDataList()){
            JSONObject containerObject = new JSONObject(otherDocumentData);
            if(!containerObject.has("displayName") || otherDocumentData.getDisplayName() == "") {
                return "Kindly enter valid document name";
            }else{
                if (!containerObject.has("status") || otherDocumentData.getStatus() == -1) {
                    return "Kindly select deferral status for '" +otherDocumentData.getDisplayName()+"'";
                }else{
                    if(otherDocumentData.getStatus() == 2){
                        if(otherDocumentReportsEntities.size() != 0) {
                            for (OtherDocumentReportsEntity document : otherDocumentReportsEntities) {
                                if(containerObject.has("id") || otherDocumentData.getId() != null){
                                    if (otherDocumentData.getDisplayName().equals(document.getOtherDocumentMasterEntity().getDisplayName()) || otherDocumentData.getId().equals(document.getOtherDocumentMasterEntity().getId())) {
                                        flag = false;
                                        break;
                                    } else {
                                        flag = true;
                                    }
                                }else {
                                    if (otherDocumentData.getDisplayName().equals(document.getOtherDocumentMasterEntity().getDisplayName())) {
                                        flag = false;
                                        break;
                                    } else {
                                        flag = true;
                                    }
                                }
                            }
                            if (flag) {
                                return "Kindly upload document for '" + otherDocumentData.getDisplayName()+"'";
                            }
                        }else {
                            return "Kindly upload document for '" + otherDocumentData.getDisplayName()+"'";
                        }
                    }else if(otherDocumentData.getStatus() == 0){
                        if(otherDocumentReportsEntities.size() != 0) {
                            for (OtherDocumentReportsEntity document : otherDocumentReportsEntities) {
                                if (otherDocumentData.getDisplayName().equals(document.getOtherDocumentMasterEntity().getDisplayName())) {
                                    return "Document uploaded so change the deferral status for '"+otherDocumentData.getDisplayName()+"' or Delete the uploaded file";
                                } else {
                                    flag = true;
                                }
                            }
                            if (flag) {
                                if (!containerObject.has("initialTime") || otherDocumentData.getInitialTime() == null || otherDocumentData.getInitialTime() == "") {
                                    return "Kindly select deferral date for '" +otherDocumentData.getDisplayName()+"'";
                                }
                            }
                        }else{
                            if (!containerObject.has("initialTime") || otherDocumentData.getInitialTime() == null || otherDocumentData.getInitialTime() == "") {
                                return "Kindly select deferral date for '" +otherDocumentData.getDisplayName()+"'";
                            }
                        }
                        if(!mainContainer.has("rmName") || otherDocumentMasterData.getRmName() == null){
                            return "Kindly select the RM Name";
                        }
                    }

                }
            }
        }
        return result;
    }

    public String otherDocValidation(OtherDocumentData otherDocumentData,String otherDisplayName) {
        String result = "success";
        Boolean flag = false;
        Collection<OtherDocumentReportsEntity> otherDocumentReportsEntities = otherDocumentReportsRepository.findCustomerDocReports(otherDocumentData.getAppId());
        if(otherDisplayName != null){
            JSONObject containerObject = new JSONObject(otherDocumentData);
            if(!containerObject.has("displayName") || otherDocumentData.getDisplayName() == "") {
                return "Kindly enter valid document name";
            }else{
                if (!containerObject.has("status") || otherDocumentData.getStatus() == -1) {
                    return "Kindly select deferral status for '" +otherDocumentData.getDisplayName()+"'";
                }else{
                    if(otherDocumentData.getStatus() == 2){
                        return result;
                    }else if(otherDocumentData.getStatus() == 0){
                        if (!containerObject.has("initialTime") || otherDocumentData.getInitialTime() == null || otherDocumentData.getInitialTime() == "") {
                            return "Kindly select deferral date for '" +otherDocumentData.getDisplayName()+"'";
                        }
                    }
                }
            }
        }else {
            return "Kindly enter valid document name";
        }

        return result;
    }


    public String documentCheck(Long appId,Long docTypeId,Long docCategoryId,Long docListId,Long otherDocMasterId,String fileName) {
        List<OtherDocumentReportsEntity> document = otherDocumentReportsRepository.documentCheck(appId, docTypeId, docCategoryId,docListId,otherDocMasterId);
        if (document.size() != 0 && document.size() < fileLimit) {
            String bool = "success";
            for (OtherDocumentReportsEntity otherDocumentReportsEntity : document) {
                if (otherDocumentReportsEntity.getFileName().equals(fileName)) {
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
}
