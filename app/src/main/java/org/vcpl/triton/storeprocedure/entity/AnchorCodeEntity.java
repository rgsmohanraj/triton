package org.vcpl.triton.storeprocedure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="anchor_code")
public class AnchorCodeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "cif_id")
//    @NotNull(message = "gstNumber shouldn't be null in GST Sheet")
//    @Pattern(regexp = "([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$",message = "please Enter valid Gst Number in GST Sheet")
    private BigInteger cifId;

    @Column(name = "vcpl_code", nullable = false)
    @NotNull(message = "vcpl code shouldn't be null")
    private String vcplCode;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;
}
