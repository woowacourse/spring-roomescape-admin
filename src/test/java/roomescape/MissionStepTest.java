package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void moveToAdminPage() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 페이지 응답")
    @Test
    void moveToReservationPage() {
        List<Reservation> reservations = reservationRepository.getReservations();
        int reservationSize = reservations.size();

        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(reservationSize));
    }

    @DisplayName("예약 추가")
    @Test
    void addReservation() {
        List<Reservation> reservations = reservationRepository.getReservations();
        int reservationSize = reservations.size();
        int lastIndex = Math.toIntExact(reservations.get(reservationSize - 1)
                .getId());

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(lastIndex + 1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(reservationSize + 1));

//        RestAssured.given().log().all()
//                .when().delete("/reservations/" + (lastIndex + 1))
//                .then().log().all()
//                .statusCode(200);
//
//        RestAssured.given().log().all()
//                .when().get("/reservations")
//                .then().log().all()
//                .statusCode(200)
//                .body("size()", is(reservationSize));
    }
}
