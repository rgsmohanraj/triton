package org.vcpl.triton.anchor.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.repository.DeleteAnchorFileUpload;
import org.vcpl.triton.anchor.entity.*;
import org.vcpl.triton.anchor.helper.*;
import org.vcpl.triton.anchor.repository.*;
import org.vcpl.triton.workflow.repository.WorkflowStageRepository;
import org.vcpl.triton.workflow.service.WFApprovalStatusService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AnchorFileUploadService implements IAnchorFileUpload {

    private static final Logger logger = LoggerFactory.getLogger(AnchorFileUploadService.class);


    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private ApplicationRepository applicationRepository;

    private AnchorBasicDetails anchorBasicDetails = new AnchorBasicDetails();

    private ProgramNormsDetails programDetails = new ProgramNormsDetails();

    private BeneficiaryDetails beneficiaryDetails = new BeneficiaryDetails();

    private AnchorGst anchorgst = new AnchorGst();

    private AnchorAuthorized anchorAuthorized = new AnchorAuthorized() ;

    private AnchorAddress anchorAddress = new AnchorAddress();

    private AnchorKey anchorKey = new AnchorKey();

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private WFApprovalStatusService wfApprovalStatusService;

    @Autowired
    private DeleteAnchorFileUpload deleteAnchorFileUpload;

    @Autowired
    private WorkflowStageRepository workflowStageRepository;




    @Override
    public Map<String,List<Object>> save(MultipartFile file,String approver,HttpServletResponse response) throws IOException, ParseException {
        try {
            logger.info(" | URL | Logging to FileUploadService | OPERATION | " + "Message: Logging to FileUploadService");
            Map<String, List<Object>> map = new HashMap<>();
            List applicationEntityList = new ArrayList();
            List<ProgramNormsEntity> programDetailsEntityList = programDetails.convertExcelToListOfProgramNorms(file.getInputStream());
            if (programDetailsEntityList != null) {
                CustomerInfoEntity customerInfoEntity = customerInfo.convertExcelToListOfCustomerInfo(file.getInputStream());
                try {
                    customerInfoEntity = customerInfoRepository.save(customerInfoEntity);
                } catch (ConstraintViolationException ex) {
                    List<String> errorMessages = ex.getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList());
                    map.put("status", Collections.singletonList(false));
                    map.put("message", Collections.singletonList(errorMessages));
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    ex.printStackTrace();
                    return map;
                }
                ApplicationEntity applicationEntity = new ApplicationEntity();
                applicationEntity.setType(1);
                applicationEntity.setAppType(1);
                applicationEntity.setWfType(0);
                applicationEntity.setSeqNo(1);
                applicationEntity.setCustomerInfoEntity(customerInfoRepository.getById(customerInfoEntity.getId()));
                try {
                    applicationEntity = applicationRepository.save(applicationEntity);
                    List<AnchorAddressEntity> anchorAddressDetailsEntities = anchorAddress.convertExcelToListOfAnchorAddress(file.getInputStream(), applicationEntity);
                    List<AnchorAuthorizedEntity> anchorAuthorizedEntities = anchorAuthorized.convertExcelToListOfAnchorAuthorized(file.getInputStream(), applicationEntity);
                    List<AnchorKeyEntity> anchorKeyEntities = anchorKey.convertExcelToListOfAnchorKey(file.getInputStream(), applicationEntity);
                    List<AnchorGstEntity> anchorGstEntities = anchorgst.convertExcelToListOfAnchorGst(file.getInputStream(), applicationEntity);
//                    List<ProgramNormsEntity> programDetailsEntities = programDetails.convertExcelToListOfProgramNorms(file.getInputStream(), applicationEntity);

                    for (ProgramNormsEntity programNormsEntity : programDetailsEntityList) {
                        programNormsEntity.setApplicationEntity(applicationEntity);
                    }

                    List<AnchorBasicEntity> anchorBasicEntity = anchorBasicDetails.convertExcelToListOfAnchorBasic(file.getInputStream(), applicationEntity, customerInfoEntity);

                    applicationEntity.setAnchorAddressDetailsEntityList(anchorAddressDetailsEntities);
                    applicationEntity.setAnchorAuthorizedEntities(anchorAuthorizedEntities);
                    applicationEntity.setAnchorKeyEntities(anchorKeyEntities);
                    applicationEntity.setAnchorGstEntities(anchorGstEntities);
                    applicationEntity.setProgramDetailsEntities(programDetailsEntityList);
                    applicationEntity.setAnchorBasicDetails(anchorBasicEntity);
                    applicationRepository.saveAndFlush(applicationEntity);
//            WFApprovalStatusData wfApprovalStatusData = new WFApprovalStatusData();
//            wfApprovalStatusData.setAppId(applicationEntity.getId());
//            wfApprovalStatusData.setStatus(2);
//            wfApprovalStatusData.setApproverInfo(approver);
//           WorkFlowStageEntity stageId = workflowStageRepository.findByStageId("A1");
//            wfApprovalStatusData.setStageId(stageId.getId());
//            wfApprovalStatusData.setRemarks("Approved");
//            wfApprovalStatusService.saveWFApproval(wfApprovalStatusData);
                    applicationEntityList.add(String.valueOf(applicationEntity.getId()));
                    map.put("status", Collections.singletonList(true));
                    map.put("applicationEntity", Collections.singletonList(applicationEntityList));
                } catch (ConstraintViolationException ex) {
                    this.deleteAnchorFileUpload.deleteByAppId(customerInfoEntity.getId(), applicationEntity.getId());
                    List<String> errorMessages = ex.getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList());
                    map.put("status", Collections.singletonList(false));
                    map.put("message", Collections.singletonList(errorMessages));
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
                    ex.printStackTrace();
                    logger.error(" | URL | anchorFileUpload->save method | OPERATION | " + " Error |" + errorMessages);
                    return map;
                } catch (IllegalStateException illegal) {
                    this.deleteAnchorFileUpload.deleteByAppId(customerInfoEntity.getId(), applicationEntity.getId());
                    map.put("status", Collections.singletonList(false));
                    String[] msg = {"Please Enter Valid Data in Excel"};
                    map.put("message", Collections.singletonList(msg));
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
                    illegal.printStackTrace();
                    logger.error(" | URL | anchorFileUpload->save method | OPERATION | " + " Error | " + illegal.getMessage());
                    return map;
                } catch (Exception ex) {
                    this.deleteAnchorFileUpload.deleteByAppId(customerInfoEntity.getId(), applicationEntity.getId());
                    map.put("status", Collections.singletonList(false));
                    String[] msg = {"Some Technical Error, Please Contact Admin"};
                    map.put("message", Collections.singletonList(msg));
                    logger.error(" | URL | anchorFileUpload->save method | OPERATION |  Error |" + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                String[] msg = {"Please enter valid program norms details"};
                map.put("status", Collections.singletonList(false));
                map.put("message", Collections.singletonList(msg));
                return map;
            }

            return map;
        } catch (Exception ex) {
            logger.error(" | URL | Logging to FileUploadService | OPERATION | " + "Error Message:Error While Logging to FileUploadService"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


    public Optional<CustomerInfoEntity> getCustomerById(Long id) {
        logger.info(" | URL | /anchorDetails/{id} | OPERATION | " + "GetById anchorFileUpload");
        return customerInfoRepository.findById(id);
    }


}
