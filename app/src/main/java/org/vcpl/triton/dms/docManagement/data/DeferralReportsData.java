package org.vcpl.triton.dms.docManagement.data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DeferralReportsData {
    private Long id;

    @NotNull
    private Long appId;

    @NotNull
    private Long docListId;

    private String documentName;

    @NotNull
    private LocalDate initialTime;

    private LocalDate revisedTime;

    @NotNull
    private int status;

    private int deferral;

    private String documentId;

    private LocalDate docCompletionDate;

    @NotNull
    private int deferralType;

    private LocalDate exRevisedTime;

    private int index;


    public LocalDate getExRevisedTime() {
        return exRevisedTime;
    }

    public void setExRevisedTime(LocalDate exRevisedTime) {
        this.exRevisedTime = exRevisedTime;
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

    public Long getDocListId() {
        return docListId;
    }

    public void setDocListId(Long docListId) {
        this.docListId = docListId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDate getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(LocalDate initialTime) {
        this.initialTime = initialTime;
    }

    public LocalDate getRevisedTime() {
        return revisedTime;
    }

    public void setRevisedTime(LocalDate revisedTime) {
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public LocalDate getDocCompletionDate() {
        return docCompletionDate;
    }

    public void setDocCompletionDate(LocalDate docCompletionDate) {
        this.docCompletionDate = docCompletionDate;
    }

    public int getDeferralType() {
        return deferralType;
    }

    public void setDeferralType(int deferralType) {
        this.deferralType = deferralType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
