import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specification {

    private static final String baseURI = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getRequestSpecification() {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(baseURI)
                .when();
    }
}
