package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2TimeDaoTest {
    private final TimeDao timeDao;

    @Autowired
    public H2TimeDaoTest(JdbcTemplate jdbcTemplate) {
        this.timeDao = new H2TimeDao(jdbcTemplate);
    }

    @BeforeEach
    void SetUp() {
//        timeDao
    }
}
