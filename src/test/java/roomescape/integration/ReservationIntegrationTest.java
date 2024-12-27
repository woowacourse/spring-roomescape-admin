package roomescape.integration;

import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.fixture.ReservationFixture;
import roomescape.service.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("관리자 페이지를 응답한다.")
    void adminPage() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("관리자 예약 페이지를 응답한다.")
    void adminReservationPage() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("관리자 시간 관리 페이지를 응답한다.")
    void adminTimePage() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
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
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        ReservationRequest request = ReservationFixture.newRequest();
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", "브라운");
//        params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE));
//        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
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
                .when().post("/reservations")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelReservation() {
        RestAssured
                .when().delete("/reservations/1")
                .then().statusCode(204);

        RestAssured
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }
}
