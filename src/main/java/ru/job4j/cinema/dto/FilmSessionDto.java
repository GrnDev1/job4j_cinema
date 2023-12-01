package ru.job4j.cinema.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(builderMethodName = "of")
public class FilmSessionDto implements Comparable<FilmSessionDto> {
    @EqualsAndHashCode.Include
    private int id;
    private int filmId;
    private int hallId;
    private String filmName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String date;
    private String start;
    private String end;
    private int price;

    @Override
    public int compareTo(FilmSessionDto o) {
        return this.startTime.compareTo(o.startTime);
    }
}
