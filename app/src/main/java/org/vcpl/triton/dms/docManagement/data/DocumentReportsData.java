package org.vcpl.triton.dms.docManagement.data;

import javax.validation.constraints.NotNull;

public class DocumentReportsData {

    private Long id;

    @NotNull
    private Long appId;

    @NotNull
    private Long docTypeId;

    @NotNull
    private String docTypeName;

    @NotNull
    private Long docCategoryId;

    @NotNull
    private String docCategoryName;

    @NotNull
    private Long docListId;

    @NotNull
    private String docListName;

    private String fileName;

    private int key;

    private String version;

    private String createdBy;

    private String updatedBy;

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

    public Long getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Long docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public Long getDocCategoryId() {
        return docCategoryId;
    }

    public void setDocCategoryId(Long docCategoryId) {
        this.docCategoryId = docCategoryId;
    }

    public String getDocCategoryName() {
        return docCategoryName;
    }

    public void setDocCategoryName(String docCategoryName) {
        this.docCategoryName = docCategoryName;
    }

    public Long getDocListId() {
        return docListId;
    }

    public void setDocListId(Long docListId) {
        this.docListId = docListId;
    }

    public String getDocListName() {
        return docListName;
    }

    public void setDocListName(String docListName) {
        this.docListName = docListName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
}
