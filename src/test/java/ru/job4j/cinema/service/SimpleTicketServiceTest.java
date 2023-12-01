package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleTicketServiceTest {
    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final SimpleTicketService simpleTicketService = new SimpleTicketService(ticketRepository);

    @Test
    public void whenSaveTicketThenGetSame() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketRepository.save(ticket)).thenReturn(Optional.of(ticket));
        var result = simpleTicketService.save(ticket);
        assertThat(result).isEqualTo(ticket);
    }

    @Test
    public void whenSaveTicketThenGetNoSuchElementException() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketRepository.save(ticket)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> simpleTicketService.save(ticket)).
                isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void whenFindAllThenGetSame() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        var expectedTickets = List.of(ticket);
        when(ticketRepository.findAll()).thenReturn(expectedTickets);
        var result = simpleTicketService.findAll();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        var result = simpleTicketService.findById(ticket.getId());
        assertThat(result).isEqualTo(ticket);
    }

    @Test
    public void whenGetNoSuchElementException() {
        when(ticketRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> simpleTicketService.findById(1)).
                isInstanceOf(NoSuchElementException.class);
    }
}