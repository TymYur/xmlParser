package com.testapp.xmlparser.repository;

import com.testapp.xmlparser.entity.Upload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends CrudRepository<Upload, Long> {
}
