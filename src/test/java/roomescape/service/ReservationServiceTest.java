package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.service.dto.ReservationCreation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    private static final ReservationTime EXAMPLE_TIME = new ReservationTime(1L, LocalTime.of(12, 0));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time
                (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);
        jdbcTemplate.execute("""
                CREATE TABLE reservation
                (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,                                        -- 컬럼 수정
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id) -- 외래키 추가
                );
                """);

        reservationTimeRepository.insert(EXAMPLE_TIME);
    }

    @DisplayName("예약자명, 날짜, 시간을 받아 예약을 생성한다")
    @Test
    void createTest() {
        // given
        ReservationCreation creation = new ReservationCreation(
                "검프", LocalDate.of(2025, 4, 4), 1L);

        // when
        ReservationResponse response = reservationService.create(creation);

        // then
        assertThat(response.date()).isEqualTo(creation.date());
        assertThat(response.name()).isEqualTo(creation.name());
        assertThat(response.time().id()).isEqualTo(creation.timeId());
    }

    @DisplayName("존재하는 예약을 조회한다")
    @Test
    void readAllTest() {
        // given
        final Reservation reservation = new Reservation(
                1L, "라젤", LocalDate.of(2025, 4, 4),
                EXAMPLE_TIME);
        reservationRepository.insert(reservation);

        // when
        List<ReservationResponse> response = reservationService.readAll();

        // then
        assertThat(response.size()).isEqualTo(1);
    }

    @DisplayName("id 값에 맞는 예약을 삭제한다")
    @Test
    void deleteByTest() {
        // given
        final Reservation reservation = new Reservation(
                1L, "라젤", LocalDate.of(2025, 4, 4),
                EXAMPLE_TIME);
        reservationRepository.insert(reservation);

        // when // then
        assertThatCode(() -> reservationService.deleteBy(1L))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 예약을 삭제하는 경우 예외를 던진다.")
    @Test
    void deleteExceptionTest() {
        // when // then
        assertThatCode(() -> reservationService.deleteBy(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
