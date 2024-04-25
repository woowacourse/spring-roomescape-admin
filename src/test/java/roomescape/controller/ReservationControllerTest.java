package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.CreateReservationRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    CreateReservationRequest request;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        request = new CreateReservationRequest("브라운", LocalDate.of(2023, 8, 5), 1);
    }

    @Test
    void 예약_목록을_요청하면_200_코드와_데이터를_반환한다() {
        validateResponseBodyDataSize("/reservations", 200, 0);
    }

    @Test
    void 예약추가_및_조회를_수행한다() {
        jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        validateResponseBodyDataSize("/reservations", 200, 1);
    }

    @Test
    void 예약삭제_및_조회를_수행한다() {
        int updated = jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/times/" + updated)
                .then().log().all()
                .statusCode(204);

        validateResponseBodyDataSize("/reservations", 200, 0);
    }

    @Test
    void 서비스_및_데이터베이스_접근_객체_분리_테스트() {
        boolean isJdbcTemplateInjected = false;

        for (var field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    private void validateResponseBodyDataSize(String path, int expectedStatusCode, int size) {
        validatePageStatus(path, expectedStatusCode)
                .body("size()", is(size));
    }

    private ValidatableResponse validatePageStatus(String path, int expectedStatusCode) {
        return RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }
}
