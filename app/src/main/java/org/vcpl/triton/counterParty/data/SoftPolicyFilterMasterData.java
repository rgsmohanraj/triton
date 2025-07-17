package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftPolicyFilterMasterData {

    @NotNull
    private Long softPolicySubTypeId;

    @NotNull
    private String type;

    @NotNull
    private String formula;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private  Timestamp updatedAt;
}
