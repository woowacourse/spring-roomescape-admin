package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.User;

public class ReservationTest {
    @DisplayName("예약자 이름, 날짜, 시간으로 예약을 생성한다.")
    @Test
    void 예약_생성_테스트() {
        // given
        User user = new User("brown");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Reservation reservation = new Reservation(user, date, time);

        // when
        var response = RestAssured
                .given().log().all()
                .body(reservation)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("생성된 예약 정보를 조회한다.")
    @Test
    void 예약_조회_테스트() {
        // given
        예약_생성_테스트();

        // when
        var response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", Reservation.class)).hasSize(1);
    }

    @DisplayName("예약 ID로 예약을 삭제한다.")
    @Test
    void 예약_삭제_테스트() {
        // given
        예약_생성_테스트();

        // when
        var response = RestAssured
                .given().log().all()
                .when().delete("/reservations/1")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
