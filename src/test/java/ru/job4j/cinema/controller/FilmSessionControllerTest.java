package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {
    private final FilmSessionService filmSessionService = mock(FilmSessionService.class);
    private final HallService hallService = mock(HallService.class);
    private final FilmSessionController filmSessionController = new FilmSessionController(filmSessionService, hallService);

    @Test
    public void whenRequestFilmSessionListPageThenGetPageWithFilmSessions() {
        var filmSessionDto = FilmSessionDto.of()
                .id(1)
                .filmId(1)
                .hallId(1)
                .filmName("Film")
                .hallName("HAll1")
                .startTime(LocalDateTime.of(2023, 12, 1, 12, 0))
                .endTime(LocalDateTime.of(2023, 12, 1, 13, 33))
                .price(10)
                .date("2023-12-01")
                .start("12 : 00")
                .end("13 : 33")
                .build();
        var expectedFilmSessionDto = new ArrayList<>(List.of(filmSessionDto));
        when(filmSessionService.findAll()).thenReturn(expectedFilmSessionDto);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("filmSessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessionDto);
    }

    @Test
    public void whenRequestFilmSessionGetByIdThenGetPageWithFilmSession() {
        var expectedFilmSessionDto = FilmSessionDto.of()
                .id(1)
                .filmId(1)
                .hallId(1)
                .filmName("Film")
                .hallName("HAll1")
                .startTime(LocalDateTime.of(2023, 12, 1, 12, 0))
                .endTime(LocalDateTime.of(2023, 12, 1, 13, 33))
                .price(10)
                .date("2023-12-01")
                .start("12 : 00")
                .end("13 : 33")
                .build();
        when(filmSessionService.findById(1)).thenReturn(expectedFilmSessionDto);
        var expectedHall = new Hall(1, expectedFilmSessionDto.getHallName(), 10, 10, "-");
        when(hallService.findById(expectedFilmSessionDto.getHallId())).thenReturn(expectedHall);
        var model = new ConcurrentModel();
        var view = filmSessionController.getById(model, 1);
        var actualFilmSessions = model.getAttribute("filmSession");
        var actualHall = model.getAttribute("hall");

        assertThat(view).isEqualTo("filmSessions/create");
        assertThat(actualFilmSessions).usingRecursiveComparison().isEqualTo(expectedFilmSessionDto);
        assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }
}