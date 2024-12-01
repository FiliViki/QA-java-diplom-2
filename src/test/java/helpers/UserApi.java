package helpers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URL = "/api/auth";

    @Step("Создание пользователя")
    public Response createUser(String login, String password, String name) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", login);
        requestBody.put("password", password);
        requestBody.put("name", name);

        return given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(BASE_URL + "/register");
    }

    @Step("Обновление пользователя")
    public Response updateUser(String login, String password, String email, String name) {
        Response response = loginUser(login, password);

        String accessToken = response.jsonPath().getString("accessToken");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", accessToken);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("name", name);

        return given()
                .header("Content-Type", "application/json")
                .headers(headers)
                .body(requestBody)
                .patch(BASE_URL + "/user");
    }

    @Step("Логин пользователя")
    public Response loginUser(String login, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", login);
        requestBody.put("password", password);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL + "/login");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String login, String password) {
        Response response = loginUser(login, password);

        String accessToken = response.jsonPath().getString("accessToken");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", accessToken);

        return given()
                .contentType(ContentType.JSON)
                .headers(headers)
                .delete(BASE_URL + "/user");
    }
}
