package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    void 이름이_없으면_400_응답을_반환한다() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", LocalDate.now().plusDays(20));
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 이름이_비어있으면_400_응답을_반환한다() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "");
        params.put("date", LocalDate.now().plusDays(20));
        params.put("time_id", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 날짜가_없으면_400_응답을_반환한다() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "dompoo");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 시간이_없으면_400_응답을_반환한다() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "dompoo");
        params.put("date", LocalDate.now().plusDays(20));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
