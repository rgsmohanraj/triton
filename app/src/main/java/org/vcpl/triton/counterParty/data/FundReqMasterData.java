package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FundReqMasterData {

    @NotNull
    private String questions;

    @NotNull
    private String displayName;

    @NotNull
    private String dataType;

    @NotNull
    private String requirementName;

    @NotNull
    private String description;

    @NotNull
    private String status;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
