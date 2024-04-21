package roomescape.acceptance;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static roomescape.fixture.ClockFixture.fixedClock;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationAcceptanceTest {
    private static final String PATH = "/reservations";

    @SpyBean
    private Clock clock;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Order(1)
    @Test
    void 이단계_예약_목록을_응답한다() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Order(2)
    @Test
    void 삼단계_예약을_등록한다() {
        when(clock.instant()).thenReturn(fixedClock(LocalDate.of(2023, 8, 4)).instant());
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(PATH)
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }

    @Order(3)
    @Test
    void 삼단계_등록된_예약을_조회한다() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Order(4)
    @Test
    void 삼단계_등록된_예약을_삭제한다() {
        RestAssured.given().log().all()
                .when().delete(PATH + "/1")
                .then().log().all()
                .statusCode(204);
    }

    @Order(5)
    @Test
    void 삼단계_삭제된_예약을_조회한다() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
