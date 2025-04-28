package roomescape.common;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.config.JdbcTemplateInitializer;

public abstract class DbTest {

    protected final JdbcTemplate jdbcTemplate = JdbcTemplateInitializer.getJdbcTemplate();

    @BeforeEach
    void truncateAll() {
        Constant.truncateAll(jdbcTemplate);
    }
}
