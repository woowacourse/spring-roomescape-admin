package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RoomescapeControllerTest {

    @Autowired
    private RoomescapeController roomescapeController;

    @Test
    void 예약_화면_요청을_성공한다() {

        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        assertThat(roomescapeController).isNotNull();
    }

    @Test
    void 예약_시간_화면_요청을_성공한다() {

        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
