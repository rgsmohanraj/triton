package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksData {
    @NotNull
    private Long custId;

    private String description;

    private String stepperTab;

    private String CreatedBy;

    private String updatedBy;

}
