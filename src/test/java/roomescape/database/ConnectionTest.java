package roomescape.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/test-schema.sql")
class ConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            assertThat(
                connection.getMetaData()
                    .getTables(null, null, "RESERVATION_TIME", null)
                    .next()
            ).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
