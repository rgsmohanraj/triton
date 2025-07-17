package org.vcpl.triton.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.scheduler.data.DeferralReportData;

import java.util.List;

@Repository
public interface SchedulerRepository extends ReadOnlyRepository<DeferralReportData ,Long> {

    
//    @Query(value = "select * from sample_view",nativeQuery = true)
//    List<DeferralReportData> getPendingDeferralReportData();

//    @Query(value = "CREATE VIEW view_deferral_document_report AS\n" +
//            "\tSELECT d.* FROM (SELECT *, CASE \n" +
//            "\t\t\t\t\t\t\t\tWHEN initial_time < curdate() AND revised_time IS NULL THEN '-0' \n" +
//            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() AND revised_time IS NULL THEN '-1'   \n" +
//            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() + INTERVAL 2 DAY AND revised_time IS NULL THEN '-2'\n" +
//            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() + INTERVAL 5 DAY AND revised_time IS NULL THEN '-5'\n" +
//            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() THEN '0'\n" +
//            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() + INTERVAL 2 DAY THEN '2'\n" +
//            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() + INTERVAL 5 DAY THEN '5'\n" +
//            "\t\t\t\t\t\t\t\tWHEN revised_time < curdate() THEN '1'\n" +
//            "\t\t\t\t\t\t\tEND AS data_view  FROM deferral_document_reports WHERE status IN (0,1)) AS d WHERE d.data_view IS NOT NULL;",nativeQuery = true)
//    @Query(value = "CREATE VIEW view_deferral_document_report AS SELECT * FROM deferral_document_reports",nativeQuery = true)
//    void createViewDeferralReportTable();


    @Query(value ="CREATE PROCEDURE view_deferral_document_report() BEGIN SELECT d.* FROM (SELECT *, CASE \n" +
            "\t\t\t\t\t\t\t\tWHEN initial_time < curdate() AND revised_time IS NULL THEN '-0' \n" +
            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() AND revised_time IS NULL THEN '-1'   \n" +
            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() + INTERVAL 2 DAY AND revised_time IS NULL THEN '-2'\n" +
            "\t\t\t\t\t\t\t\tWHEN initial_time = curdate() + INTERVAL 5 DAY AND revised_time IS NULL THEN '-5'\n" +
            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() THEN '0'\n" +
            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() + INTERVAL 2 DAY THEN '2'\n" +
            "\t\t\t\t\t\t\t\tWHEN revised_time = curdate() + INTERVAL 5 DAY THEN '5'\n" +
            "\t\t\t\t\t\t\t\tWHEN revised_time < curdate() THEN '1'\n" +
            "\t\t\t\t\t\t\tEND AS data_view  FROM dms_deferral_document_reports WHERE status IN (0,1)) AS d WHERE d.data_view IS NOT NULL; END",nativeQuery = true)
    void createStoreProcedureDeferralReportTable();

    @Procedure(name = "view_deferral_document_report")
    List<DeferralReportData> view_deferral_document_report();

//    @Query(value = " DELIMITER// \" +\n" +
//            "                \"CREATE PROCEDURE view_deferral_document_report() BEGIN SELECT d.* FROM (SELECT *, CASE \\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN initial_time < curdate() AND revised_time IS NULL THEN '-0' \\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN initial_time = curdate() AND revised_time IS NULL THEN '-1'   \\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN initial_time = curdate() + INTERVAL 2 DAY AND revised_time IS NULL THEN '-2'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN initial_time = curdate() + INTERVAL 5 DAY AND revised_time IS NULL THEN '-5'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN revised_time = curdate() THEN '0'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN revised_time = curdate() + INTERVAL 2 DAY THEN '2'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN revised_time = curdate() + INTERVAL 5 DAY THEN '5'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\t\\tWHEN revised_time < curdate() THEN '1'\\n\" +\n" +
//            "                \"\\t\\t\\t\\t\\t\\t\\tEND AS data_view  FROM deferral_document_reports WHERE status IN (0,1)) AS d WHERE d.data_view IS NOT NULL; END //DELIMITER ;", nativeQuery = true)
//    void createMyStoredProcedure();
}
