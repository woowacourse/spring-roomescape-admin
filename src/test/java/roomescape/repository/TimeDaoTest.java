package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("db 연결 확인")
    @Test
    void checkConnection() {
        try (final Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE_TEST");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION_TIME", null).next()).isTrue();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
