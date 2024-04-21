package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DatabaseCleanupListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        JdbcTemplate jdbc = getJdbcTemplateBean(testContext);
        jdbc.execute("TRUNCATE TABLE reservation RESTART IDENTITY");
    }

    private JdbcTemplate getJdbcTemplateBean(TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }
}
