package roomescape.admin;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminTest {
    @Test
    void admin() {
        RestAssured.given().log().all()
                   .when().get("/admin")
                   .then().log().all()
                   .statusCode(200);
    }

    @Test
    void reservation(){
        RestAssured.given().log().all()
                   .when().get("/admin/reservation")
                   .then().log().all()
                   .statusCode(200);
    }

    @Test
    void time(){
        RestAssured.given().log().all()
                   .when().get("/admin/time")
                   .then().log().all()
                   .statusCode(200);
    }
}
