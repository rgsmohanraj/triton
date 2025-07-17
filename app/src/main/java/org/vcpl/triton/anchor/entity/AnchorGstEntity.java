
package org.vcpl.triton.anchor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="anchor_gst")
public class AnchorGstEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "gst_number", nullable = false)
    @NotBlank(message = "gstNumber shouldn't be null in GST Sheet")
    @NotNull(message = "gstNumber shouldn't be null in GST Sheet")
    @Pattern(regexp = "([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$",message = "please Enter valid Gst Number in GST Sheet")
    private String gstNumber;

    @Column(name = "address_line1", nullable = false)
    //@NotBlank(message = "addressLine1 shouldn't be null in GST Sheet")
    @NotNull(message = "addressLine1 shouldn't be null in Address Sheet")
    private String addressLine1;

    @Column(name = "address_line2", nullable = false)
    //@NotBlank(message = "addressLine2 shouldn't be null in GST Sheet")
    @NotNull(message = "addressLine2 shouldn't be null in Address Sheet")
    private String addressLine2;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "city shouldn't be null in GST Sheet")
    @NotNull(message = "city shouldn't be null in GST Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "City should contain only Alphabets in GST Sheet")
    private String city;

    @Column(name = "state", nullable = false)
    @NotBlank(message = "state shouldn't be null in GST Sheet")
    @NotNull(message = "state shouldn't be null in GST Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "State should contain only Alphabets in GST Sheet")
    private String state;

    @Column(name = "country", nullable = false)
    @NotBlank(message = "country shouldn't be null in GST Sheet")
    @NotNull(message = "country shouldn't be null in GST Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Country should contain only Alphabets in GST Sheet")

    private String country;

    @Column(name = "pincode", nullable = false)
    @NotBlank(message = "pincode shouldn't be null in GST Sheet")
    @NotNull(message = "pincode shouldn't be null in GST Sheet")
    @Pattern(regexp = "[0-9]{6}",message = "Please Enter valid Pincode")
    private String pincode;

    @Column(name = "gst_acct_holder_name", nullable = false)
    @NotBlank(message = "gstAcctHolderName shouldn't be null in GST Sheet")
    @NotNull(message = "gstAccntHolderName shouldn't be null in GST Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "ACCOUNT HOLDER NAME should contain only Alphabets in GST Sheet")
    private String gstAcctHolderName;

    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

    @Column(name = "created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
