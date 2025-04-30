package roomescape.controller.reservationtime;

import static org.hamcrest.Matchers.is;

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
class ReservationTimeControllerTest {


    @DisplayName("예약시간을 추가할 수 있다.")
    @Test
    void saveReservationTime() {
        //given
        Map<String, String> reservationTime = createReservationTimeFixture();

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, String> createReservationTimeFixture() {
        Map<String, String> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");
        return reservationTime;
    }

    @DisplayName("예약시간을 조회할 수 있다.")
    @Test
    void readAllReservationTime() {
        //given
        saveReservationTime();

        //when //then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약시간 번호에 따른 예약시간을 삭제할 수 있다.")
    @Test
    void deleteReservationTime() {
        //given
        saveReservationTime();

        //when //then
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

}
