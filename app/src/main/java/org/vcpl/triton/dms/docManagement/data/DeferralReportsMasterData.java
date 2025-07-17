package org.vcpl.triton.dms.docManagement.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeferralReportsMasterData {
    @NotNull
    private Long appId;

    @NotNull
    List<DeferralReportsData> deferralReportsDataList;

    private int customerType;

    private String rmName;

    private Boolean docProductCheck;

    private String constitution;

    private String updatedBy;

    private String createdBy;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<DeferralReportsData> getDeferralReportsDataList() {
        return deferralReportsDataList;
    }

    public void setDeferralReportsDataList(List<DeferralReportsData> deferralReportsDataList) {
        this.deferralReportsDataList = deferralReportsDataList;
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

    public Boolean getDocProductCheck() {
        return docProductCheck;
    }

    public void setDocProductCheck(Boolean docProductCheck) {
        this.docProductCheck = docProductCheck;
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
