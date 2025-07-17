package org.vcpl.triton.dms.docManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.dms.docManagement.entity.DocumentReportsEntity;

import java.util.List;

@Repository
public interface DocumentReportsRepository extends JpaRepository<DocumentReportsEntity,Long> {

    @Query(value = "SELECT * FROM dms_document_reports WHERE app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId",nativeQuery = true)
    List<DocumentReportsEntity> documentCheck(Long appId, Long docTypeId, Long docCategoryId, Long docListId);

    @Query(value = "SELECT * FROM dms_document_reports WHERE app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId and file_name=:fileName",nativeQuery = true)
    DocumentReportsEntity getDocumentDetails(Long appId,Long docTypeId,Long docCategoryId,Long docListId,String fileName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM dms_document_reports where app_id=:appId and doc_type_id=:docTypeId and doc_category_id=:docCategoryId and doc_list_id=:docListId and file_name=:fileName",nativeQuery = true)
    void deleteDocDetails(Long appId,Long docTypeId,Long docCategoryId,Long docListId,String fileName);

    @Query(value = "SELECT * FROM dms_document_reports WHERE app_id=?1",nativeQuery = true)
    List<DocumentReportsEntity> findCustomerDocReports(Long appId);

}
