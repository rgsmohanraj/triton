package org.vcpl.triton.counterParty.data;

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
public class ProposedProductsMasterData {

    @NotNull
    @Valid
    List<ProposedProductsData> proposedProductsDataList;

    private String updatedBy;

    private String createdBy;

    public List<ProposedProductsData> getProposedProductsDataList() {
        return proposedProductsDataList;
    }

    public void setProposedProductsDataList(List<ProposedProductsData> proposedProductsDataList) {
        this.proposedProductsDataList = proposedProductsDataList;
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
