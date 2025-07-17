package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralDetailsData {

    private Long id;

    @NotNull
    private String value;

    @NotNull
    private Long cmId;

    @NotNull
    private Long appId;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private  Timestamp updatedAt;


}
