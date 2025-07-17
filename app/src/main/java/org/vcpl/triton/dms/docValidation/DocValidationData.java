package org.vcpl.triton.dms.docValidation;

import javax.validation.constraints.NotNull;

public class DocValidationData {
    @NotNull
    private Long appId;

    @NotNull
    private Boolean mandatory;

    private Long docTypeId;

    private Long docCategoryId;

    private Long docListId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Long getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Long docTypeId) {
        this.docTypeId = docTypeId;
    }

    public Long getDocCategoryId() {
        return docCategoryId;
    }

    public void setDocCategoryId(Long docCategoryId) {
        this.docCategoryId = docCategoryId;
    }

    public Long getDocListId() {
        return docListId;
    }

    public void setDocListId(Long docListId) {
        this.docListId = docListId;
    }
}
