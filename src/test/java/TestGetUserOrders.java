import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetUserOrders {

    AuthController authController = new AuthController();
    UserController userController = new UserController();

    private static JsonObject getListIngredients() {
        List<String> ingredients = OrderController.getIngredients()
                .then()
                .extract()
                .jsonPath().getList("data._id", String.class);

        JsonArray jsonArray = new JsonArray();
        for (String ingredient : ingredients) {
            jsonArray.add(ingredient);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("ingredients", jsonArray);
        return jsonObject;
    }

    @Test
    @Description("Создание заказов с авторизацией")
    public void testCreateOrder() {
        String token = userController.postUser(Constants.RANDON_EMAIL, Constants.PASSWORD, Constants.NAME)
                .then()
                .extract().body().path("accessToken");
        OrderController.createOrder(token, getListIngredients());
        userController.getUserOrders(token)
                .then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(Constants.RANDON_EMAIL, Constants.PASSWORD);
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
        }
    }
}
