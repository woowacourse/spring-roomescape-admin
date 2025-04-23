package roomescape.reservation.dao;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.dto.ReservationReqDTO;
import roomescape.reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("새 예약 데이터를 추가하면 해당 데이터에 아이디를 부여한다.")
    void insertTest() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservationInfo = getNewReservationInfo();

        // when
        Reservation newReservation = reservationDAO.insert(reservationInfo);

        // then
        assertThat(newReservation.getId()).isNotNull();
    }

    @Test
    @DisplayName("새 데이터 추가 시 생성된 id로 예약 데이터를 삭제할 수 있다")
    void deleteByTest() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservationInfo = getNewReservationInfo();

        // when
        Reservation newReservation = reservationDAO.insert(reservationInfo);
        Long reservationId = newReservation.getId();

        // then
        assertDoesNotThrow(() -> reservationDAO.deleteBy(reservationId));
    }

    @Test
    @DisplayName("새 데이터 추가 시 생성된 id로 예약 데이터를 조회할 수 있다")
    void selectByTest() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservationInfo = getNewReservationInfo();

        // when
        Reservation newReservation = reservationDAO.insert(reservationInfo);
        Long reservationId = newReservation.getId();

        // then
        assertDoesNotThrow(() -> reservationDAO.selectBy(reservationId));
    }

    @Test
    @DisplayName("존재하지 않는 id의 데이터를 삭제하려고 하면 예외가 발생한다")
    void deleteByExceptionTest() {
        // given
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        Reservation reservationInfo = getNewReservationInfo();

        // when
        reservationDAO.insert(reservationInfo);
        Long invalidId = 20L;

        // then
        assertThatThrownBy(() -> reservationDAO.deleteBy(invalidId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("예약 조회 API를 통해 조회한 예약 수와 DB 쿼리를 통해 조회한 예약 수가 같은지 비교한다.")
    void checkReservationCount() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        // when
        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        // then
        Integer count = countReservations();
        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("예약을 삭제하면 DB에서 실제로 제거된다.")
    void verifyReservationIsDeletedInDB() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = countReservations();
        assertThat(count).isEqualTo(1);

        // when
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        // then
        Integer countAfterDelete = countReservations();
        assertThat(countAfterDelete).isEqualTo(0);
    }

    private Integer countReservations() {
        return jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    }

    private Reservation getNewReservationInfo() {
        ReservationReqDTO reservationReqDto = new ReservationReqDTO("포비", LocalDate.now(), LocalTime.now());
        return reservationReqDto.toEntity();
    }
}
