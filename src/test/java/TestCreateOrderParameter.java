import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class TestCreateOrderParameter {

    AuthController authController = new AuthController();
    UserController userController = new UserController();

    Integer statusCode;

    JsonObject jsonObject;

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

    private static JsonObject getEmptyListIngredients() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("ingredients", new JsonArray());
        return jsonObject;
    }

    private static JsonObject getIncorrectListIngredients() {
        List<String> ingredients = OrderController.getIngredients()
                .then()
                .extract()
                .jsonPath().getList("data._id", String.class);

        JsonArray jsonArray = new JsonArray();
        for (String ingredient : ingredients) {
            jsonArray.add(ingredient);
        }
        jsonArray.add("kfg3");


        JsonObject jsonObject = new JsonObject();
        jsonObject.add("ingredients", jsonArray);
        return jsonObject;
    }

    public TestCreateOrderParameter(JsonObject jsonObject, Integer statusCode) {
        this.jsonObject = jsonObject;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {getListIngredients(), 200},
                {getEmptyListIngredients(), 400},
                {getIncorrectListIngredients(), 500},
        };
    }

    @Test
    @Description("Создание заказов с авторизацией")
    public void testCreateOrder() {
        String token = userController.postUser(Constants.RANDON_EMAIL, Constants.PASSWORD, Constants.NAME)
                .then()
                .extract().body().path("accessToken");
        OrderController.createOrder(token, jsonObject)
                .then()
                .statusCode(statusCode);
    }

    @Test
    @Description("Создание заказов без авторизации")
    public void testCreateOrderWithoutLogin() {
        OrderController.createOrderWithoutLogin(jsonObject)
                .then()
                .statusCode(statusCode);
    }

    @After
    public void tearDown() {
        String token = authController.getUserToken(Constants.RANDON_EMAIL, Constants.PASSWORD);
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
        }
    }
}
