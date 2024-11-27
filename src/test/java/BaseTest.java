import io.restassured.RestAssured;

import java.util.Arrays;
import java.util.List;

public abstract class BaseTest {
    // Тестовые данные
    public final String login = "andrey@example.ru";
    public final String password = "qwerty123";
    // Заранее посмотрели https://stellarburgers.nomoreparties.site/api/ingredients
    public final List<String> ingredients = Arrays.asList(
            "61c0c5a71d1f82001bdaaa6d", // Флюоресцентная булка R2-D3
            "61c0c5a71d1f82001bdaaa6f", // Мясо бессмертных моллюсков Protostomia
            "61c0c5a71d1f82001bdaaa72"  // Соус Spicy-X
    );

    public BaseTest() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}
