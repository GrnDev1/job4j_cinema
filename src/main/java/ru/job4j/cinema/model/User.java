package ru.job4j.cinema.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "full_name", "name"
    );
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String email;
    private String password;
}
