package ru.job4j.cinema.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String path;
}
