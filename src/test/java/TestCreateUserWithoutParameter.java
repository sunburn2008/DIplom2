import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateUserWithoutParameter {

    private UserController userController = new UserController();
    private AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";

    @Test
    @Description("Создание пользователя")
    public void createWithoutOneParameterUser() {
        userController.postUserWithoutOneParameter(email, password)
                .then()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(email, password);
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
        }
    }
}
