package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.NoSuchElementException;

@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Hall findById(int id) {
        var hallOptional = hallRepository.findById(id);
        if (hallOptional.isEmpty()) {
            throw new NoSuchElementException("Hall with this id is not found");
        }
        return hallOptional.get();
    }
}
