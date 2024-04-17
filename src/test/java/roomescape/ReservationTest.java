package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTest {

    private ReservationDto reservationDto;

    @BeforeEach
    void setUp() {
        String name = "브라운";
        String date = "2023-08-05";
        String time = "15:40";
        reservationDto = ReservationDto.of(null, name, date, time);
    }

    @Test
    @DisplayName("전체 예약을 조회한다.")
    void getAllReservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 성공적으로 추가한다.")
    void addReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 성공적으로 삭제한다.")
    void deleteReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 삭제시 경로 변수가 null이면 예외가 발생한다.")
    void deleteNullId() {
        Long id = null;
        ReservationController reservationController = new ReservationController();

        assertThatThrownBy(() -> reservationController.delete(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약을 삭제시 id에 해당하는 예약이 없다면 예외가 발생한다.")
    void deleteNotContainsId() {
        Long id = 3L;
        ReservationController reservationController = new ReservationController();

        assertThatThrownBy(() -> reservationController.delete(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
