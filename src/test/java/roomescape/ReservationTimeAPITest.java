package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeAPITest {
    @DisplayName("시작 시간으로 예약 시간을 생성한다.")
    @Test
    void 시간_생성_테스트() {
        // given & when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("startAt", "10:00"))
                .when().post("/times")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("생성된 예약 시간 목록을 조회한다.")
    @Test
    void 시간_조회_테스트() {
        // given
        createTime();

        // when
        var response = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("").size()).isEqualTo(1);
    }

    @DisplayName("예약 시간 ID로 예약 시간을 삭제한다.")
    @Test
    void 시간_삭제_테스트() {
        // given
        createTime();

        // when
        var response = RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("시작 시간 없이 예약 시간을 생성하는 경우, 400을 반환한다.")
    @Test
    void 잘못된_요청으로_시간_생성_시_400_반환_테스트() {
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of())
                .when().post("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약과 시간이 올바르게 연결된다.")
    @Test
    void 예약과_시간_연결() {
        RestAssured.given()
                .body(Map.of("startAt", "10:00"))
                .contentType(ContentType.JSON)
                .when().post("/times");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", LocalDate.now().toString());
        reservation.put("timeId", 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    private void createTime() {
        RestAssured.given()
                .body(Map.of("startAt", "10:00"))
                .contentType(ContentType.JSON)
                .when().post("/times");
    }
}
