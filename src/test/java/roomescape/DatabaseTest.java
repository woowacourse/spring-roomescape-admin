package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DatabaseTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void test_connection() {
        try (Connection connection = jdbcTemplate.getDataSource()
                                                 .getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("ROOMESCAPE");
            assertThat(connection.getMetaData()
                                 .getTables(null, null, "RESERVATION", null)
                                 .next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
