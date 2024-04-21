package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DatabaseCleanupListener extends AbstractTestExecutionListener {

    private final String sql = """
            SELECT CONCAT('TRUNCATE TABLE ', TABLE_NAME, ' RESTART IDENTITY;') 
            FROM INFORMATION_SCHEMA.TABLES 
            WHERE TABLE_SCHEMA = 'PUBLIC';
            """;

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        JdbcTemplate jdbc = getJdbcTemplateBean(testContext);
        List<String> queries = jdbc.queryForList(sql, String.class);
        execute(queries, jdbc);
    }

    private JdbcTemplate getJdbcTemplateBean(TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private static void execute(List<String> queries, JdbcTemplate jdbc) {
        for (String query : queries) {
            jdbc.execute(query);
        }
    }
}
