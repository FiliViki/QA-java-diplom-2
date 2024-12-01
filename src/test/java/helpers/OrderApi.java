package helpers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String BASE_URL = "/api/orders";

    private final UserApi userApi = new UserApi();

    @Step("Создание заказа")
    public Response createOrder(String login, String password, List<String> ingredients) {
        Response response = userApi.loginUser(login, password);

        String accessToken = response.jsonPath().getString("accessToken");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", accessToken);

        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("ingredients", ingredients);

        return given()
                .header("Content-Type", "application/json")
                .headers(headers)
                .body(requestBody)
                .post(BASE_URL);
    }

    @Step("Получение заказа")
    public Response getOrder(String login, String password) {
        Response response = userApi.loginUser(login, password);

        String accessToken = response.jsonPath().getString("accessToken");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", accessToken);

        return given()
                .header("Content-Type", "application/json")
                .headers(headers)
                .get(BASE_URL);
    }
}
