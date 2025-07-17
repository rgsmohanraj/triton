package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.ProgramNormsData;
import org.vcpl.triton.anchor.data.ProgramNormsMasterData;
import org.vcpl.triton.anchor.entity.ProgramNormsEntity;

import java.util.Collection;
import java.util.List;

public interface IProgramNorms {

    List<ProgramNormsEntity> getAllProduct();
    Collection<ProgramNormsEntity> getProgarmDetails(long id);
    ProgramNormsEntity getProgramDetailsById(long id);

    String getProgarmDetailsByCustId(long id);
    List<Long> updateProgramDetails(ProgramNormsMasterData programNormsMasterData);

    ProgramNormsEntity saveProgramDetails(ProgramNormsData programNormsData);

    List<Long> saveAnchorProgram(ProgramNormsMasterData programNormsMasterData);

   String deleteProgramNorms(long id);
//    String deleteDetails(long id);
}
