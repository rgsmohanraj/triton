package org.vcpl.triton.scheduler.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.scheduler.data.OtherDeferralReportData;

import java.util.List;

@Repository
public interface OtherDeferralReportRepository extends ReadOnlyRepository<OtherDeferralReportData,Long>{
    @Procedure(name = "view_other_deferral_document_report")
    List<OtherDeferralReportData> view_other_deferral_document_report();

}
