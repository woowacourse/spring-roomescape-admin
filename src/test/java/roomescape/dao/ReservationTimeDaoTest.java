package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

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

    @Nested
    class Insert {

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

    }

    @Nested
    class SelectAll {

        @Test
        void 저장된_시간이_없으면_빈리스트를_반환한다() {
            List<ReservationTime> actual = reservationTimeDao.selectAll();

            Assertions.assertThat(actual)
                    .isEmpty();
        }

        @Test
        void 저장된_시간이_1개이면_조회시_크기가_1인_리스트를_반환한다() {
            // given
            reservationTimeDao.insert(reservationTime);

            // when
            List<ReservationTime> actual = reservationTimeDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(1);
        }

        @Test
        void 저장된_시간이_여러개이면_조회시_저장된_개수만큼_반환한다() {
            // given
            reservationTimeDao.insert(reservationTime);
            reservationTimeDao.insert(reservationTime);
            reservationTimeDao.insert(reservationTime);

            // when
            List<ReservationTime> actual = reservationTimeDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(3);
        }

        @Test
        void 삽입한_예약_시간을_조회하면_모든_필드가_일치한다() {
            ReservationTime savedTime = reservationTimeDao.insert(reservationTime);

            ReservationTime actual = reservationTimeDao.selectAll()
                    .getFirst();

            Assertions.assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(savedTime);
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

}
