package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JdbcConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("JDBC 연결 테스트")
    void jdbcConnectionTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
