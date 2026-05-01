package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.domain.reservation.dto.ReservationResponseDTO;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {
    @DisplayName("예약자 이름, 날짜, 시간으로 예약을 생성한다.")
    @Test
    void 예약_생성_테스트() {
        // given
        RestAssured.given()
                .body(Map.of("startAt", "10:00"))
                .contentType(ContentType.JSON)
                .when().post("/times");

        var body = Map.of(
                "name", "brown",
                "date", LocalDate.now().toString(),
                "timeId", 1L
        );

        // when
        var response = RestAssured
                .given().log().all()
                .body(body)
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
                .when().get("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ReservationResponseDTO.class))
                .hasSize(1)
                .extracting(ReservationResponseDTO::getName)
                .containsExactly("brown");
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
