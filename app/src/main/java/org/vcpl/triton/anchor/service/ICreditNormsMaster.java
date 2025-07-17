package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.CreditNormsMasterData;
import org.vcpl.triton.anchor.entity.CreditNormsMasterEntity;

import java.util.List;

public interface ICreditNormsMaster {
    List<CreditNormsMasterEntity> getAllCreditNormsMaster();

    CreditNormsMasterEntity findCreditNormsMasterById(long id);

    CreditNormsMasterEntity saveCreditNormsMaster(CreditNormsMasterData creditNormsMasterData);

    CreditNormsMasterEntity updateCreditNormsMaster(CreditNormsMasterData creditNormsMasterData, long id);

    String deleteCreditNormsMaster(long id);
}
