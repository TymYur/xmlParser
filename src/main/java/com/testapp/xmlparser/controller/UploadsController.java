package com.testapp.xmlparser.controller;

import com.testapp.xmlparser.dto.UploadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadsController {

    List<UploadDTO> getAllUploads();

    ResponseEntity<?> createNewUpload(@RequestParam("file") MultipartFile file);
}
