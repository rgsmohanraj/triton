package org.vcpl.triton.counterParty.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.LimitEligibilityDetailsData;
import org.vcpl.triton.counterParty.data.LimitEligibilityMasterData;
import org.vcpl.triton.counterParty.entity.LimitEligibilityDetailsEntity;


import java.util.List;

public interface ILimitEligibilityDetailsService {

    List<LimitEligibilityDetailsEntity> getLimitEligibilityDetails();
    LimitEligibilityDetailsEntity getlimitEligibilityDetailsById(Long id);
    List<Long> saveLimitEligibilityDetails(LimitEligibilityMasterData limitEligibilityMasterData, long id) ;
    List<Long> updateLimitEligibilityDetails(LimitEligibilityMasterData limitEligibilityMasterData);

    String deleteLimitEligibilityDetails(long id);
}
