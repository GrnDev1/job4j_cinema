package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oHallRepositoryTest {
    private static Sql2oHallRepository sql2oHallRepository;
    private final static List<Hall> HALLS = List.of(
            new Hall(1, "Hall 1", 10, 10, "Hall 1. Capacity 10x10"),
            new Hall(2, "Hall 2", 15, 20, "Hall 2. Capacity 15x20"));

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var halls = sql2oHallRepository.findAll();
        assertThat(HALLS).usingRecursiveComparison().isEqualTo(halls);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var hallOptional = sql2oHallRepository.findById(1);
        assertThat(HALLS.get(0)).usingRecursiveComparison().isEqualTo(hallOptional.get());
    }

    @Test
    public void whenFindByIdThenGetEmptyOptional() {
        var hallOptional = sql2oHallRepository.findById(HALLS.size() + 1);
        assertThat(hallOptional).usingRecursiveComparison().isEqualTo(Optional.empty());
    }
}