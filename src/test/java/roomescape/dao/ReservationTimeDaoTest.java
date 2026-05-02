package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private JdbcTemplate jdbcTemplate;
    private JdbcReservationTimeDao reservationTimeDao;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        DataSource dataSource = generateDataSource();

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("schema.sql")
        );
    }

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = generateDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        reservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("test-clear-schema.sql")
        );
    }

    @Test
    void 예약시간_삽입시_생성되는_ID가_양수이다() {
        // when
        ReservationTime actual = reservationTimeDao.insert(reservationTime);

        // then
        Assertions.assertThat(actual.getId())
                .isNotNull()
                .isPositive();
    }

    @Test
    void 삽입하면_반환된_시작_시간이_입력값과_일치한다() {
        // when
        ReservationTime actual = reservationTimeDao.insert(reservationTime);

        // then
        Assertions.assertThat(actual.getStartAt())
                .isEqualTo(time);
    }

    @Test
    void 두_번_삽입해도_서로_다른_ID가_부여된다() {
        // when
        ReservationTime first  = reservationTimeDao.insert(reservationTime);
        ReservationTime second = reservationTimeDao.insert(reservationTime);

        // then
        Assertions.assertThat(first.getId())
                .isNotEqualTo(second.getId());
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
