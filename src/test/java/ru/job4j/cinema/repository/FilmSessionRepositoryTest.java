package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FilmSessionRepositoryTest {
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = FilmSessionRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var filmSessions = sql2oFilmSessionRepository.findAll();
        assertThat(filmSessions.size()).isEqualTo(20);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var filmSessionOptional = sql2oFilmSessionRepository.findById(3);
        var filmSession = FilmSession.of()
                .id(3)
                .filmId(3)
                .hallId(1)
                .startTime(LocalDateTime.of(2023, 12, 1, 17, 0))
                .endTime(LocalDateTime.of(2023, 12, 1, 18, 52))
                .price(10)
                .build();
        assertThat(filmSession).usingRecursiveComparison().isEqualTo(filmSessionOptional.get());
    }

    @Test
    public void whenFindByIdThenGetEmptyOptional() {
        var filmSessionOptional = sql2oFilmSessionRepository.findById(0);
        assertThat(filmSessionOptional).isEmpty();
    }
}