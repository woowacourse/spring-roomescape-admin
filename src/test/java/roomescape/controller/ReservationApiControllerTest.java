package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiControllerTest {

    @Autowired
    ReservationApiController reservationController;

    @Autowired
    ReservationTimeApiController timeController;

    @BeforeEach
    void setUp() {
        timeController.addReservationTime(new ReservationTimeRequest("10:00"));
    }

    @DisplayName("예약 정보를 조회한다.")
    @Test
    void findAllReservations() {
        timeController.addReservationTime(new ReservationTimeRequest("11:00"));
        reservationController.addReservation(new ReservationRequest("상돌", "2024-04-25", 1L));
        reservationController.addReservation(new ReservationRequest("상돌1", "2024-04-24", 2L));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].name", is("상돌"))
                .body("[1].id", is(2))
                .body("[1].name", is("상돌1"));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addReservation() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "상돌");
        params.put("date", "2024-04-21");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("상돌"))
                .body("date", is("2024-04-21"))
                .body("time.id", is(1))
                .body("time.startAt", is("10:00"));
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteReservation() {
        reservationController.addReservation(new ReservationRequest("상돌", "2024-04-25", 1L));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }
}
