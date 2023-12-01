package ru.job4j.cinema.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(builderMethodName = "of")
public class FilmSession {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );
    @EqualsAndHashCode.Include
    private int id;
    private int filmId;
    private int hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;
}
