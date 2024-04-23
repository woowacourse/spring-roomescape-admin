package roomescape.support.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TableTruncateExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) {
        JdbcTemplate jdbcTemplate = SpringExtension.getApplicationContext(context).getBean(JdbcTemplate.class);

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.queryForList("SHOW TABLES").stream()
                .map(table -> table.get("TABLE_NAME").toString())
                .forEach(tableName -> jdbcTemplate.execute("TRUNCATE TABLE " + tableName + " RESTART IDENTITY"));
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
