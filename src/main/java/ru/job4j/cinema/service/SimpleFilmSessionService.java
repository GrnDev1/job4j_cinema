package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmRepository filmRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmRepository sql2oFilmRepository, FilmSessionRepository sql2oFilmSessionRepository, HallRepository sql2oHallRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getFilmSessionDto(filmSessionOptional.get()));
    }

    @Override
    public List<FilmSessionDto> findAll() {
        List<FilmSessionDto> list = new ArrayList<>();
        for (FilmSession filmSession : filmSessionRepository.findAll()) {
            list.add(getFilmSessionDto(filmSession));
        }
        return list;
    }

    private FilmSessionDto getFilmSessionDto(FilmSession filmSession) {
        return FilmSessionDto.of()
                .id(filmSession.getId())
                .filmId(filmSession.getFilmId())
                .hallId(filmSession.getHallId())
                .filmName(getFilmName(filmSession.getFilmId()))
                .hallName(getHallName(filmSession.getHallId()))
                .startTime(filmSession.getStartTime())
                .endTime(filmSession.getEndTime())
                .price(filmSession.getPrice())
                .date(filmSession.getStartTime().format(DateTimeFormatter.ISO_DATE))
                .start(getTime(filmSession.getStartTime()))
                .end(getTime(filmSession.getEndTime()))
                .build();
    }

    private String getTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String getFilmName(int id) {
        var filmOptional = filmRepository.findById(id);
        return filmOptional.isPresent() ? filmOptional.get().getName() : "-";
    }

    private String getHallName(int id) {
        var hallOptional = hallRepository.findById(id);
        return hallOptional.isPresent() ? hallOptional.get().getName() : "-";
    }
}
