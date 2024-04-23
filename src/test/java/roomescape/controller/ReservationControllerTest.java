package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.dto.ReservationCreateDto;

/*
 * 테스트 데이터베이스 초기 데이터
 * {ID=1, NAME=브라운, DATE=2024-05-04, TIME={ID=1, START_AT="10:00"}}
 * {ID=2, NAME=엘라, DATE=2024-05-04, TIME={ID=2, START_AT="11:00"}}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME={ID=2, START_AT="11:00"}}
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/reset_test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("H2 데이터베이스에 연결한다.")
    void connectH2Database() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("전체 예약 목록을 조회한다.")
    void readReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        ReservationCreateDto createDto = new ReservationCreateDto("브라운", "2023-08-05", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(4));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("컨트롤러에는 데이터베이스 관련 로직이 존재하지 않는다.")
    void isJdbcTemplateInjected() {
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
