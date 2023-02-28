package com.testapp.xmlparser.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestDatabaseConfiguration {
    private static final PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName dbImage = DockerImageName.parse("postgres:15.2-alpine")
                .asCompatibleSubstituteFor("postgres");
        postgreSQLContainer = new PostgreSQLContainer(dbImage)
                .withUsername("postgres")
                .withPassword("password123");
        postgreSQLContainer.start();

        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    }
}
