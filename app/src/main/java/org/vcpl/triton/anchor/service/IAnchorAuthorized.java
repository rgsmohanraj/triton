package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.AnchorAuthorizedData;
import org.vcpl.triton.anchor.data.AnchorAuthorizedMasterData;
import org.vcpl.triton.anchor.entity.AnchorAuthorizedEntity;

import java.util.Collection;
import java.util.List;

public interface IAnchorAuthorized {

    List<AnchorAuthorizedEntity> getAllProduct();
    List<Long> saveAnchorAuthorized(AnchorAuthorizedMasterData anchorAuthorizedMasterData, long id) ;
    Collection<AnchorAuthorizedEntity> getanchorAuthorized (long id);
    AnchorAuthorizedEntity getAnchorAuthorizedById(long id);
    List<Long> updateAnchorAuthorizedEntity(AnchorAuthorizedMasterData anchorAuthorizedMasterData);
    String deleteAnchorAuthorized(long id);

}
