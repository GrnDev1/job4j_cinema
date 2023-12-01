package ru.job4j.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
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

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int row, int place, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.row = row;
        this.place = place;
        this.userId = userId;
    }
}
