package ru.job4j.cinema.mappers;

import org.mapstruct.Mapper;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;

@Mapper
public interface FileMapper {
    FileDto getFileFromEntity(File file, byte[] content);
}
