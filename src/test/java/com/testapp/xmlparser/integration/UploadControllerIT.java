package com.testapp.xmlparser.integration;

import com.testapp.xmlparser.configuration.TestDatabaseConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(TestDatabaseConfiguration.class)
@ActiveProfiles({"test"})
public class UploadControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Test
    void testCreateNewUploads() throws Exception {
        String testFileName = "testFile1.xml";
        File file1 = new File(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource(testFileName))
                .getFile());

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", testFileName,
                "multipart/form-data", new FileInputStream(file1));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/uploads")
                        .file(mockMultipartFile1)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";" + "charset=UTF-8"))
                .andExpect(content().string("Successfully uploaded - " + testFileName));
    }

    @Test
    void testGetAllUploads() throws Exception {
        String testFileName1 = "testFile1.xml";
        String testFileName2 = "testFile2.xml";
        File file1 = new File(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource(testFileName1))
                .getFile());

        File file2 = new File(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource(testFileName2))
                .getFile());

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", testFileName1,
                "multipart/form-data", new FileInputStream(file1));

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file", testFileName2,
                "multipart/form-data", new FileInputStream(file2));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/uploads")
                        .file(mockMultipartFile1)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/uploads")
                        .file(mockMultipartFile2)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/uploads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].newspaperName").value("abb"))
                .andExpect(jsonPath("$.[0].width").value(1280))
                .andExpect(jsonPath("$.[0].height").value(752))
                .andExpect(jsonPath("$.[1].dpi").value(400));
    }

    @Test
    void testUploadBadFile() throws Exception {
        String badTestFile = "badTestFile.xml";
        File file1 = new File(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource(badTestFile))
                .getFile());

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", badTestFile,
                "multipart/form-data", new FileInputStream(file1));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/uploads")
                        .file(mockMultipartFile1)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(jsonPath("$.detail").value("Wrong file structure of the file."))
                .andExpect(jsonPath("$.fileName").value(badTestFile));
    }

}
