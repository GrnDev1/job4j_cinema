package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {
    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenRequestGetByIdThenResponse200() throws Exception {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.findById(1)).thenReturn(Optional.of(fileDto));
        var result = fileController.getById(1);
        assertThat(result).isEqualTo(ResponseEntity.ok(fileDto.getContent()));
    }

    @Test
    public void whenRequestGetByIdThenResponse404() {
        when(fileService.findById(1)).thenReturn(Optional.empty());
        var result = fileController.getById(1);
        assertThat(result).isEqualTo(ResponseEntity.notFound().build());
    }
}