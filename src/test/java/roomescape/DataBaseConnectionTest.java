package roomescape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DataBaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("데이터베이스 연결")
    @Test
    void dataBaseConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            Assertions.assertAll(
                    () -> assertThat(connection).isNotNull(),
                    () -> assertThat(connection.getCatalog()).isEqualTo("DATABASE"),
                    () -> assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue(),
                    () -> assertThat(connection.getMetaData().getTables(null, null, "RESERVATION_TIME", null).next()).isTrue()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
