package com.testapp.xmlparser.service;

import com.testapp.xmlparser.dto.UploadDTO;
import com.testapp.xmlparser.entity.Upload;
import com.testapp.xmlparser.mapper.UploadMapper;
import com.testapp.xmlparser.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadRepository repository;
    private final UploadMapper mapper;

    public List<UploadDTO> findAllUploads() {
        return mapper.uploadsToUploadDTOs(repository.findAll());
    }

    public void createUpload(Upload newUpload) {
        repository.save(newUpload);
    }
}
