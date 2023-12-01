package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {
    private final IndexController indexController = new IndexController();

    @Test
    public void whenRequestIndexPageThenGetHomePage() {
        assertThat(indexController.getIndex()).isEqualTo("index");
    }
}