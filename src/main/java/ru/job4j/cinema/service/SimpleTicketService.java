package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        var ticketOptional = ticketRepository.save(ticket);
        if (ticketOptional.isEmpty()) {
            throw new NoSuchElementException(
                    """
                            Failed to purchase a ticket for the specified seat.
                            It is probably already occupied.
                            Go to the ticket booking page and try again ...""");
        }
        return ticketOptional.get();
    }

    @Override
    public Ticket findById(int id) {
        var ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new NoSuchElementException("Ticket with this id is not found");
        }
        return ticketOptional.get();
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
