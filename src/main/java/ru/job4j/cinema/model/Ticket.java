package ru.job4j.cinema.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "session_id", "sessionId",
            "row_number", "row",
            "place_number", "place",
            "user_id", "userId"
    );
    @EqualsAndHashCode.Include
    private int id;
    private int sessionId;
    private int row;
    private int place;
    private int userId;
}
