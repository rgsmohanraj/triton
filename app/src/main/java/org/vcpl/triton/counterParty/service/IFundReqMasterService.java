package org.vcpl.triton.counterParty.service;


import org.vcpl.triton.counterParty.data.FundReqMasterData;
import org.vcpl.triton.counterParty.entity.FundReqMasterEntity;

import java.util.List;

public interface IFundReqMasterService {

    List<FundReqMasterEntity> getAllProduct();
    FundReqMasterEntity getFundReqMasterById(long id);
    FundReqMasterEntity saveFundReqMaster(FundReqMasterData fundRequirementMasterData);
    String deleteFundReqMaster(long id);
    FundReqMasterEntity updateFundReqMaster(FundReqMasterData fundRequirementMasterData, long id);


}
