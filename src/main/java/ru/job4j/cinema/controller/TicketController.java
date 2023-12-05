package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        var ticketOptional = ticketService.save(ticket);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message",
                    """
                            Failed to purchase a ticket for the specified seat.
                            It is probably already occupied.
                            Go to the ticket booking page and try again ...""");
            return "errors/failure";
        }
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/success";
    }
}