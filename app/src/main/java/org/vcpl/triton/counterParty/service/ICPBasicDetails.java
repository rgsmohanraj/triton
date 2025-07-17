package org.vcpl.triton.counterParty.service;


import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.counterParty.data.CPBasicDetailsData;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;

import java.util.Collection;
import java.util.List;

public interface ICPBasicDetails {

    CPBasicDetailsEntity getCPDetails(long id);

    CPBasicDetailsEntity saveRenewalBasicDetails(CPBasicDetailsData cpBasicDetailsData);

    String deleteCPBasicDetails(long id);
    ApplicationEntity saveCPDetails(CPBasicDetailsData cpBasicDetail);
    Collection<CPBasicDetailsEntity> getCPBasicDetailsById(long id);

    CPBasicDetailsEntity updateCPBasicDetails(CPBasicDetailsData cpBasicDetailsData);
}
