package roomescape;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationReservationTimeFlowTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @TestFactory
    @DisplayName("시간을 생성하고 삭제한다")
    Collection<DynamicTest> saveTimeAndDelete() {
        ReservationTimeRequest params = new ReservationTimeRequest(LocalTime.of(10, 0));

        return List.of(
                dynamicTest("시간을 생성한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(201)
                            .header("Location", "/times/1");
                }),
                dynamicTest("저장된 모든 시간을 조회한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/times")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),
                dynamicTest("시간을 삭제한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(204);
                }),
                dynamicTest("존재하지 않는 시간을 삭제하려고 시도하면 404 status를 반환한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(404);
                })
        );
    }
}
