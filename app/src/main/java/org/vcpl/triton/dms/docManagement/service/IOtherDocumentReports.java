package org.vcpl.triton.dms.docManagement.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.dms.docManagement.data.OtherDocumentReportsData;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;

import java.io.File;
import java.util.Optional;

public interface IOtherDocumentReports {
    JSONObject saveFile(OtherDocumentReportsData otherDocumentReportsData, MultipartFile file);

    Optional<OtherDocumentMasterEntity> getOtherMaster(Long id);

    File downloadFile(Long appId, String docType, String docList, String docName, String fileName);

    String customerDocReports(Long appId);
}
