package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftPolicyDetailsData {

    private Long id;

    @NotNull
    private Long softPolicyId;

    @NotNull
    private Long appId;

    @NotNull
    private String value;


    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
