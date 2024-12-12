import io.qameta.allure.Step;

import java.util.HashMap;

public class AuthController {

    @Step("Получение токена пользователя")
    public String getUserToken(String email, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        return Specification.getRequestSpecification()
                .body(map)
                .post("/api/auth/login")
                .then()
                .extract().body().path("accessToken");
    }
}
