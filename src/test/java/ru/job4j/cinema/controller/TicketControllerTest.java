package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.NoSuchElementException;

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
        when(ticketService.save(ticket)).thenReturn(ticket);

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenSomeExceptionCreateTicketWithSamePlacesThenGetErrorPage() {
        var expectedMessage = "Failure";
        when(ticketService.save(any())).thenThrow(new NoSuchElementException(expectedMessage));
        var model = new ConcurrentModel();
        var view = ticketController.create(new Ticket(), model);
        var actualExceptionMessage = model.getAttribute("message");
        assertThat(view).isEqualTo("errors/failure");
        assertThat(actualExceptionMessage).isEqualTo(expectedMessage);
    }
}