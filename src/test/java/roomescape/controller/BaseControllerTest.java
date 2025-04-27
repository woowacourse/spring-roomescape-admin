package roomescape.controller;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseControllerTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncateTables() {
        jdbcTemplate.update("""
            SET REFERENTIAL_INTEGRITY FALSE;
            TRUNCATE TABLE reservation;
            ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;
            TRUNCATE TABLE reservation_time;
            ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1;
            SET REFERENTIAL_INTEGRITY TRUE;
        """);
    }


}
