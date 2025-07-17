package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CollateralDetailsData;
import org.vcpl.triton.counterParty.data.CollateralDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CollateralDetailsEntity;

import java.util.Collection;
import java.util.List;

public interface ICollateralDetailsService {

    List<CollateralDetailsEntity> getCollateralDetails();
    CollateralDetailsEntity getCollateralDetailsById(Long id);
    List<Long> saveCollateralDetails(CollateralDetailsMasterData collateralDetailsMasterData);

    List<Long> updateCollateralDetails(CollateralDetailsMasterData collateralDetailsMasterData);

    //    CollateralDetailsEntity updateCollateralDetails(CollateralDetailsData collateralDetailsData, long id);
    String deleteCollateralDetails(long id);
    Collection<CollateralDetailsEntity> getCollateralById(Long id);

}
