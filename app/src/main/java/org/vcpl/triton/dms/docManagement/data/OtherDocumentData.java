package org.vcpl.triton.dms.docManagement.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class OtherDocumentData {

    private Long id;

    @NotNull
    private Long appId;

//    @NotNull
    private int sequenceNo;

    private  int deferralType;

    @NotNull
    private Long docListId;

    @NotNull
    private String displayName;

    @NotNull
    private String initialTime;

    private String revisedTime;

    private LocalDate exRevisedTime;

    @NotNull
    private int status;

    private int deferral;

    private String rmName;

    private String createdBy;

    private String updatedBy;

    public OtherDocumentData() throws NoSuchFieldException {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getDeferralType() {
        return deferralType;
    }

    public void setDeferralType(int deferralType) {
        this.deferralType = deferralType;
    }

    public Long getDocListId() {
        return docListId;
    }

    public void setDocListId(Long docListId) {
        this.docListId = docListId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String  getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public String getRevisedTime() {
        return revisedTime;
    }

    public void setRevisedTime(String revisedTime) {
        this.revisedTime = revisedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeferral() {
        return deferral;
    }

    public void setDeferral(int deferral) {
        this.deferral = deferral;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getExRevisedTime() {
        return exRevisedTime;
    }

    public void setExRevisedTime(LocalDate exRevisedTime) {
        this.exRevisedTime = exRevisedTime;
    }
}
