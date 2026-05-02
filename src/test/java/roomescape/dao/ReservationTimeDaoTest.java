package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalTime;

class ReservationTimeDaoTest {

    private static final int hour = 11;
    private static final int minute = 4;
    private static final LocalTime time = LocalTime.of(hour, minute);
    private static final ReservationTime reservationTime = new ReservationTime(time);

    private JdbcReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = generateDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        reservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("schema.sql")
        );
    }

    private static DataSource generateDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

}
