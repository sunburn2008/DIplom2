import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateDoubleUser {
    private UserController userController = new UserController();
    private AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";
    String name = "Филлипок";

    @Test
    @Description("Создание ранее зарегистрированного пользователя")
    public void createDoubleUser() {
        userController.postUser(email, password, name);
        userController.postUser(email, password, name)
                .then()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(email, password);
        userController.deleteUser(token).then().statusCode(202);
    }
}
