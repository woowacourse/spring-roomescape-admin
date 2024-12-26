package roomescape.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationTimeIntegrationTest {

    @Test
    @DisplayName("예약이 존재하지 않는 시간은 삭제할 수 있다.")
    void removeTime() {
        RestAssured
                .when().delete("/times/3")
                .then().statusCode(204);
    }

    @Test
    @DisplayName("예약이 존재하는 시간을 삭제하면 400을 반환한다.")
    void removeTimeFail() {
        RestAssured
                .when().delete("/times/1")
                .then().statusCode(400);
    }
}
