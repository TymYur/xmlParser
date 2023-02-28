package com.testapp.xmlparser.mapper;

import com.testapp.xmlparser.dto.UploadDTO;
import com.testapp.xmlparser.entity.Upload;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UploadMapper {

    UploadDTO uploadToUploadDto(Upload upload);

    Upload uploadDtoToUpload(UploadDTO uploadDto);

    List<UploadDTO> uploadsToUploadDTOs(Iterable<Upload> uploads);
}
