package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleHallServiceTest {
    private final HallRepository hallRepository = mock(HallRepository.class);
    private final SimpleHallService simpleHallService = new SimpleHallService(hallRepository);

    @Test
    public void whenFindByIdThenGetSame() {
        var hall = new Hall(1, "Hall1", 10, 10, "description");
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        var result = simpleHallService.findById(hall.getId());
        assertThat(result).isEqualTo(hall);
    }

    @Test
    public void whenGetNoSuchElementException() {
        when(hallRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> simpleHallService.findById(1)).
                isInstanceOf(NoSuchElementException.class);
    }
}