package ru.job4j.cinema.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(builderMethodName = "of")
public class FilmDto {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private int durationInMinutes;
    private String genre;
    private int fileId;
}