package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.TimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.testRepository.FakeReservationRepository;
import roomescape.testRepository.FakeTimeRepository;

class ReservationServiceTest {

    private ReservationService reservationService;
    private FakeReservationRepository reservationRepository;
    private FakeTimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationRepository();
        timeRepository = new FakeTimeRepository();
        TimeService timeService = new TimeService(timeRepository);
        reservationService = new ReservationService(reservationRepository, timeService);
    }

    @DisplayName("예약을 정상적으로 등록한다.")
    @Test
    void register_Reservation() {
        // given
        timeRepository.save(ReservationTime.withoutId(LocalTime.of(10, 0)));

        String date = "2025-04-01";
        String name = "멍구";
        Long timeId = 1L;

        ReservationRequest reservationRequest = new ReservationRequest(date, name, timeId);

        // when
        ReservationResponse response = reservationService.registerReservation(reservationRequest);

        // then
        assertAll(
                () -> assertThat(response.id()).isEqualTo(1),
                () -> assertThat(response.name()).isEqualTo("멍구"),
                () -> assertThat(response.date()).isEqualTo("2025-04-01"),
                () -> assertThat(response.time()).isEqualTo(new TimeResponse(1L, "10:00"))
        );
    }

    @DisplayName("잘못된 형식의 date로 예약 등록 요청했을 때 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"20000-04-01", "2024-04", "2024,10,00"})
    void registerReservationFail_when_invalidFormattedDate() {
        // given
        timeRepository.save(ReservationTime.withoutId(LocalTime.of(10, 0)));

        String date = "20255-04-01";
        String name = "멍구";
        Long timeId = 1L;

        ReservationRequest reservationRequest = new ReservationRequest(date, name, timeId);

        // when & then
        assertThatThrownBy(() -> reservationService.registerReservation(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 날짜입니다: " + date);
    }

    @DisplayName("없는 날짜(2월 30일)로 예약 등록 요청했을 때 예외가 발생한다.")
    @Test
    void registerReservationFail_when_nonExistDate() {
        // given
        timeRepository.save(ReservationTime.withoutId(LocalTime.of(10, 0)));

        String date = "2025-02-30";
        String name = "멍구";
        Long timeId = 1L;

        ReservationRequest reservationRequest = new ReservationRequest(date, name, timeId);

        // when & then
        assertThatThrownBy(() -> reservationService.registerReservation(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 날짜입니다: " + date);
    }

    @DisplayName("모든 예약을 조회할 수 있다.")
    @Test
    void getAllReservations() {
        // given
        ReservationTime time1 = ReservationTime.of(1L, LocalTime.of(10, 0));
        ReservationTime time2 = ReservationTime.of(2L, LocalTime.of(11, 0));

        reservationRepository.save(Reservation.of(1L, "브라운", LocalDate.of(2024, 4, 1), time1));
        reservationRepository.save(Reservation.of(2L, "솔라", LocalDate.of(2024, 4, 1), time2));
        reservationRepository.save(Reservation.of(3L, "브리", LocalDate.of(2024, 4, 2), time1));

        // when
        List<ReservationResponse> allReservations = reservationService.getAllReservations();

        // then
        assertAll(
                () -> assertThat(allReservations).hasSize(3),
                () -> assertThat(allReservations)
                        .extracting(ReservationResponse::name)
                        .containsExactly("브라운", "솔라", "브리")
        );
    }

    @DisplayName("예약을 id로 취소할 수 있다.")
    @Test
    void cancelReservation() {
        // given
        ReservationTime time = ReservationTime.of(1L, LocalTime.of(10, 0));
        reservationRepository.save(Reservation.of(1L, "브라운", LocalDate.of(2024, 4, 1), time));

        assertThat(reservationRepository.findAll()).hasSize(1);

        // when
        reservationService.cancelReservation(1L);

        // then
        assertThat(reservationRepository.findAll()).hasSize(0);
    }
}
