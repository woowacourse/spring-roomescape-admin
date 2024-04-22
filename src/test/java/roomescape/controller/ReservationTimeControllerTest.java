package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("저장된 모든 시간을 조회한다.")
    void findAllTimes() {
        jdbcTemplate.update(
                "INSERT INTO reservation_time (start_at) VALUES (?)", "10:00"
        );

        List<ReservationTime> reservationTimes = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) from reservation_time", Integer.class);
        assertThat(reservationTimes.size()).isEqualTo(count);
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

                    Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) from reservation_time", Integer.class);
                    assertThat(count).isEqualTo(1);
                }),
                dynamicTest("시간을 삭제한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(204);

                    Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(1) from reservation_time", Integer.class);
                    assertThat(countAfterDelete).isEqualTo(0);
                })
        );
    }
}
