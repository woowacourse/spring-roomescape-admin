package roomescape.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class H2DatabaseTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("커넥션을 얻어올 수 있다")
    @Test
    void connectionTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("데이터 베이스의 논리적 구분 이름은 'database'이다")
    @Test
    void catalogTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("데이터 베이스에 reservation 테이블이 존재한다")
    @Test
    void tablesTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
