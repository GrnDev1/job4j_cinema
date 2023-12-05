package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmSessionServiceTest {
    private final FilmRepository filmRepository = mock(FilmRepository.class);
    private final FilmSessionRepository filmSessionRepository = mock(FilmSessionRepository.class);
    private final HallRepository hallRepository = mock(HallRepository.class);
    private final SimpleFilmSessionService simpleFilmSessionService =
            new SimpleFilmSessionService(filmRepository, filmSessionRepository, hallRepository);

    @Test
    public void whenFindAllThenGetSame() {
        var filmSession = FilmSession.of()
                .id(3)
                .filmId(3)
                .hallId(1)
                .startTime(LocalDateTime.of(2023, 12, 1, 17, 0))
                .endTime(LocalDateTime.of(2023, 12, 1, 18, 52))
                .price(10)
                .build();
        var expectedFilmSession = new ArrayList<>(List.of(filmSession));
        when(filmSessionRepository.findAll()).thenReturn(expectedFilmSession);
        var dtoResult = simpleFilmSessionService.findAll().get(0);

        assertThat(dtoResult.getStartTime()).isEqualTo(filmSession.getStartTime());
        assertThat(dtoResult.getEndTime()).isEqualTo(filmSession.getEndTime());
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var filmSession = FilmSession.of()
                .id(3)
                .filmId(3)
                .hallId(1)
                .startTime(LocalDateTime.of(2023, 12, 1, 17, 0))
                .endTime(LocalDateTime.of(2023, 12, 1, 18, 52))
                .price(10)
                .build();
        when(filmSessionRepository.findById(filmSession.getId())).thenReturn(Optional.of(filmSession));
        var dtoResult = simpleFilmSessionService.findById(filmSession.getId()).get();

        assertThat(dtoResult.getStartTime()).isEqualTo(filmSession.getStartTime());
        assertThat(dtoResult.getEndTime()).isEqualTo(filmSession.getEndTime());
    }
}