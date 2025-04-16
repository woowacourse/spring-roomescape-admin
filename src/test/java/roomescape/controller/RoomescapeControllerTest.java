package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeControllerTest {

    @DisplayName("같은 날짜 및 시간 예약이 존재하면 404 Not Found를 던진다")
    @Test
    void reservationAdd() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Map<String, String> duplicated = new HashMap<>();
        duplicated.put("name", "네오");
        duplicated.put("date", "2023-08-05");
        duplicated.put("time", "15:40");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(duplicated)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }
}
