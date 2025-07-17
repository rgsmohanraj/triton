package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditPolicyDetailsData {

    private Long id;

    @NotNull
    private String value;

    @NotNull
    private Long cpMasterId;

    @NotNull
    private Long appId;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private  Timestamp updatedAt;


}
