package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.AnchorKeyMasterData;
import org.vcpl.triton.anchor.entity.AnchorKeyEntity;

import java.util.Collection;
import java.util.List;

public interface IAnchorKey {

    List<AnchorKeyEntity> getAllProduct();
    Collection<AnchorKeyEntity> getanchorKey(long id);
    AnchorKeyEntity getAnchorById(Long id);
    String deleteAnchor(long id);
    List<Long>  saveAnchorKeyDetails(AnchorKeyMasterData anchorKeyMasterData, long id);
    List<Long>  updateAnchor(AnchorKeyMasterData anchorKeyMasterData);


}
