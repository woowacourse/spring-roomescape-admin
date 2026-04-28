package roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @ParameterizedTest(name = "{0}은 1에서 10자 이내의 예약자 이름이 아니다")
    @ValueSource(strings = {"", "12345678901"})
    void 예약을_추가할_때_이름이_1자에서_10자이내가_아니면_오류를_일으킨다(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

}
