package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.CreditNormsDetailsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;

import java.util.List;

public interface ICreditNormsDetails {
    List<CreditNormsDetailsEntity> getCreditNormsDetails();

    CreditNormsDetailsEntity getCreditNormsDetailsById(long id);

    List<Long> saveCreditNormsDetails(CreditNormsDetailsMasterData creditNormsDetailsMasterData);

    List<Long> updateCreditNormsDetails(CreditNormsDetailsMasterData creditNormsDetailsMasterData);

    String deleteCreditNormsDetails(long id);

    List<CreditNormsDetailsEntity> findByIdcreditNormsDetails(long id);
}
