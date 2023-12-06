package ru.job4j.cinema.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

@Mapper
public interface FilmMapper {
    @Mapping(target = "durationInMinutes", source = "film.duration")
    @Mapping(target = "genre", source = "genreName")
    FilmDto getFilmFromEntity(Film film, String genreName);
}
