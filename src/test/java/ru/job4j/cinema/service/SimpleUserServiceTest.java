package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final SimpleUserService simpleUserService = new SimpleUserService(userRepository);

    @Test
    public void whenSaveTicketThenGetSame() {
        var user = new User(1, "email", "name", "password");
        when(userRepository.save(user)).thenReturn(Optional.of(user));
        var result = simpleUserService.save(user);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void whenSaveTicketThenGetNoSuchElementException() {
        var user = new User(1, "email", "name", "password");
        when(userRepository.save(user)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> simpleUserService.save(user)).
                isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void whenFindUserThenGetSameUser() {
        var user = new User(1, "email", "name", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        var result = simpleUserService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void whenFindUserThenGetNoSuchElementException() {
        var user = new User(1, "email", "name", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> simpleUserService.findByEmailAndPassword(user.getEmail(), user.getPassword())).
                isInstanceOf(NoSuchElementException.class);
    }
}