package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {
    private FilmService filmService = mock(FilmService.class);
    private FilmController filmController = new FilmController(filmService);

    @Test
    public void whenRequestFilmListPageThenGetPageWithFilms() {
        var filmDto = FilmDto.of()
                .id(1)
                .name("1")
                .description("1")
                .year(1)
                .minimalAge(1)
                .durationInMinutes(1)
                .genre("1")
                .fileId(1)
                .build();
        var expectedFilms = List.of(filmDto);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}