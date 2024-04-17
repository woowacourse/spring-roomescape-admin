package roomescape;

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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationDao reservationDao;

    @BeforeEach
    void initPort() {
        RestAssured.port = port;
    }

    @AfterEach
    void initData() {
        for (ReservationEntity reservationEntity : reservationDao.findAll()) {
            reservationDao.deleteById(reservationEntity.getId());
        }
    }

    @DisplayName("예약 추가 테스트")
    @Test
    void createReservation() {
        //when
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationRequest("브라운", "2023-08-05", "15:40"))
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
        Reservation reservation = new Reservation("브라운", "2023-08-05", "15:40");
        reservationDao.save(reservation);

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
        Reservation reservation = new Reservation("브라운", "2023-08-05", "15:40");
        long id = reservationDao.save(reservation).getId();

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
