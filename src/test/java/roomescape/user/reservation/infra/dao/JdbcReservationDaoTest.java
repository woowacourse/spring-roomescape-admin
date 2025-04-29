package roomescape.user.reservation.infra.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
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
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationTime;

class JdbcReservationDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcReservationTimeDao jdbcReservationTimeDao;
    private static ReservationTime dummyReservationTime;
    private static JdbcReservationDao jdbcReservationDao;

    @BeforeAll
    static void beforeAll() {
        jdbcTemplate = initializeDatabase();
        executeSchema();
        jdbcReservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);
        dummyReservationTime = insertDummyReservationTime();
        jdbcReservationDao = new JdbcReservationDao(jdbcReservationTimeDao, jdbcTemplate);
    }

    private static JdbcTemplate initializeDatabase() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        return new JdbcTemplate(dataSource);
    }

    private static void executeSchema() {
        try (InputStream inputStream = JdbcReservationDaoTest.class.getClassLoader().getResourceAsStream("schema.sql");
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

    private static ReservationTime insertDummyReservationTime() {
        Long savedId = jdbcReservationTimeDao.save(new ReservationTime(null, LocalTime.of(10, 0)));

        return jdbcReservationTimeDao.findById(savedId).get();
    }

    @BeforeEach
    void setUp() {
        jdbcReservationDao.clear();
    }

    @AfterEach
    void tearDown() {
        jdbcReservationDao.clear();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약을 저장하고 조회할 수 있다.")
        void saveAndFindById() {
            // given
            var reservation = new Reservation(null, "브라운", LocalDate.of(2025, 5, 1),
                    new ReservationTime(null, LocalTime.of(10, 0)));

            // when
            var savedId = jdbcReservationDao.save(reservation);
            var foundReservation = jdbcReservationDao.findById(savedId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundReservation.get().getName()).isEqualTo("브라운");
                softly.assertThat(foundReservation.get().getDate()).isEqualTo(LocalDate.of(2025, 5, 1));
                softly.assertThat(foundReservation.get().extractTime()).isEqualTo(LocalTime.of(10, 0));
            });
        }

        @Test
        @DisplayName("모든 예약을 조회할 수 있다.")
        void findAll() {
            // given
            jdbcReservationDao.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), dummyReservationTime));
            jdbcReservationDao.save(new Reservation(null, "포비", LocalDate.of(2025, 5, 2), dummyReservationTime));

            // when
            var reservations = jdbcReservationDao.findAll();

            // then
            assertThat(reservations).hasSize(2);
        }

        @Test
        @DisplayName("예약을 삭제할 수 있다.")
        void deleteById() {
            // given
            var savedId = jdbcReservationDao.save(
                    new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), dummyReservationTime));

            // when
            jdbcReservationDao.deleteById(savedId);

            // then
            assertThat(jdbcReservationDao.findById(savedId)).isEmpty();
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
            assertThatThrownBy(() -> jdbcReservationDao.deleteById(nonExistentId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Reservation with id " + nonExistentId + " does not exist");
        }
    }
}
