package roomescape.reservation.acceptance;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.util.AcceptanceTest;

@DisplayName("예약 시간 인수 테스트")
class ReservationTimeAcceptanceTest extends AcceptanceTest {
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @DisplayName("시간 생성 시, 200을 반환한다.")
    @Test
    void create() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("시간 조회 시, 200을 반환한다.")
    @Test
    void findAll() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        // when & then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("시간 삭제 시, 200을 반환한다.")
    @Test
    void delete() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        //when & then
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
