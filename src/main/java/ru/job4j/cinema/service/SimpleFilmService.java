package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public FilmDto findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            throw new NoSuchElementException("Film with this id is not found");
        }
        return getFilmDto(filmOptional.get());
    }

    @Override
    public List<FilmDto> findAll() {
        List<FilmDto> list = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            list.add(getFilmDto(film));
        }
        return list;
    }

    private FilmDto getFilmDto(Film film) {
        return FilmDto.of()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .year(film.getYear())
                .minimalAge(film.getMinimalAge())
                .durationInMinutes(film.getDuration())
                .genre(getGenre(film.getGenreId()))
                .fileId(film.getFileId())
                .build();
    }

    private String getGenre(int id) {
        var genre = genreRepository.findById(id);
        return genre.isPresent() ? genre.get().getName() : "-";
    }
}
