package roomescape.controller;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("1", "10:00"))
                .when()
                .post("/times");
    }

    @DisplayName("예약 정보가 존재하지 않으면 예약을 생성할 수 없다.")
    @MethodSource
    @ParameterizedTest
    void addReservationWithoutReservationInformation(Map<String, String> params) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404)
                .body("requestUrl", containsString("/reservations"));
    }

    private static Stream<Arguments> addReservationWithoutReservationInformation() {
        return Stream.of(
                Arguments.of(Map.of("name", "", "date", "2025-04-20", "timeId", "1")),
                Arguments.of(Map.of("name", "포스티", "date", "", "timeId", "1")),
                Arguments.of(Map.of("name", "포스티", "date", "2025-04-20", "timeId", ""))
        );
    }

    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    @Test
    void deleteReservationWithNonExistsId() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404)
                .body("requestUrl", containsString("/reservations/1"));
    }

    @DisplayName("잘못된 예약번호를 입력하면 삭제할 수 없다.")
    @ValueSource(strings = {"일", "one"})
    @ParameterizedTest
    void deleteReservationWithInvalidId(String id) {
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(400)
                .body("requestUrl", containsString("/reservations"));
    }
}
