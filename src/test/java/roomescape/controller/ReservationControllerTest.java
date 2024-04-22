package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("데이터 베이스에 'reservation' 테이블이 생성된다.")
    void createReservationTable() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData()
                    .getTables(null, null, "RESERVATION", null).next())
                    .isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("저장된 모든 reservation을 조회한다.")
    void findAllReservationsFromDatabase() {
        jdbcTemplate.update(
                "INSERT INTO reservation_time (start_at) VALUES (?)", "15:40");
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @TestFactory
    @DisplayName("예약을 생성하고 삭제한다.")
    Collection<DynamicTest> saveReservationAndDelete() {
        jdbcTemplate.update(
                "INSERT INTO reservation_time (start_at) VALUES (?)", "15:40");
        ReservationRequest params = new ReservationRequest(
                "브라운", LocalDate.of(2023, 8, 5), 1L);

        return List.of(
                dynamicTest("예약을 생성한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(201)
                            .header("Location", "/reservations/1");

                    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
                    assertThat(count).isEqualTo(1);
                }),
                dynamicTest("예약을 삭제한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/reservations/1")
                            .then().log().all()
                            .statusCode(204);

                    Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
                            Integer.class);
                    assertThat(countAfterDelete).isEqualTo(0);
                })
        );
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하려고 시도하면 404 status를 반환한다.")
    void deleteReservationByNotExistId() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }

    @Nested
    @DisplayName("클래스 분리 테스트")
    class LayeredArchitectureTest {
        @Autowired
        private ReservationController reservationController;

        @Test
        @DisplayName("데이터베이스 관련 로직이 분리되어 있다.")
        void databaseLogicSeparated() {
            boolean isJdbcTemplateInjected = false;

            for (Field field : reservationController.getClass().getDeclaredFields()) {
                if (field.getType().equals(JdbcTemplate.class)) {
                    isJdbcTemplateInjected = true;
                    break;
                }
            }

            assertThat(isJdbcTemplateInjected).isFalse();
        }
    }
}
