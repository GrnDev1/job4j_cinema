package ru.job4j.cinema.service;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.mappers.FilmSessionMapper;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmRepository filmRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmSessionMapper filmSessionMapper;

    public SimpleFilmSessionService(FilmRepository sql2oFilmRepository, FilmSessionRepository sql2oFilmSessionRepository, HallRepository sql2oHallRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.hallRepository = sql2oHallRepository;
        this.filmSessionMapper = Mappers.getMapper(FilmSessionMapper.class);
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        var filmSession = filmSessionOptional.get();
        return Optional.of(getDto(filmSession));
    }

    @Override
    public List<FilmSessionDto> findAll() {
        List<FilmSessionDto> list = new ArrayList<>();
        for (FilmSession filmSession : filmSessionRepository.findAll()) {
            list.add(getDto(filmSession));
        }
        return list;
    }

    private FilmSessionDto getDto(FilmSession filmSession) {
        return filmSessionMapper.getFilmSessionFromEntity(filmSession,
                getFilmName(filmSession.getFilmId()), getHallName(filmSession.getHallId()));
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
