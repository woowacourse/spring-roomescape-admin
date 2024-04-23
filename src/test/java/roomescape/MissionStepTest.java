package roomescape;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static roomescape.ReservationTestSetting.createReservationDto;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MissionStepTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void 메인_페이지_이동() {
        given().log().all()
                .port(port)
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_페이지_이동() {
        given().log().all()
                .port(port)
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_페이지에서_예약_목록_조회() {
        given().log().all()
                .port(port)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 예약_페이지에서_예약_추가_및_삭제() {
        ReservationDto reservationRequest = createReservationDto();

        Response response = given().log().all()
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Integer savedId = response.path("id");

        given().log().all()
                .port(port)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        given().log().all()
                .port(port)
                .when().delete("/reservations/" + savedId)
                .then().log().all()
                .statusCode(200);

        given().log().all()
                .port(port)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 데이터베이스_테이블_생성() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
