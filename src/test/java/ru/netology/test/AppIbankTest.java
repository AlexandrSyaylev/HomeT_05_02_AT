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
        public void login(DataGen.RegistrationDto info, boolean wrongLogin, boolean wrongPassword) {
            if (wrongLogin) {
                $("input[name='login']").setValue(DataGen.getRandomLogin());
            } else {
                $("input[name='login']").setValue(info.getLogin());
            }
            if (wrongPassword) {
                $("input[name='password']").setValue(DataGen.getRandomPassword());
            } else {
                $("input[name='password']").setValue(info.getPassword());
            }
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
        new LoginPage().login(registeredUser, false, false);
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGen.Registration.getUser("active");
        new LoginPage().login(notRegisteredUser, false, false);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGen.Registration.getRegisteredUser("blocked");
        new LoginPage().login(blockedUser, false, false);
        $(withText("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().login(registeredUser, true, false);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().login(registeredUser, true, true);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));
    }

}
