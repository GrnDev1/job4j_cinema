package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oTicketRepositoryTest {
    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearTickets() {
        try (var connection = sql2o.open()) {
            var sql = """
                    TRUNCATE TABLE tickets
                    """;
            var query = connection.createQuery(sql);
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1)).get();
        var savedTicket = sql2oTicketRepository.findById(ticket.getId()).get();
        assertThat(ticket).usingRecursiveComparison().isEqualTo(savedTicket);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1)).get();
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, 1, 1, 2, 1)).get();
        var result = sql2oTicketRepository.findAll();
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(ticket1, ticket2));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1));
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, 1, 1, 1, 1));
        assertThat(ticket2).isEmpty();
    }
}
