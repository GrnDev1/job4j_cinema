package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oGenreRepositoryTest {
    private static Sql2oGenreRepository sql2oGenreRepository;
    private final static List<Genre> GENRES = List.of(
            new Genre(1, "Drama"),
            new Genre(2, "Action"),
            new Genre(3, "Documentary"));

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var genres = sql2oGenreRepository.findAll();
        assertThat(GENRES).usingRecursiveComparison().isEqualTo(genres);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var genreOptional = sql2oGenreRepository.findById(3);
        assertThat(GENRES.get(2)).usingRecursiveComparison().isEqualTo(genreOptional.get());
    }

    @Test
    public void whenFindByIdThenGetEmptyOptional() {
        var genreOptional = sql2oGenreRepository.findById(GENRES.size() + 1);
        assertThat(genreOptional).usingRecursiveComparison().isEqualTo(Optional.empty());
    }
}