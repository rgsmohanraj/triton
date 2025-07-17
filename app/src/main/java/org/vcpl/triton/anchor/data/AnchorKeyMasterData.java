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
public class AnchorKeyMasterData {

    @NotNull
    @Valid
    List<AnchorKeyData> anchorKeyDataList;



    private String updatedBy;

    private String createdBy;

    public List<AnchorKeyData> getAnchorKeyDataList() {
        return anchorKeyDataList;
    }

    public void setAnchorKeyDataList(List<AnchorKeyData> anchorKeyDataList) {
        this.anchorKeyDataList = anchorKeyDataList;
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
