package ru.job4j.cinema.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilmSessionDto implements Comparable<FilmSessionDto> {
    private int id;
    private int filmId;
    private int hallId;
    private String filmName;
    private String hallName;
    private String date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String start;
    private String end;
    private int price;

    public FilmSessionDto(int id, int filmId, int hallId, String filmName, String hallName, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.filmName = filmName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.date = startTime.format(DateTimeFormatter.ISO_DATE);
        this.start = getTime(startTime);
        this.end = getTime(endTime);
    }

    private String getTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public int compareTo(FilmSessionDto o) {
        return this.startTime.compareTo(o.startTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
