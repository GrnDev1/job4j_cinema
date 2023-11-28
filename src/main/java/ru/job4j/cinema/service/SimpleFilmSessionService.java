package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    public FilmSessionDto findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            throw new NoSuchElementException("Session with this id is not found");
        }
        return getFilmSessionDto(filmSessionOptional.get());
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
        return new FilmSessionDto(filmSession.getId(), filmSession.getFilmId(), filmSession.getHallId(),
                getFilmName(filmSession.getFilmId()), getHallName(filmSession.getHallId()),
                filmSession.getStartTime(), filmSession.getEndTime(), filmSession.getPrice());
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
