package com.testapp.xmlparser.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.testapp.xmlparser.dto.UploadDTO;
import com.testapp.xmlparser.entity.Upload;
import com.testapp.xmlparser.exception.WrongUploadException;
import com.testapp.xmlparser.service.UploadService;
import com.testapp.xmlparser.xml.EpaperRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/uploads")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Uploads", description = "This section contains all endpoints to manage uploads in application")
public class RestUploadsController implements UploadsController {

    private final UploadService service;

    @Override
    @GetMapping
    @Operation(summary = "Get all uploads from database",
            responses = {@ApiResponse(responseCode = "200", description = "Uploads found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UploadDTO.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Wrong request")})
    public List<UploadDTO> getAllUploads() {
        return service.findAllUploads();
    }

    @Override
    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Create new upload")
    public ResponseEntity<?> createNewUpload(@RequestParam("file") MultipartFile uploadFile) {
        if (!validateXML(uploadFile)) {
            throw new WrongUploadException(new Exception(uploadFile.getOriginalFilename()));
        }
        try {
            EpaperRequest data = parseXML(uploadFile.getBytes());
            Upload newUpload = new Upload();
            newUpload.setNewspaperName(data.getDeviceInfo().getAppInfo().getNewspaperName());
            newUpload.setDpi(data.getDeviceInfo().getScreenInfo().getDpi());
            newUpload.setWidth(data.getDeviceInfo().getScreenInfo().getWidth());
            newUpload.setHeight(data.getDeviceInfo().getScreenInfo().getHeight());

            newUpload.setFileName(uploadFile.getOriginalFilename());
            newUpload.setUploadedAt(LocalDateTime.now());

            service.createUpload(newUpload);
        } catch (IOException e) {
            throw new WrongUploadException(new Exception(uploadFile.getOriginalFilename()));
        }
        return new ResponseEntity("Successfully uploaded - " + uploadFile.getOriginalFilename(),
                new HttpHeaders(), HttpStatus.OK);

    }

    private EpaperRequest parseXML(byte[] inputStream) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(inputStream, EpaperRequest.class);
    }

    private boolean validateXML(MultipartFile uploadFile) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            javax.xml.validation.Schema schema = factory.newSchema(
                    new File(Objects.requireNonNull(getClass()
                                    .getClassLoader()
                                    .getResource("xmlparser-schema-definition.xsd"))
                            .getFile()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(uploadFile.getInputStream()));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

}
