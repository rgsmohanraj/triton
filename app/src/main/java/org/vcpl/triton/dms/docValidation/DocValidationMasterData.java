package org.vcpl.triton.dms.docValidation;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DocValidationMasterData {

    @NotNull
    private Long appId;

    @NotNull
    private Integer customerType;

    List<DocValidationData> docValidationData;

    private String constitution;

    private String anchorType;

    private String assessmentType;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public List<DocValidationData> getDocValidationData() {
        return docValidationData;
    }

    public void setDocValidationData(List<DocValidationData> docValidationData) {
        this.docValidationData = docValidationData;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getAnchorType() {
        return anchorType;
    }

    public void setAnchorType(String anchorType) {
        this.anchorType = anchorType;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
}
