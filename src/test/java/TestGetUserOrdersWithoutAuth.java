import io.qameta.allure.Description;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestGetUserOrdersWithoutAuth {

    UserController userController = new UserController();

    String value = "MegaNagibator2000@yandex.ru";

    @Test
    @Description("Создание заказов бей авторизации")
    public void testCreateOrderWithoutAuth() {
        userController.getUserDataWithoutAuth(value)
                .then()
                .log().all()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }
}
