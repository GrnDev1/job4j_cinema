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
public class Hall {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "row_count", "rowCount",
            "place_count", "placeCount",
            "description", "description"
    );
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private int rowCount;
    private int placeCount;
    private String description;

    public Hall(int id, String name, int rowCount, int placeCount, String description) {
        this.id = id;
        this.name = name;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.description = description;
    }
}
