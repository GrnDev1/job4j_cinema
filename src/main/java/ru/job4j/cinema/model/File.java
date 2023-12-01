package ru.job4j.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File {
    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String path;
}
