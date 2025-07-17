package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.BeneficiaryData;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IBeneficiary {

    List<BeneficiaryEntity> getAllProduct();
    Collection<BeneficiaryEntity> getBeneficiary(long id);
    BeneficiaryEntity saveBeneficiaryDetails(BeneficiaryData beneficiaryData);
    Optional<BeneficiaryEntity> getBeneficiaryDetailById(long id);
    BeneficiaryEntity updateBeneficiaryDetails(BeneficiaryData beneficiaryData);
    String deleteBeneficiaryDetails(long id);

}
