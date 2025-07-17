package org.vcpl.triton.anchor.data;

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
public class AnchorGstMasterData {

    @NotNull
    @Valid
    List<AnchorGstDetailsData> anchorGstDetailsDataList;

    private String updatedBy;

    private String createdBy;

    public List<AnchorGstDetailsData> getAnchorGstDetailsDataList() {
        return anchorGstDetailsDataList;
    }

    public void setAnchorGstDetailsDataList(List<AnchorGstDetailsData> anchorGstDetailsDataList) {
        this.anchorGstDetailsDataList = anchorGstDetailsDataList;
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
