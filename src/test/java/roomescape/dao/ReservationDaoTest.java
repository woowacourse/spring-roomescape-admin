package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationDaoTest {

    private static final int hour = 10;
    private static final int minute = 0;
    private static final LocalTime time = LocalTime.of(hour, minute);
    private static final ReservationTime reservationTime = new ReservationTime(time);
    private static final String name = "송송";
    private static final LocalDate date = LocalDate.of(2026, 5, 3);

    private JdbcTemplate jdbcTemplate;
    private JdbcReservationTimeDao jdbcReservationTimeDao;
    private JdbcReservationDao reservationDao;

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
        jdbcReservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);
        reservationDao = new JdbcReservationDao(jdbcTemplate);

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("test-clear-schema.sql")
        );
    }

    @Nested
    class Insert {

        @Test
        void 예약_삽입시_생성되는_ID가_양수이다() {
            // given
            Reservation reservation = new Reservation(name, date, generateReservationTime());

            // when
            Reservation actual = reservationDao.insert(reservation);

            // then
            Assertions.assertThat(actual.getId())
                    .isNotNull()
                    .isPositive();
        }

        @Test
        void 삽입된_객체와_반환되는_객체가_id를_제외한_모든_필드가_일치한다() {
            // given
            Reservation reservation = new Reservation(name, date, generateReservationTime());

            // when
            Reservation actual = reservationDao.insert(reservation);

            // then
            Assertions.assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(reservation);
        }

        @Test
        void 두_번_삽입해도_서로_다른_ID가_부여된다() {
            // when
            Reservation reservation = new Reservation(name, date, generateReservationTime());
            Reservation first  = reservationDao.insert(reservation);
            Reservation second = reservationDao.insert(reservation);

            // then
            Assertions.assertThat(first.getId())
                    .isNotEqualTo(second.getId());
        }

    }

    @Nested
    class SelectAll {

        @Test
        void 저장된_예약이_없으면_빈리스트를_반환한다() {
            List<Reservation> actual = reservationDao.selectAll();

            Assertions.assertThat(actual)
                    .isEmpty();
        }

        @Test
        void 저장된_예약이_1개이면_조회시_크기가_1인_리스트를_반환한다() {
            // given
            Reservation reservation = new Reservation(name, date, generateReservationTime());
            reservationDao.insert(reservation);

            // when
            List<Reservation> actual = reservationDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(1);
        }

        @Test
        void 저장된_예약이_여러개이면_조회시_저장된_개수만큼_반환한다() {
            // given
            Reservation reservation = new Reservation(name, date, generateReservationTime());
            reservationDao.insert(reservation);
            reservationDao.insert(reservation);
            reservationDao.insert(reservation);

            // when
            List<Reservation> actual = reservationDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(3);
        }

        @Test
        void 삽입한_예약을_조회하면_모든_필드가_일치한다() {
            // given
            Reservation expected = reservationDao.insert(new Reservation(name, date, generateReservationTime()));

            // when
            Reservation actual = reservationDao.selectAll()
                    .getFirst();

            // then
            Assertions.assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

    }

    private static DataSource generateDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private ReservationTime generateReservationTime() {
        return jdbcReservationTimeDao.insert(reservationTime);
    }

}
