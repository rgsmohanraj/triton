package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CPDebtProfileData;
import org.vcpl.triton.counterParty.data.CPDebtProfileMasterData;
import org.vcpl.triton.counterParty.entity.CPDebtProfileEntity;

import java.util.Collection;
import java.util.List;

public interface ICPDebtProfile {

    List<CPDebtProfileEntity> getAllCPDebtProfile();

    Collection<CPDebtProfileEntity> findByCpDebtProfile(long id);

    String deleteCPDebtProfile(long id);

    List<Long> saveCpDebtProfile(CPDebtProfileMasterData cpDebtProfileMasterData, long id);

    List<Long> updateCpDebtProfile(CPDebtProfileMasterData cpDebtProfileMasterData, Long appId);

    String deleteDebtProfile(long id);
}
