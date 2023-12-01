package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private final UserService userService = mock(UserService.class);
    private final UserController userController = new UserController(userService);

    @Test
    public void whenRequestGetRegistrationPage() {
        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");
    }

    @Test
    public void whenPostRegisterUserThenRedirectToVacanciesPage() {
        var user = new User(1, "email", "name", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(user);

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/schedule");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSomeExceptionRegisterUserThenGetErrorPageWithMessage() {
        var model = new ConcurrentModel();
        var user = new User();
        var expectedMessage = "My message";
        when(userService.save(user)).thenThrow(new NoSuchElementException(expectedMessage));
        var view = userController.register(model, user);
        var actualExceptionMessage = model.getAttribute("error");
        assertThat(view).isEqualTo("users/register");
        assertThat(actualExceptionMessage).isEqualTo(expectedMessage);
        assertThat(user.getName()).isEqualTo("Guest");
    }

    @Test
    public void whenRequestGetLoginPage() {
        assertThat(userController.getLoginPage()).isEqualTo("users/login");
    }

    @Test
    public void whenPostLoginUserThenRedirectToVacanciesPage() {
        var user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);
        var model = new ConcurrentModel();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("redirect:/schedule");
    }

    @Test
    public void whenSomeExceptionLoginUserThenGetErrorPageWithMessage() {
        var user = new User(1, "email", "name", "password");
        var model = new ConcurrentModel();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        var expectedMessage = "My message";
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenThrow(new NoSuchElementException(expectedMessage));
        var view = userController.loginUser(user, model, request);
        var actualExceptionMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualExceptionMessage).isEqualTo(expectedMessage);
        assertThat(user.getName()).isEqualTo("Guest");
    }

    @Test
    public void whenRequestLogoutPage() {
        HttpSession session = mock(HttpSession.class);
        assertThat(userController.logout(session)).isEqualTo("redirect:/users/login");
    }
}