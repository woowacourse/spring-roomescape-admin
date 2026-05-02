package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {
    private ReservationService reservationService;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:reservation-service-test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id),
                    UNIQUE (start_at)
                )
                """);
        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                )
                """);

        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    @DisplayName("같은 날짜와 시간으로 예약을 중복 생성할 수 없다.")
    void throwException_When_CreateDuplicateReservation() {
        insertTime("10:00");
        reservationService.create("브라운", "2026-04-29", 1L);

        assertThatThrownBy(() -> reservationService.create("리사", "2026-04-29", 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("같은 시간이라도 다른 날짜이면 예약을 생성할 수 있다.")
    void createReservation_When_SameTimeDifferentDate() {
        insertTime("10:00");
        reservationService.create("브라운", "2026-04-29", 1L);

        Reservation reservation = reservationService.create("리사", "2026-04-30", 1L);

        assertThat(reservation.getId()).isEqualTo(2L);
        assertThat(reservation.getName()).isEqualTo("리사");
        assertThat(reservation.getDate()).isEqualTo("2026-04-30");
        assertThat(reservation.getTime().id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 시간으로 예약을 생성할 수 없다.")
    void throwException_When_CreateReservationWithNotFoundTime() {
        assertThatThrownBy(() -> reservationService.create("브라운", "2026-04-29", 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    @DisplayName("잘못된 시간 ID로 예약을 생성할 수 없다.")
    void throwException_When_CreateReservationWithInvalidTimeId(Long timeId) {
        assertThatThrownBy(() -> reservationService.create("브라운", "2026-04-29", timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간 ID가 null이면 예약을 생성할 수 없다.")
    void throwException_When_CreateReservationWithNullTimeId() {
        assertThatThrownBy(() -> reservationService.create("브라운", "2026-04-29", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void insertTime(String startAt) {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", startAt);
    }
}
