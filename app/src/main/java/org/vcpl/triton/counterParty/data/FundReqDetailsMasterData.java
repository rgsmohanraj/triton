package org.vcpl.triton.counterParty.data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FundReqDetailsMasterData {

    @NotNull
    List<FundReqDetailsData> fundReqDetailsDataList;

    private String updatedBy;

    private String createdBy;

    public List<FundReqDetailsData> getFundReqDetailsDataList() {
        return fundReqDetailsDataList;
    }

    public void setFundReqDetailsDataList(List<FundReqDetailsData> fundReqDetailsDataList) {
        this.fundReqDetailsDataList = fundReqDetailsDataList;
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
