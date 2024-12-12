import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestUpdateUserDataWithoutAuth {
    UserController userController = new UserController();
    AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";
    String name = "Филлипок";

    @Test
    public void updateUser() {
        userController.postUser(email, password, name);
        userController.updateUserData(email, "")
                .then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(email, password);
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
        }
    }
}
