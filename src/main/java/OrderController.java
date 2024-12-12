import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderController {

    @Step("Создание заказа")
    public static Response createOrder(String token, JsonObject jsonObject) {
        return Specification.getRequestSpecification().header("Authorization", token).body(jsonObject).post("/api/orders");
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutLogin(JsonObject jsonObject) {
        return Specification.getRequestSpecification().body(jsonObject).post("/api/orders");
    }

    @Step("Получение ингредиентов для заказа")
    public static Response getIngredients() {
        return Specification.getRequestSpecification().get("/api/ingredients");
    }
}
