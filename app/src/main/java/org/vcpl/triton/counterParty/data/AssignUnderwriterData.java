package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignUnderwriterData {

    @NotNull
    private Long appId;

    private  Long id;

    @Column(name = "assign_to", nullable = false)
    @NotBlank(message = "assign to shouldn't be null in Assign Underwriter")
    @NotNull
    private String assignTo;

    private String createdBy;

    private String updatedBy;

}
