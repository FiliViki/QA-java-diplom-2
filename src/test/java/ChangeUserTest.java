import helpers.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("Обновление пользователя")
public class ChangeUserTest extends BaseTest {
        UserApi api = new UserApi();

        @Before()
        public void setUp() {
                api.createUser(login, password, "Andrey");
        }

        @Test()
        @DisplayName("Пользователь должен быть обновлен")
        public void updateUser() {
                Response response = api.updateUser(login, password, "sergey@example.ru", "Sergey");

                response.then().statusCode(SC_OK).body("success", equalTo(true));

                api.deleteUser("sergey@example.ru", password);
        }

        @Test()
        @DisplayName("Пользователь не должен быть обновлен")
        public void updateUserWithoutCredentials() {
                Response response = api.updateUser("", "", "sergey@example.ru", "Sergey");

                response.then().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
        }

        @After()
        public void removeUser() {
                api.deleteUser(login, password);
        }

}
