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
public class User {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "full_name", "name"
    );
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
