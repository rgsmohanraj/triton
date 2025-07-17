package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CommercialData;
import org.vcpl.triton.counterParty.data.CommercialMasterData;
import org.vcpl.triton.counterParty.entity.CommercialEntity;

import java.util.Collection;
import java.util.List;

public interface ICommercialCc {

     List<CommercialEntity> getAllCommercialCc();
    Collection<CommercialEntity> getCommercial(long id) ;
    List<Long> saveCommercialCc(CommercialMasterData commercialMasterData, long id);
    List<Long> updateCommercialDetails(CommercialMasterData commercialMasterData);
}
