package org.vcpl.triton.dms.docManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentReportsEntity;

import java.util.Collection;
import java.util.List;

public interface OtherDocumentReportsRepository extends JpaRepository<OtherDocumentReportsEntity, Long> {

    @Query(value = "SELECT * FROM dms_other_document_reports WHERE app_id=?1",nativeQuery = true)
    Collection<OtherDocumentReportsEntity> findCustomerDocReports(Long appId);

    @Query(value = "SELECT * FROM dms_other_document_reports WHERE other_doc_master=?1",nativeQuery = true)
    Collection<OtherDocumentReportsEntity> findDocReportsWithMasterId(Long id);

    @Query(value = "SELECT * FROM dms_other_document_reports WHERE app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId and other_doc_master=:docMasterId and file_name=:fileName",nativeQuery = true)
    OtherDocumentReportsEntity getDocumentDetails(Long appId, Long docTypeId, Long docCategoryId, Long docListId,Long docMasterId, String fileName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM dms_other_document_reports where app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId and other_doc_master=:docMasterId and file_name=:fileName",nativeQuery = true)
    void deleteDocDetails(Long appId, Long docTypeId, Long docCategoryId, Long docListId, Long docMasterId, String fileName);

    @Query(value = "SELECT * FROM dms_other_document_reports WHERE app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId and other_doc_master=:otherDocMasterId",nativeQuery = true)
    List<OtherDocumentReportsEntity> documentCheck(Long appId, Long docTypeId, Long docCategoryId, Long docListId, Long otherDocMasterId);
}
