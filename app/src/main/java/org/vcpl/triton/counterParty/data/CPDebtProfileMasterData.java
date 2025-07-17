package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.vcpl.triton.anchor.data.AnchorGstDetailsData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Valid

public class CPDebtProfileMasterData {
    @NotNull
    @Valid
    List<CPDebtProfileData> cpDebtProfileDataList;

    private String updatedBy;

    private String createdBy;
}
