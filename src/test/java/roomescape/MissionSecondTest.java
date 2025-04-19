package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest(
    properties = {
        "spring.datasource.generate-unique-name=false",
        "spring.datasource.url=jdbc:h2:mem:database",
        "spring.datasource.name=database"
    }
)
public class MissionSecondTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 사단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertAll(
                () -> assertThat(connection).isNotNull(),
//                () -> assertThat(connection.getCatalog()).isEqualTo("DATABASE"),
                () -> assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null)
                    .next()).isTrue()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
