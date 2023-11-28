package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    @Override
    public FileDto findById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new NoSuchElementException("File with this id is not found");
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return new FileDto(fileOptional.get().getName(), content);

    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
