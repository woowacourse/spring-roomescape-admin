package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservations() {
        assertGetRequestStatusCodeOKAndSize("/reservations", 0);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        final Map<String, String> params = createReservations();

        assertPostRequestStatusCodeOKAndId(params, "/reservations", 1);

        assertGetRequestStatusCodeOKAndSize("/reservations", 1);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        final Map<String, String> params = createReservations();
        assertPostRequestStatusCodeOKAndId(params, "/reservations", 1);

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(200);

        assertGetRequestStatusCodeOKAndSize("/reservations", 0);
    }

    private Map<String, String> createReservations() {
        final Map<String, String> reservations = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );
        return reservations;
    }

    private void assertGetRequestStatusCodeOKAndSize(final String path, final int size) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(size));
    }

    private void assertPostRequestStatusCodeOKAndId(final Map<String, String> params, final String path, final int id) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(path)
                .then().log().all()
                .statusCode(200)
                .body("id", is(id));
    }
}
