package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.common.SpringBootTestBase;

class PageTest extends SpringBootTestBase {

    @Test
    void 어드민_페이지를_조회시_200을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 어드민_예약페이지를_조회시_200을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 어드민_시간추가페이지를_조회시_200을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }
}
