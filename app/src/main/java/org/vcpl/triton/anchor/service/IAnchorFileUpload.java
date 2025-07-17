package org.vcpl.triton.anchor.service;

import org.springframework.web.multipart.MultipartFile;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAnchorFileUpload {

    Map<String,List<Object>> save(MultipartFile file, String approver, HttpServletResponse response) throws IOException, ParseException;
    Optional<CustomerInfoEntity> getCustomerById(Long id);
}
