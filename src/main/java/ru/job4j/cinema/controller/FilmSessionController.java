package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import java.util.Collections;
import java.util.List;

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
        var filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "Session with this id is not found");
            return "errors/404";
        }
        model.addAttribute("filmSession", filmSessionOptional.get());
        var hallOptional = hallService.findById(filmSessionOptional.get().getHallId());
        if (hallOptional.isEmpty()) {
            model.addAttribute("message", "Hall with this id is not found");
            return "errors/404";
        }
        model.addAttribute("hall", hallOptional.get());
        return "filmSessions/create";
    }

    private List<FilmSessionDto> sort(List<FilmSessionDto> list) {
        Collections.sort(list);
        return list;
    }
}