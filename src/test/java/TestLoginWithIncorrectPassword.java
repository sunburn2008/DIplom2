import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestLoginWithIncorrectPassword {

    private UserController userController = new UserController();
    private AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";
    String incorrectPassword = "123321";
    String name = "Филлипок";

    @Test
    @Description("Создание пользователя")
    public void loginUser() {
        userController.postUser(email, password, name);
        userController.loginUser(email, incorrectPassword)
                .then()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(email, password);
        userController.deleteUser(token).then().statusCode(202);
    }
}
