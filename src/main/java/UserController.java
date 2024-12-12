import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

public class UserController {

    @Step("Создание пользователя")
    public Response postUser(String email, String password, String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        return Specification.getRequestSpecification().body(map).post("/api/auth/register");
    }

    @Step("Создание пользователя без 1 параметра")
    public Response postUserWithoutOneParameter(String email, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return Specification.getRequestSpecification().body(map).post("/api/auth/register");
    }

    @Step("Логин пользователя")
    public Response loginUser(String email, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return Specification.getRequestSpecification().body(map).post("/api/auth/login");
    }

    @Step("Обновление данных пользователя")
    public Response updateUserData(String email, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        return Specification.getRequestSpecification().header("Authorization", token).body(map).patch("/api/auth/user");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return Specification.getRequestSpecification()
                .header("Authorization", token)
                .delete("/api/auth/user");
    }

    @Step("Получение заказов пользователя")
    public Response getUserOrders(String token) {
        return Specification.getRequestSpecification().header("Authorization", token).get("/api/orders");
    }

    @Step("Получение заказов пользователя без авторизации")
    public Response getUserDataWithoutAuth(String value) {
        HashMap<String, String> map = new HashMap<>();
        map.put("value", value);
        return Specification.getRequestSpecification().get(String.format("/api/auth/user?email=%s", value));
    }
}
