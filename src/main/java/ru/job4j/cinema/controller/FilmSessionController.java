package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/schedule")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;
    private final HallService hallService;

    public FilmSessionController(FilmSessionService filmSessionService, HallService hallService) {
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", sort(filmSessionService.findAll()));
        return "filmSessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        try {
            var filmSession = filmSessionService.findById(id);
            model.addAttribute("filmSession", filmSession);
            var hall = hallService.findById(filmSession.getHallId());
            model.addAttribute("hall", hall);
            return "filmSessions/create";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    private List<FilmSessionDto> sort(List<FilmSessionDto> list) {
        Collections.sort(list);
        return list;
    }
}