package roomescape;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
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
    void 일단계() {
        given()
                .when().get("/")
                .then()
                .statusCode(200);
    }

    @Test
    void 이단계() {
        given()
                .when().get("/admin/reservation")
                .then()
                .statusCode(200);

        given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 삼단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

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
