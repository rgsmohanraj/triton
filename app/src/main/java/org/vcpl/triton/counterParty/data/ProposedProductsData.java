package org.vcpl.triton.counterParty.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProposedProductsData {

    private  Long id;

    private Long appId;

    @NotNull
    private Long custId;

    @NotNull
    @NotBlank(message = "product shouldn't be null in ProposedProduct")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "product should contain only Alphabets")
    private String product;

//    @NotNull
//    @NotBlank(message = "type shouldn't be null in ProposedProduct")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "type should contain only Alphabets")
    private String type;

    @NotNull
    @NotBlank(message = "proposed shouldn't be null in ProposedProduct")
    @Pattern(regexp = "([0-9]*[.])?[0-9]+",message = "type should contain only Alphabets")
    @Min(value=1)
    private String proposed;

    private Double vintageWithAnchor;

    private Double minMonthlyAnchor;

    private String anchorRelationship;

    private Boolean creditPolicyCheck;

    public Boolean getCreditPolicyCheck() {
        return creditPolicyCheck;
    }

    public void setCreditPolicyCheck(Boolean creditPolicyCheck) {
        this.creditPolicyCheck = creditPolicyCheck;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProposed() {
        return proposed;
    }

    public void setProposed(String proposed) {
        this.proposed = proposed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Double getVintageWithAnchor() {
        return vintageWithAnchor;
    }

    public void setVintageWithAnchor(Double vintageWithAnchor) {
        this.vintageWithAnchor = vintageWithAnchor;
    }

    public Double getMinMonthlyAnchor() {
        return minMonthlyAnchor;
    }

    public void setMinMonthlyAnchor(Double minMonthlyAnchor) {
        this.minMonthlyAnchor = minMonthlyAnchor;
    }

    public String getAnchorRelationship() {
        return anchorRelationship;
    }

    public void setAnchorRelationship(String anchorRelationship) {
        this.anchorRelationship = anchorRelationship;
    }
}
