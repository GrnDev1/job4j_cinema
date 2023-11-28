package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        try {
            model.addAttribute("ticket", ticket);
            ticketService.save(ticket);
            return "tickets/success";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/failure";
        }
    }
}