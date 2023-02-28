package com.testapp.xmlparser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDTO {

    @Schema(description = "Upload ID - generated automatically")
    private long id;

    @Schema(description = "Width of newspaper")
    @NotNull(message = "Width could not be null")
    private Integer width;

    @Schema(description = "Height of newspaper")
    @NotNull(message = "Height could not be null")
    private Integer height;

    @Schema(description = "Dpi of newspaper")
    @NotNull(message = "Dpi could not be null")
    private Integer dpi;

    @Schema(description = "Name of newspaper")
    @NotNull(message = "Name of newspaper could not be null")
    @Column(name = "newspaper_name")
    private String newspaperName;

    @Schema(description = "Name of file, that upload information of newspaper")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "file_name")
    private String fileName;

    @Schema(description = "Date and time, when information about newspaper was uploaded")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
