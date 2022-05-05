package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGen;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AppIbankTest {

    public class LoginPage {
        public void login(String login, String password) {
            $("input[name='login']").setValue(login);
            $("input[name='password']").setValue(password);
            $("button[type='button']").click();
        }
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().login(registeredUser.getLogin(), registeredUser.getPassword());
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGen.Registration.getUser("active");
        new LoginPage().login(notRegisteredUser.getLogin(), notRegisteredUser.getPassword());
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGen.Registration.getRegisteredUser("blocked");
        new LoginPage().login(blockedUser.getLogin(), blockedUser.getPassword());
        $(withText("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().login(DataGen.getRandomLogin(), registeredUser.getPassword());
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().login(registeredUser.getLogin(), DataGen.getRandomPassword());
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));
    }

}
