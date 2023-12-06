package ru.job4j.cinema.service;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.mappers.FilmMapper;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FilmMapper filmMapper;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
        this.filmMapper = Mappers.getMapper(FilmMapper.class);
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var film = filmOptional.get();
        return Optional.of(filmMapper.getFilmFromEntity(film, getGenre(film.getGenreId())));
    }

    @Override
    public List<FilmDto> findAll() {
        List<FilmDto> list = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            list.add(filmMapper.getFilmFromEntity(film, getGenre(film.getGenreId())));
        }
        return list;
    }

    private String getGenre(int id) {
        var genre = genreRepository.findById(id);
        return genre.isPresent() ? genre.get().getName() : "-";
    }
}
