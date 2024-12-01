import helpers.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Логин пользователя")
public class LoginUsersTest extends BaseTest {
    UserApi api = new UserApi();

    @Before()
    public void setUp() {
        api.createUser(login, password, "Andrey");
    }

    @Test()
    @DisplayName("Пользователь должен успешно авторизоваться")
    public void loginUser(){
        Response response = api.loginUser(login, password);

        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }



    @Test()
    @DisplayName("Пользователь не должен быть авторизован с несуществующим логином")
    public void loginUserWithWrongLogin() {
        Response response = api.loginUser(login + "wrong", password);

        response.then().statusCode(SC_UNAUTHORIZED).body("message", equalTo("email or password are incorrect"));
    }

    @Test()
    @DisplayName("Пользователь не должен быть авторизован с неправильным паролем")
    public void loginUserWithWrongPassword() {
        Response response = api.loginUser(login, password + "wrong");

        response.then().statusCode(SC_UNAUTHORIZED).body("message", equalTo("email or password are incorrect"));
    }

    @After()
    public void removeUser() {
        api.deleteUser(login, password);
    }

}
