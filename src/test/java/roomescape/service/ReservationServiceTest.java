package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.ReservationTime;
import roomescape.model.exception.ReservationNotFoundException;
import roomescape.model.exception.ReservationTimeNotFoundException;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@ActiveProfiles("test")
@JdbcTest
@Import({ReservationDao.class, ReservationTimeDao.class, ReservationService.class})
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationTimeDao.insert(new ReservationTime(null, LocalTime.of(10, 0)));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");

        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("예약 생성 요청이 들어오면 예약을 생성한다.")
    @Test
    void createReservation() {
        ReservationCreateRequest request = new ReservationCreateRequest("포스티", LocalDate.of(2025, 4, 27), 1L);

        ReservationResponse response = reservationService.createReservation(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("포스티");
        assertThat(response.time().startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("예약 생성 요청에 빈 값이 들어오면 예약을 생성할 수 없다.")
    @MethodSource
    @ParameterizedTest
    void createReservationWithBadRequest(ReservationCreateRequest request) {
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> createReservationWithBadRequest() {
        return Stream.of(
                Arguments.of(new ReservationCreateRequest("", LocalDate.of(2025, 4, 27), 1L)),
                Arguments.of(new ReservationCreateRequest("포스티", null, 1L))
        );
    }

    @DisplayName("존재하지 않는 예약 시간으로 예약을 생성할 수 없다.")
    @Test
    void createReservationWithNonExistsTime() {
        ReservationCreateRequest request = new ReservationCreateRequest("포스티", LocalDate.of(2025, 4, 25), 2L);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(ReservationTimeNotFoundException.class)
                .hasMessage("존재하지 않는 예약 시간 번호입니다.");
    }

    @DisplayName("예약 번호로 예약을 삭제한다.")
    @Test
    void deleteReservationById() {
        ReservationResponse reservation = reservationService.createReservation(
                new ReservationCreateRequest("포스티", LocalDate.of(2025, 4, 27), 1L));

        reservationService.deleteReservation(reservation.id());

        assertThat(reservationService.findAllReservations()).isEmpty();
    }

    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    @Test
    void deleteReservationByNonExistsId() {
        assertThatThrownBy(() -> reservationService.deleteReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("존재하지 않는 예약번호 입니다.");
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAllReservations() {
        reservationService.createReservation(new ReservationCreateRequest("포스티", LocalDate.of(2025, 4, 27), 1L));

        List<ReservationResponse> reservationResponses = reservationService.findAllReservations();

        assertThat(reservationResponses).hasSize(1);
    }
}
