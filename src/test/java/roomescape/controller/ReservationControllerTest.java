package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationDao reservationDao;

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

    @DisplayName("예약 추가 테스트")
    @Test
    void createReservation() {
        //when
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations")
                .then().log().all().extract().response();

        //then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(reservationDao.findAll()).hasSize(1)
        );
    }

    @DisplayName("모든 예약 내역 조회 테스트")
    @Test
    void findAllReservations() {
        //given
        RestAssured.given().contentType(ContentType.JSON).body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations");

        //when
        Response response = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all().extract().response();

        //then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("", ReservationResponse.class)).hasSize(1)
        );
    }

    @DisplayName("예약 취소 성공 테스트")
    @Test
    void deleteReservationSuccess() {
        //given
        var id = RestAssured.given().contentType(ContentType.JSON).body(new ReservationRequest("브라운", date, time))
                .when().post("/reservations")
                .then().extract().body().jsonPath().get("id");

        //when
        Response deleteResponse = RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all().extract().response();

        //then
        assertAll(
                () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(reservationDao.findAll()).hasSize(0)
        );
    }

    @DisplayName("예약 취소 실패 테스트")
    @Test
    void deleteReservationFail() {
        //given
        long invalidId = 0;

        //when
        Response response = RestAssured.given().log().all()
                .when().delete("/reservations/" + invalidId)
                .then().log().all().extract().response();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
