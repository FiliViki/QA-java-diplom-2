import helpers.OrderApi;
import helpers.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest {
    UserApi userApi = new UserApi();
    OrderApi orderApi = new OrderApi();

    @Before()
    public void setUp() {
        userApi.createUser(login, password, "Andrey");
    }

    @Test()
    @DisplayName("Заказ должен быть создан")
    public void createOrder() {
        Response response = orderApi.createOrder(login, password, ingredients);

        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Test()
    @DisplayName("Заказ должен быть создан без авторизации")
    public void createOrderWithoutLogin() {
        Response response = orderApi.createOrder("", "", ingredients);

        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Test()
    @DisplayName("Заказ не должен быть создан без ингридиентов")
    public void createOrderWithoutIngredients() {
        Response response = orderApi.createOrder(login, password, List.of());

        response.then().statusCode(SC_BAD_REQUEST).body("success", equalTo(false));
    }

    @Test()
    @DisplayName("Заказ не должен быть создан с невалидным ингридиентом")
    public void createOrderWithIncorrectIngredients() {
        Response response = orderApi.createOrder(login, password, Arrays.asList("61c0c5a71d1f82001bdaaa72090190"));

        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After()
    public void removeUser() {
        userApi.deleteUser(login, password);
    }
}
