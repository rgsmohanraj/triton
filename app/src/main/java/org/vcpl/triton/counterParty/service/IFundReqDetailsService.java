package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.FundReqDetailsData;
import org.vcpl.triton.counterParty.data.FundReqDetailsMasterData;
import org.vcpl.triton.counterParty.entity.FundReqDetailsEntity;

import java.util.List;

public interface IFundReqDetailsService {
    List<FundReqDetailsEntity> getAllFundReqDetails();

    List<FundReqDetailsEntity> getFundReqDetailsByFId(long appId);

    List<Long> saveFundReqDetails(FundReqDetailsMasterData fundReqDetailsMasterData);
    List<Long> updateFundReqDetails(FundReqDetailsMasterData fundReqDetailsMasterData);
    String deleteFundReqDetails(long id);
    FundReqDetailsEntity getFundReqDetailsById(long id);

}
