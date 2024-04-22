package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void moveToAdminPage() {
        HttpRestTestTemplate.assertGetOk("/admin");
    }

    @DisplayName("예약 페이지 응답")
    @Test
    void moveToReservationPage() {
        HttpRestTestTemplate.assertGetOk("/admin/reservation");
        HttpRestTestTemplate.assertGetOk("/reservations", "size()", 0);
    }

    @DisplayName("Database 연결")
    @Test
    void connectDatabase() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(
                connection.getMetaData()
                    .getTables(null, null, "RESERVATION", null)
                    .next()
            ).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("전체 예약 조회")
    @Test
    void findAllReservations() {
        String insertSql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(insertSql, "브라운", "2023-08-05", "15:40");

        List<ReservationResponse> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", ReservationResponse.class);

        String selectSql = "select count(1) from reservation";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class);

        assertThat(reservations).hasSize(count);
    }

    @DisplayName("예약 추가 후 삭제")
    @Test
    void insertAndRemoveReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "10:00");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
            Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
            Integer.class);
        assertThat(countAfterDelete).isZero();
    }
}
