package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 삼단계() {
        Map<String, String> params = Map.of(
                "name", "새양",
                "date", "1998-02-24",
                "time", "10:00"
        );

        RestAssured.given().log().all()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .port(randomServerPort)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 사단계() {
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(conn).isNotNull();
            assertThat(conn.getCatalog()).isEqualTo("DATABASE");
            assertThat(conn.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
