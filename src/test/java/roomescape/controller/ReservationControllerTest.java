package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.dto.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    private String date;
    private String time;

    @BeforeEach
    void initPort() {
        RestAssured.port = port;
        date = LocalDate.now().plusDays(1).toString();
        time = LocalTime.now().toString();
    }

    @AfterEach
    void initData() {
        RestAssured.get("/reservations")
                .then().extract().body().jsonPath().getList("id")
                .forEach(id -> RestAssured.delete("/reservations/" + id));
    }

    @DisplayName("예약 추가 성공 테스트")
    @Test
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations")
                .then().log().all()
                .assertThat().statusCode(200).body("id", is(greaterThan(0)));
    }

    @DisplayName("예약 추가 실패 테스트 - 이름 길이 오류")
    @Test
    void createInvalidNameReservation() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("", date, time))
                .when().post("/reservations")
                .then().log().all()
                .assertThat().statusCode(400).body(is("이름은 1자 이상, 5자 이하여야 합니다."));
    }

    @DisplayName("예약 추가 실패 테스트 - 일정 길이 오류")
    @Test
    void createInvalidScheduleReservation() {
        //given
        String invalidDate = "2023-10-04";

        //when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("lini", invalidDate, time))
                .when().post("/reservations")
                .then().log().all()
                .assertThat().statusCode(400).body(is("현재보다 이전으로 일정을 설정할 수 없습니다."));
    }

    @DisplayName("모든 예약 내역 조회 테스트")
    @Test
    void findAllReservations() {
        //given
        RestAssured.given().contentType(ContentType.JSON).body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations");

        //when & then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .assertThat().statusCode(200).body("size()", is(1));
    }

    @DisplayName("예약 취소 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        //given
        var id = RestAssured.given().contentType(ContentType.JSON).body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations")
                .then().extract().body().jsonPath().get("id");

        //when
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all()
                .assertThat().statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .assertThat().body("size()", is(0));
    }

    @DisplayName("예약 취소 실패 테스트")
    @Test
    void deleteReservationFail() {
        //given
        long invalidId = 0;

        //when
        RestAssured.given().log().all()
                .when().delete("/reservations/" + invalidId)
                .then().log().all()
                .assertThat().statusCode(404);
    }
}
