package roomescape.common;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class Constant {

    public static Clock FIXED_CLOCK = Clock.fixed(LocalDateTime.of(2025, 1, 1, 12, 0)
            .atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    public static void truncateAll(JdbcTemplate jdbcTemplate) {
        String sql = """
                select TABLE_NAME
                from INFORMATION_SCHEMA.TABLES
                where TABLE_SCHEMA = 'PUBLIC';
                """;
        List<String> tableNames = jdbcTemplate.query(sql, (resultSet, rowNum) -> resultSet.getString("TABLE_NAME"));
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE;");
        tableNames.forEach(tableName -> {
            jdbcTemplate.update(String.format("TRUNCATE TABLE %s RESTART IDENTITY;", tableName));
        });
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE;");

    }
}
