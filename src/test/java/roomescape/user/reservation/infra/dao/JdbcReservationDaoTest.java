package roomescape.user.reservation.infra.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
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

class JdbcReservationDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcReservationDao jdbcReservationDao;

    @BeforeAll
    static void beforeAll() {
        initializeDatabase();
        executeSchema();
        insertDummyReservationTime();
        jdbcReservationDao = new JdbcReservationDao(jdbcTemplate);
    }

    private static void initializeDatabase() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);
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

    private static void insertDummyReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
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
            // Given
            var reservation = new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L);

            // When
            var savedId = jdbcReservationDao.save(reservation);
            var foundReservation = jdbcReservationDao.findById(savedId);

            // Then
            assertSoftly(softly -> {
                softly.assertThat(foundReservation.get().getName()).isEqualTo("브라운");
                softly.assertThat(foundReservation.get().getDate()).isEqualTo(LocalDate.of(2025, 5, 1));
                softly.assertThat(foundReservation.get().getTimeId()).isEqualTo(1L);
            });
        }

        @Test
        @DisplayName("모든 예약을 조회할 수 있다.")
        void findAll() {
            // Given
            jdbcReservationDao.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L));
            jdbcReservationDao.save(new Reservation(null, "포비", LocalDate.of(2025, 5, 2), 1L));

            // When
            var reservations = jdbcReservationDao.findAll();

            // Then
            assertThat(reservations).hasSize(2);
        }

        @Test
        @DisplayName("예약을 삭제할 수 있다.")
        void deleteById() {
            // Given
            var savedId = jdbcReservationDao.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L));

            // When
            jdbcReservationDao.deleteById(savedId);

            // Then
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
                    .hasMessageContaining("Reservation with id " + nonExistentId + " does not exist");
        }
    }
}
