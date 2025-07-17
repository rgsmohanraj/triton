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
public class DueDiligenceDetailsMasterData {
    @NotNull
    List<DueDiligenceDetailsData> dueDiligenceDetailsDataList;

    private String updatedBy;

    private String createdBy;

    public List<DueDiligenceDetailsData> getDueDiligenceDetailsDataList() {
        return dueDiligenceDetailsDataList;
    }

    public void setDueDiligenceDetailsDataList(List<DueDiligenceDetailsData> dueDiligenceDetailsDataList) {
        this.dueDiligenceDetailsDataList = dueDiligenceDetailsDataList;
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
