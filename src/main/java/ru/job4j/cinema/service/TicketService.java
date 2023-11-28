package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket save(Ticket ticket);

    Ticket findById(int id);

    List<Ticket> findAll();
}
