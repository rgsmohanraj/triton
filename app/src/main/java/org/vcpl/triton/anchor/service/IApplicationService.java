package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.ApplicationData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

public interface IApplicationService {

    String deleteApplicationDetails(long id);
    ApplicationEntity updateApplicationDetails(ApplicationData applicationData, long id);
    ApplicationEntity saveApplicationDetails(ApplicationData applicationData);
    ApplicationEntity getApplicationDetailsById(long id);
    List<ApplicationEntity> getAllApplicationDetails();
    Collection<ApplicationEntity> getApplicationDetails (long id);
    List<ApplicationEntity> findByAnchor();
    List<ApplicationEntity> findByCp();
    String getApplicationIds(Long custId);
    String findAllApplicationIds(Long custId) throws ParseException;
}
