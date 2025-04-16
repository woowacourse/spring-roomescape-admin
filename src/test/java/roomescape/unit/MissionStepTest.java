package roomescape.unit;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import roomescape.controller.AdminController;
import roomescape.controller.ReservationController;

@WebMvcTest(controllers = {ReservationController.class, AdminController.class})
public class MissionStepTest {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test
    void 일단계_메인페이지에_접근가능하다() {
        given()
                .when().get("/")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 이단계_예약_어드민페이지에_접근가능하다() {
        given()
                .when().get("/admin/reservation")
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .when().get("/reservations")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Nested
    class 삼단계 {

        static Map<String, String> params;

        static {
            params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("time", "15:40");
        }

        @Test
        void 삼단계_예약을_할_수_있다() {
            given()
                    .contentType("application/json")
                    .body(params)
                    .when().post("/reservations")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            given()
                    .when().get("/reservations")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", is(1));
        }

        @Test
        void 삼단계_예약을_삭제할_수_있다() {
            given()
                    .contentType("application/json")
                    .body(params)
                    .when().post("/reservations")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            given()
                    .when().delete("/reservations/1")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            given()
                    .when().get("/reservations")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", is(0));
        }
    }
}
