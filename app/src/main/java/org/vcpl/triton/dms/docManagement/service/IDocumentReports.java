package org.vcpl.triton.dms.docManagement.service;

import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.dms.docManagement.data.DocumentReportsData;

import java.io.File;
import java.util.List;

public interface IDocumentReports {
    String saveFile(DocumentReportsData documentReportsData, MultipartFile file);

    byte[] downloadFolder(String filename);

    File downloadFile(Long appId, String docType, String docList, String fileName);

    String deleteS3File(DocumentReportsData documentReportsData);

    List<String> listAllFiles();

    String customerDocReports(Long appId);

    String documentReports(Long custId);

    String getRenewalEnhancementDocReports(Long custId);

}
