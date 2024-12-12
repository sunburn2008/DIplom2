import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateUser {

    private UserController userController = new UserController();
    private AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";
    String name = "Филлипок";

    @Test
    @Description("Создание пользователя")
    public void createUser() {
        userController.postUser(email, password, name)
                .then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(email, password);
        userController.deleteUser(token).then().statusCode(202);
    }
}

