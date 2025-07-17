package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.AssignUnderwriterData;
import org.vcpl.triton.counterParty.entity.AssignUnderwriterEntity;

import java.util.Collection;
import java.util.Optional;

public interface IAssignUnderwriter {

    AssignUnderwriterEntity saveAssignUnderwriter(AssignUnderwriterData assignUnderwriterData);
    Optional<AssignUnderwriterEntity> getAssignUnderwriter(long id);
    AssignUnderwriterEntity updateAssignUnderwriter(AssignUnderwriterData assignUnderwriterData);

}
