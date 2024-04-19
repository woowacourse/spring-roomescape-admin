package roomescape.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("테이블이 생성되는지 검증한다.")
    void tableCreationTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertAll(
                    () -> assertThat(connection).isNotNull(),
                    () -> assertThat(connection.getCatalog()).isEqualToIgnoringCase("DATABASE"),
                    () -> assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
