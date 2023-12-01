package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFilmRepositoryTest {
    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var films = sql2oFilmRepository.findAll();
        assertThat(films.size()).isEqualTo(10);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var filmOptional = sql2oFilmRepository.findById(3);
        var film = Film.of()
                .id(3)
                .name("Conan the Barbarian")
                .description("A vengeful barbarian warrior sets off to get his revenge "
                        + "on the evil warlord who attacked his village and murdered his father when he was a boy.")
                .year(2011)
                .genreId(2)
                .minimalAge(18)
                .duration(112)
                .fileId(3)
                .build();
        assertThat(film).usingRecursiveComparison().isEqualTo(filmOptional.get());
    }

    @Test
    public void whenFindByIdThenGetEmptyOptional() {
        var filmOptional = sql2oFilmRepository.findById(0);
        assertThat(filmOptional).isEmpty();
    }
}