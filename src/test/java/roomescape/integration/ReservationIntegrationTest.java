package roomescape.integration;

import static org.hamcrest.Matchers.is;
import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.service.dto.LoginRequest;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationIntegrationTest {

    private String token;

    @BeforeEach
    void init() {
        LoginRequest loginRequest = MemberFixture.loginRequest1();
        token = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().extract().cookie(LOGIN_TOKEN_HEADER_NAME);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void findAllReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("관리자 예약을 추가한다.")
    void createAdminReservation() {
        ReservationAdminRequest adminRequest = ReservationFixture.newAdminRequest();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(adminRequest)
                .cookie(LOGIN_TOKEN_HEADER_NAME, token)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(4));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        ReservationRequest request = ReservationFixture.newRequest();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .cookie(LOGIN_TOKEN_HEADER_NAME, token)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(4));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    @DisplayName("과거 시간대 예약을 시도하면 400을 반환한다.")
    void createReservationFail() {
        ReservationRequest request = ReservationFixture.badRequest();
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", "브라운");
//        params.put("date", LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE));
//        params.put("timeId", 1L);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .cookie(LOGIN_TOKEN_HEADER_NAME, token)
                .when().post("/reservations")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelReservation() {
        RestAssured.given()
                .cookie(LOGIN_TOKEN_HEADER_NAME, token)
                .when().delete("/reservations/1")
                .then().statusCode(204);

        RestAssured
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }
}
