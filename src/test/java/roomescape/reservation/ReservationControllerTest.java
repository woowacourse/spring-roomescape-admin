package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @DisplayName("예약을 조회한다.")
    @Test
    void findAll() {
        insertReservationTime(jdbcTemplate);
        insertReservation(jdbcTemplate);
        insertReservation(jdbcTemplate);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void create() {
        insertReservationTime(jdbcTemplate);
        insertReservation(jdbcTemplate);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RESERVATION", Integer.class);
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        insertReservation(jdbcTemplate);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RESERVATION", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("컨트롤러와 DB 관련 로직 클래스의 분리를 확인한다.")
    @Test
    void layeredStructure() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    static ReservationRequest reservationRequest() {
        return new ReservationRequest("브라운", LocalDate.parse("2023-08-05"), 1);
    }

    static void insertReservationTime(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO RESERVATION_TIME (START_AT) VALUES ('10:00')");
    }

    static void insertReservation(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                "INSERT INTO RESERVATION (NAME, DATE, TIME_ID) "
                        + "VALUES (?, ?, SELECT t.id FROM RESERVATION_TIME t WHERE t.id = ?)",
                "브라운", "2023-08-05", 1
        );
    }
}
