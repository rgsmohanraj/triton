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
public class AnchorAddressMasterData {

    @NotNull
    @Valid
    List<AnchorAddressData> anchorAddressData;

    private String updatedBy;

    private String createdBy;

}
