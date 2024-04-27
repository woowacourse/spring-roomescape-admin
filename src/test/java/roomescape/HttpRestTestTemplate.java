package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.is;

class HttpRestTestTemplate {

    private HttpRestTestTemplate() {
    }

    public static void assertGetOk(String path) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200);
    }

    public static void assertGetOk(String path, String bodyPath, Integer expectedValue) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200)
                .body(bodyPath, is(expectedValue));
    }

    public static void assertPostOk(Object params, String path, String bodyPath, Integer expectedValue) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(path)
                .then().log().all()
                .statusCode(200)
                .body(bodyPath, is(expectedValue));
    }

    public static void assertDeleteOk(String path) {
        RestAssured.given().log().all()
                .when().delete(path)
                .then().log().all()
                .statusCode(200);
    }

    public static void assertDeleteInitialServerError(String path) {
        RestAssured.given().log().all()
                .when().delete(path)
                .then().log().all()
                .statusCode(500);
    }
}
