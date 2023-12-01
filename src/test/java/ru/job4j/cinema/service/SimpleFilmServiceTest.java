package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmServiceTest {
    private FilmRepository filmRepository = mock(FilmRepository.class);
    private GenreRepository genreRepository = mock(GenreRepository.class);
    private SimpleFilmService simpleFilmService = new SimpleFilmService(filmRepository, genreRepository);

    @Test
    public void whenFindAllThenGetSame() {
        var film = Film.of()
                .id(1)
                .name("1")
                .description("1")
                .year(1)
                .genreId(2)
                .minimalAge(1)
                .duration(1)
                .fileId(1)
                .build();
        var expectedFilms = List.of(film);
        when(filmRepository.findAll()).thenReturn(expectedFilms);
        var result = simpleFilmService.findAll();
        var filmDtoResult = result.get(0);
        assertThat(result.size()).isEqualTo(1);
        assertThat(filmDtoResult.getId()).isEqualTo(film.getId());
        assertThat(filmDtoResult.getName()).isEqualTo(film.getName());
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var film1 = Film.of()
                .id(1)
                .name("1")
                .description("1")
                .year(1)
                .genreId(2)
                .minimalAge(1)
                .duration(1)
                .fileId(1)
                .build();
        when(filmRepository.findById(film1.getId())).thenReturn(Optional.of(film1));
        var result = simpleFilmService.findById(1);
        assertThat(result.getId()).isEqualTo(film1.getId());
        assertThat(result.getName()).isEqualTo(film1.getName());
    }
}