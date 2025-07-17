package org.vcpl.triton.counterParty.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.CollateralMasterData;
import org.vcpl.triton.counterParty.entity.CollateralMasterEntity;

import java.util.List;

public interface ICollateralMasterService {

    List<CollateralMasterEntity> getAllProduct();
    CollateralMasterEntity getCollateralMasterById(Long id);
    CollateralMasterEntity saveCollateralMaster(@RequestBody CollateralMasterData collateralMasterData);
    CollateralMasterEntity updateCollateralMaster(CollateralMasterData collateralMasterData, long id);
    String deleteCollateralMaster(long id);


}
