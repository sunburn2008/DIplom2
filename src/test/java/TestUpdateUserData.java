import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestUpdateUserData {

    UserController userController = new UserController();
    AuthController authController = new AuthController();

    String email = "MegaNagibator2000@yandex.ru";
    String password = "133331";
    String name = "Филлипок";

    @Test
    public void updateUser() {
        String token = userController.postUser(email, password, name)
                .then()
                .extract().body().path("accessToken");
        userController.updateUserData(email, token)
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
