package roomescape.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;

public class ReservationTimeDaoTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcReservationTimeDao reservationTimeDao;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        DriverManagerDataSource dataSource = getDriverManagerDataSource();

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("schema.sql")
        );
    }

    @BeforeEach
    void setUp() throws SQLException {
        DriverManagerDataSource dataSource = getDriverManagerDataSource();

        jdbcTemplate = new JdbcTemplate(dataSource);
        reservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("test-clear-schema.sql")
        );
    }

    private static DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

}
