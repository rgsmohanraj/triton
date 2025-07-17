package org.vcpl.triton.counterParty.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.vcpl.triton.counterParty.data.DueDiligenceMasterData;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;


import java.util.List;

public interface IDueDiligenceMasterService {

    List<DueDiligenceMasterEntity> getAllProduct();
    DueDiligenceMasterEntity getdueDiligenceMasterById(Long id);
    DueDiligenceMasterEntity saveDueDiligenceMaster(@RequestBody DueDiligenceMasterData dueDiligenceMasterData);
    DueDiligenceMasterEntity updateduediligenceMaster(DueDiligenceMasterData dueDiligenceMasterData, long id);
    String deleteDueDiligenceMaster(long id);
}
