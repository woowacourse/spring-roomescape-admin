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
import java.util.Optional;

class ReservationTimeDaoTest {

    private final int hour = 10;
    private final int minute = 0;
    private final LocalTime time = LocalTime.of(hour, minute);
    private final ReservationTime reservationTime = new ReservationTime(time);

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

    @AfterAll
    static void dropTable() throws SQLException {
        DataSource dataSource = generateDataSource();

        ScriptUtils.executeSqlScript(
                dataSource.getConnection(),
                new ClassPathResource("drop-table-schema.sql")
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
            List<ReservationTime> reservationTimes = List.of(reservationTime);
            insert(reservationTimes);

            // when
            List<ReservationTime> actual = reservationTimeDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(reservationTimes.size());
        }

        @Test
        void 저장된_시간이_여러개이면_조회시_저장된_개수만큼_반환한다() {
            // given
            List<ReservationTime> reservationTimes = List.of(reservationTime, reservationTime, reservationTime);
            insert(reservationTimes);

            // when
            List<ReservationTime> actual = reservationTimeDao.selectAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(reservationTimes.size());
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

    @Nested
    class Select {

        @Test
        void 존재하는_ID로_단건_조회하면_Optional로_반환한다() {
            // given
            ReservationTime saved = reservationTimeDao.insert(reservationTime);

            // when
            Optional<ReservationTime> result = reservationTimeDao.select(saved.getId());

            // then
            Assertions.assertThat(result).isPresent();
        }

        @Test
        void 저장된_값과_존재하는_ID로_단건_조회한_값의_모든필드가_일치한다() {
            // given
            ReservationTime saved = reservationTimeDao.insert(reservationTime);

            // when
            ReservationTime actual = reservationTimeDao.select(saved.getId()).orElseThrow();

            // then
            Assertions.assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(saved);
        }

        @Test
        void 존재하지_않는_ID로_단건_조회하면_빈Optional을_반환한다() {
            // given
            Long wrongId = Long.MIN_VALUE;

            // when
            Optional<ReservationTime> actual = reservationTimeDao.select(wrongId);

            // then
            Assertions.assertThat(actual)
                    .isEmpty();
        }

    }

    @Nested
    class Delete {

        @Test
        void 존재하는_Id로_삭제하면_true를_반환한다() {
            // given
            ReservationTime saved = reservationTimeDao.insert(reservationTime);

            // when
            boolean actual = reservationTimeDao.delete(saved.getId());

            // then
            Assertions.assertThat(actual)
                    .isTrue();
        }

        @Test
        void 존재하지않는_Id로_삭제하면_false를_반환한다() {
            // given
            Long wrongId = Long.MIN_VALUE;

            // when
            boolean actual = reservationTimeDao.delete(wrongId);

            // then
            Assertions.assertThat(actual)
                    .isFalse();
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

    private void insert(List<ReservationTime> reservationTimes) {
        for (ReservationTime reservationTime : reservationTimes) {
            reservationTimeDao.insert(reservationTime);
        }
    }

}
