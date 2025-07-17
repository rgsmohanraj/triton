package org.vcpl.triton.anchor.data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreditNormsDetailsMasterData {
    @NotNull
    List<CreditNormsDetailsData> creditNormsDetailsDataList;

    private String updatedBy;

    private String createdBy;

    public List<CreditNormsDetailsData> getCreditNormsDetailsDataList() {
        return creditNormsDetailsDataList;
    }

    public void setCreditNormsDetailsDataList(List<CreditNormsDetailsData> creditNormsDetailsDataList) {
        this.creditNormsDetailsDataList = creditNormsDetailsDataList;
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
