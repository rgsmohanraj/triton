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
public class CreditPolicyDetailsMasterData {
    @NotNull
    List<CreditPolicyDetailsData> creditPolicyDetailsData;

    private String updatedBy;

    private String createdBy;

    public List<CreditPolicyDetailsData> getCreditPolicyDetailsData() {
        return creditPolicyDetailsData;
    }

    public void setCreditPolicyDetailsData(List<CreditPolicyDetailsData> creditPolicyDetailsData) {
        this.creditPolicyDetailsData = creditPolicyDetailsData;
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


