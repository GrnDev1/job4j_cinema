package ru.job4j.cinema.mappers;

import org.mapstruct.Mapper;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface FilmSessionMapper {
    default FilmSessionDto getFilmSessionFromEntity(FilmSession filmSession, String filmName, String hallName) {
        return FilmSessionDto.of()
                .id(filmSession.getId())
                .filmId(filmSession.getFilmId())
                .hallId(filmSession.getHallId())
                .filmName(filmName)
                .hallName(hallName)
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
}
