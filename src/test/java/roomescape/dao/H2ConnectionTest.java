package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class H2ConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("DataSource를 정상적으로 가져온다.")
    @Test
    void checkDataSource() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("데이터베이스 이름이 'DATABASE' 인지 확인한다.")
    @Test
    void checkCatalogName() {
        String databaseName = "DATABASE";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo(databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("테이블 이름이 'RESERVATION' 인지 확인한다.")
    @Test
    void check() {
        String tableName = "RESERVATION";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getMetaData().getTables(
                    null, null, tableName, null
            ).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
