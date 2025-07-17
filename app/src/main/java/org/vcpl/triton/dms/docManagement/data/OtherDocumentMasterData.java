package org.vcpl.triton.dms.docManagement.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtherDocumentMasterData {
    @NotNull
    private Long appId;

    @NotNull
    List<OtherDocumentData> otherDocumentDataList;

    private int customerType;

    private String rmName;

    private String constitution;

    private String updatedBy;

    private String createdBy;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<OtherDocumentData> getOtherDocumentDataList() {
        return otherDocumentDataList;
    }

    public void setOtherDocumentDataList(List<OtherDocumentData> otherDocumentDataList) {
        this.otherDocumentDataList = otherDocumentDataList;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
