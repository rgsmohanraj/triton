package org.vcpl.triton.counterParty.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsData;
import org.vcpl.triton.counterParty.data.DueDiligenceDetailsMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceDetailsEntity;

import java.util.Collection;
import java.util.List;

public interface IDueDiligenceDetailsService {

    List<DueDiligenceDetailsEntity> getDueDiligenceDetials();
    Collection<DueDiligenceDetailsEntity> getdueDiligenceDetailsById(Long id);
    List<Long> saveDueDiligenceDetails(DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData);
    List<Long> updateDueDiligenceDetails(DueDiligenceDetailsMasterData dueDiligenceDetailsMasterData);

    String deleteDueDiligenceDetails(long id);
}
