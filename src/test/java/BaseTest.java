import io.restassured.RestAssured;

import java.util.List;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {
    // Тестовые данные
    public final String login = "andrey@example.ru";
    public final String password = "qwerty123";
    public final List<String> ingredients;

    public BaseTest() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        ingredients = given()
            .header("Content-Type", "application/json")
            .get("/api/ingredients")
            .jsonPath().getList("data._id", String.class);
    }
}
