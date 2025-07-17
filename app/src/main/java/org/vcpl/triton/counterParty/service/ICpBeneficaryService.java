package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CpBeneficiaryData;
import org.vcpl.triton.counterParty.entity.CpBeneficiaryEntity;

import java.util.Collection;

public interface ICpBeneficaryService {

    CpBeneficiaryEntity getCPBeneficiaryDetailsById(long id) ;
    CpBeneficiaryEntity saveCpBeneficiaryDetails(CpBeneficiaryData cpBeneficiaryData);
    CpBeneficiaryEntity updateCpBeneficiaryDetails(CpBeneficiaryData cpBeneficiaryData);
}
