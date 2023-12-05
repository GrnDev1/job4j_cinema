package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFileServiceTest {
    private FileRepository fileRepository = mock(FileRepository.class);
    private SimpleFileService simpleFileService = new SimpleFileService(fileRepository);

    @Test
    public void whenFindByIdThenGetSame() {
        try {
            Path path = Files.createTempFile(null, null);
            var filename = path.toFile().getName();
            var content = new byte[]{1, 2, 3};
            Files.write(path, content);
            var fileDto = new FileDto(filename, content);

            when(fileRepository.findById(1)).thenReturn(Optional.of(new File(1, filename, path.toString())));
            var result = simpleFileService.findById(1);
            assertThat(result.get()).usingRecursiveComparison().isEqualTo(fileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenGetNoSuchElementException() {
        when(fileRepository.findById(1)).thenReturn(Optional.empty());
        assertThat(simpleFileService.findById(1)).isEmpty();
    }
}