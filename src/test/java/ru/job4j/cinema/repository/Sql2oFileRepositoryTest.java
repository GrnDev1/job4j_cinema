package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFileRepositoryTest {
    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var filmSessions = sql2oFileRepository.findAll();
        assertThat(filmSessions.size()).isEqualTo(10);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var fileOptional = sql2oFileRepository.findById(3);
        var file = new File(3, "Conan the Barbarian", "files/Conan the Barbarian.jpg");
        assertThat(file).usingRecursiveComparison().isEqualTo(fileOptional.get());
    }

    @Test
    public void whenFindByIdThenGetEmptyOptional() {
        var filmSessionOptional = sql2oFileRepository.findById(0);
        assertThat(filmSessionOptional).isEmpty();
    }
}