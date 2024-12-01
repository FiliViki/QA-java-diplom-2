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

@DisplayName("Получение заказа")
public class GetOrderTest extends BaseTest {
    UserApi userApi = new UserApi();
    OrderApi orderApi = new OrderApi();

    @Before()
    public void setUp() {
        userApi.createUser(login, password, "Andrey");
        orderApi.createOrder(login, password, ingredients);
    }

    @Test()
    @DisplayName("Заказ должен быть от авторизованного пользователя")
    public void getOrder() {
        Response response = orderApi.getOrder(login, password);

        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Test()
    @DisplayName("Заказ не должен быть создан без авторизации")
    public void getOrderWithoutLogin() {
        Response response = orderApi.getOrder("", "");

        response.then().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
    }


    @After()
    public void removeUser() {
        userApi.deleteUser(login, password);
    }
}
