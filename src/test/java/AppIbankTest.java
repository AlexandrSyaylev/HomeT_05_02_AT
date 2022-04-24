import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AppIbankTest {

    public class LoginPage {
        public void login(DataGen.RegistrationDto info) {
            //open("http://localhost:9999/api/system/users");
            $("input[name='login']").setValue(info.getLogin());
            $("input[name='password']").setValue(info.getPassword());
            $("button[type='button']").click();
        }
        public void enterWrongPass(DataGen.RegistrationDto info){
            $("input[name='login']").setValue(info.getLogin());
            $("input[name='password']").setValue(DataGen.getRandomPassword());
            $("button[type='button']").click();
        }
        public void enterWrongLogin(DataGen.RegistrationDto info){
            $("input[name='login']").setValue(DataGen.getRandomLogin());
            $("input[name='password']").setValue(info.getPassword());
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
        new LoginPage().login(registeredUser);
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGen.Registration.getUser("active");
        new LoginPage().login(notRegisteredUser);
        $(withText("Ошибка!")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGen.Registration.getRegisteredUser("blocked");
        new LoginPage().login(blockedUser);
        $(withText("Ошибка!")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().enterWrongPass(registeredUser);
        $(withText("Ошибка!")).shouldBe(visible, Duration.ofSeconds(5));
        //var wrongLogin = DataGen.getRandomLogin();
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        new LoginPage().enterWrongPass(registeredUser);
        $(withText("Ошибка!")).shouldBe(visible, Duration.ofSeconds(5));
        //var wrongPassword = DataGen.getRandomPassword();
    }


}
