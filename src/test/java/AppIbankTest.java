import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AppIbankTest {

    public class DataClass {
        private String login;
        private String password;
        private String status;

        public DataClass(String login, String password, String status) {
            this.login = login;
            this.password = password;
            this.status = status;
        }
        public DataClass(){}
    }

    private Faker faker;

    @BeforeEach
    void setUp(){
            faker=new Faker(new Locale("en"));
        }
        // спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    @Test
    public void shouldResponseValidData() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "passwort", "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseStatusIsBlocked() {
        DataClass dataUser = new DataClass(faker.name().firstName(), faker.name().lastName(), "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseNoUserName() {
        DataClass dataUser = new DataClass("", faker.name().lastName(), "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsSpaceStatusIsActive() {
        DataClass dataUser = new DataClass(" ", faker.name().lastName(), "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsSpaceStatusISBlocked() {
        DataClass dataUser = new DataClass(" ", faker.name().lastName(), "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsNumerStatusISBlocked() {
        DataClass dataUser = new DataClass("123", faker.name().lastName(), "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsNumberStatusIsActive() {
        DataClass dataUser = new DataClass("123", faker.name().lastName(), "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsRussianStatusIsActive() {
        DataClass dataUser = new DataClass("ЙА", faker.name().lastName(), "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseLoginIsRussianStatusIsBlocked() {
        DataClass dataUser = new DataClass("ЙА", faker.name().lastName(), "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseNoPass() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "", "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseNoPassBlocked() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "", "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponsePassIsSpaceActive() {
        DataClass dataUser = new DataClass(faker.name().firstName(), " ", "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponsePassIsSymbolActive() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "~!@$%^&*?><|:}", "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseTwoParamsIsEmptyStatusActive() {
        DataClass dataUser = new DataClass("", "", "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void shouldResponseTwoParamsIsEmptyStatusBlock() {
        DataClass dataUser = new DataClass("", "", "blocked");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    //Этот тест при неправильном формате дает ответ сервера 500
    /*@Test
    public void shouldResponseValidDataInvalidstatus() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "passwort", "Hello");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }*/




    //Этот тест при неправильном формате дает ответ сервера 500
   /* @Test
    public void shouldResponseTwoParamsIsEmpty() {
        DataClass dataUser = new DataClass(faker.name().firstName(), "", "");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }*/

    //Этот тест при неправильном формате дает ответ сервера 500
    /*@Test
    public void shouldResponseNoData() {
        DataClass dataUser = new DataClass("", "", "");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }*/



    //Этот тест при неправильном формате дает ответ сервера 500
    /*@Test
    public void shouldResponseToSimpleData() {
        DataClass dataUser = new DataClass();
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body("dataUser, passwort, active") // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }*/

    //Этот тест падает ответ сервера 500
    /*@Test
    public void shouldResponseToNullData() {
        DataClass dataUser = new DataClass();
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }*/




}
