package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeControllerTest {
    @Test
    void 날짜가_형식에_맞지_않는_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-053");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 이름이_빈_값인_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", " ");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 시간이_형식에_맞지_않는_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15 40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void id_값이_문자열_이므로_400을_반환한다() {
        RestAssured.given().log().all()
                .pathParam("id", "일")
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(500);
    }
}
