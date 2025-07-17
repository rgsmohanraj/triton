package org.vcpl.triton.anchor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationData {

//    @NotNull
//    private Long appId;

    @NotNull(message = "type shouldn't be null in Basic Details Sheet")
    private int type;

    @NotNull(message = "appType shouldn't be null in Basic Details Sheet")
    private int appType;

//    @NotNull(message = "wfType shouldn't be null in Basic Details Sheet")
//    private int wfType;

    private Long custId;

    private String createdBy;

    private String updatedBy;

}
