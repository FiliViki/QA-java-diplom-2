import helpers.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

@DisplayName("Создание нового пользователя")
public class CreateUserTest extends BaseTest {
    UserApi api = new UserApi();

    @Test()
    @DisplayName("Пользователь должен быть создан")
    public void createUser() {
        Response response = api.createUser(login, password, "Andrey");

        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Test()
    @DisplayName("Нельзя создать два одинаковых пользователя")
    public void createUserDouble() {
        Response responseOriginal = api.createUser(login, password, "Andrey");

        responseOriginal.then().statusCode(SC_OK);

        Response responseDouble = api.createUser(login, password, "Andrey");

        responseDouble.then()
            .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("User already exists"))
                .body("success", equalTo(false));
    }


    @Test()
    @DisplayName("Пользователь должен иметь логин")
    public void createUserWithoutLogin() {
        Response response = api.createUser("", password, "Andrey");

        response.then()
            .statusCode(SC_FORBIDDEN)
            .body("message", equalTo("Email, password and name are required fields"))
            .body("success", equalTo(false));
    }


    @Test()
    @DisplayName("Пользователь должен иметь пароль")
    public void createUserWithoutPassword() {
        Response response = api.createUser(login, "", "Andrey");

        response.then()
            .statusCode(SC_FORBIDDEN)
            .body("message", equalTo("Email, password and name are required fields"))
            .body("success", equalTo(false));
    }


    @Test()
    @DisplayName("Пользователь должен иметь имя")
    public void createUserWithoutName() {
        Response response = api.createUser(login, password, "");

        response.then()
            .statusCode(SC_FORBIDDEN)
            .body("message", equalTo("Email, password and name are required fields"))
            .body("success", equalTo(false));
    }

    @After()
    public void removeUser() {
        api.deleteUser(login, password);
    }
}