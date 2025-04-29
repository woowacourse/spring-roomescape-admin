package roomescape.user.reservation.infra.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.stream.Collectors;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.user.reservation.domain.ReservationTime;

class JdbcReservationTimeDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcReservationTimeDao jdbcReservationTimeDao;

    @BeforeAll
    static void beforeAll() {
        jdbcTemplate = initializeDatabase();
        executeSchema();
        jdbcReservationTimeDao = initDao();
    }

    private static JdbcTemplate initializeDatabase() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        return new JdbcTemplate(dataSource);
    }

    private static void executeSchema() {
        try (InputStream inputStream = JdbcReservationTimeDaoTest.class.getClassLoader().getResourceAsStream("schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String schemaSql = reader.lines().collect(Collectors.joining("\n"));
            for (String statement : schemaSql.split(";")) {
                if (!statement.isBlank()) {
                    jdbcTemplate.execute(statement.trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute schema.sql", e);
        }
    }

    private static JdbcReservationTimeDao initDao() {
        return new JdbcReservationTimeDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        jdbcReservationTimeDao.clear();
    }

    @AfterEach
    void tearDown() {
        jdbcReservationTimeDao.clear();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약 시간을 저장하고 조회할 수 있다.")
        void saveAndFindById() {
            // given
            var reservationTime = new ReservationTime(null, LocalTime.of(10, 0));

            // when
            var savedId = jdbcReservationTimeDao.save(reservationTime);
            var foundReservationTime = jdbcReservationTimeDao.findById(savedId);

            // then
            assertThat(foundReservationTime.get().getStartAt()).isEqualTo(LocalTime.of(10, 0));
        }

        @Test
        @DisplayName("모든 예약 시간을 조회할 수 있다.")
        void findAll() {
            // given
            jdbcReservationTimeDao.save(new ReservationTime(null, LocalTime.of(10, 0)));
            jdbcReservationTimeDao.save(new ReservationTime(null, LocalTime.of(11, 0)));

            // when
            var reservationTimes = jdbcReservationTimeDao.findAll();

            // then
            assertThat(reservationTimes).hasSize(2);
        }

        @Test
        @DisplayName("예약 시간을 삭제할 수 있다.")
        void deleteById() {
            // given
            var savedId = jdbcReservationTimeDao.save(new ReservationTime(null, LocalTime.of(10, 0)));

            // when
            jdbcReservationTimeDao.deleteById(savedId);

            // then
            assertThat(jdbcReservationTimeDao.findById(savedId)).isEmpty();
        }
    }

    @Nested
    class InvalidCases {

        @Test
        @DisplayName("존재하지 않는 예약을 삭제하면 예외를 던진다.")
        void deleteById() {
            // given
            var nonExistentId = 9999L;

            // when & then
            assertThatThrownBy(() -> jdbcReservationTimeDao.deleteById(nonExistentId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Reservation time with id " + nonExistentId + " does not exist");
        }
    }
}
