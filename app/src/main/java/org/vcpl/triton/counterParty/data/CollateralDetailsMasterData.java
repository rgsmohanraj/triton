package org.vcpl.triton.counterParty.data;

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
public class CollateralDetailsMasterData {

    @NotNull
    List<CollateralDetailsData> collateralDetailsDataList;

    private String updatedBy;

    private String createdBy;

    public List<CollateralDetailsData> getCollateralDetailsDataList() {
        return collateralDetailsDataList;
    }

    public void setCollateralDetailsDataList(List<CollateralDetailsData> collateralDetailsDataList) {
        this.collateralDetailsDataList = collateralDetailsDataList;
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
