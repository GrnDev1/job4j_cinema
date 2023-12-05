package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    private final TicketService ticketService = mock(TicketService.class);
    private final TicketController ticketController = new TicketController(ticketService);

    @Test
    public void whenPostTicketCreateThenSameDataAndRedirectSuccessPage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenSomeExceptionCreateTicketWithSamePlacesThenGetErrorPage() {
        when(ticketService.save(any())).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = ticketController.create(new Ticket(), model);
        var actualExceptionMessage = model.getAttribute("message");
        assertThat(view).isEqualTo("errors/failure");
        assertThat(actualExceptionMessage).isEqualTo("""
                Failed to purchase a ticket for the specified seat.
                It is probably already occupied.
                Go to the ticket booking page and try again ...""");
    }
}