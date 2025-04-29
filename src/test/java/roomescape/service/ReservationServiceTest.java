package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void beforeEach() {
        ReservationRepository reservationRepository = new FakeReservationRepository();
        ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeRepository.save(ReservationTime.of(LocalTime.of(10, 0)));
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    @DisplayName("존재하지 않은 시간대의 아이디를 넣으면 예외가 발생한다.")
    void createReservation_NoTime() {
        // given
        ReservationRequest request = ReservationRequest.of("코기", "2000-11-02", 100L);
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("정상적으로 예약 생성 후 DTO를 반환한다.")
    void createReservationTest() {
        // given
        ReservationRequest request = ReservationRequest.of("코기", "2000-11-02", 1L);
        // when
        ReservationResponse reservation = reservationService.createReservation(request);
        // then
        assertAll(
                () -> assertThat(reservation.id()).isNotNull(),
                () -> assertThat(reservation.name()).isEqualTo("코기"),
                () -> assertThat(reservation.date()).isEqualTo("2000-11-02"),
                () -> assertThat(reservation.time().id()).isNotNull(),
                () -> assertThat(reservation.time().startAt()).isEqualTo("10:00")
        );
    }

    @Test
    @DisplayName("전체 예약 정보를 DTO로 변환한다.")
    void findAllReservations() {
        // given
        ReservationRequest request1 = ReservationRequest.of("코기", "2000-11-02", 1L);
        ReservationRequest request2 = ReservationRequest.of("멍구", "2000-11-03", 1L);
        reservationService.createReservation(request1);
        reservationService.createReservation(request2);
        // when
        List<ReservationResponse> allReservations = reservationService.findAllReservations();
        // then
        assertAll(
                () -> assertThat(allReservations).hasSize(2),
                () -> assertThat(allReservations.get(0).id()).isNotNull(),
                () -> assertThat(allReservations.get(0).name()).isEqualTo("코기"),
                () -> assertThat(allReservations.get(0).date()).isEqualTo("2000-11-02"),
                () -> assertThat(allReservations.get(0).time().id()).isNotNull(),
                () -> assertThat(allReservations.get(0).time().startAt()).isEqualTo("10:00"),
                () -> assertThat(allReservations.get(1).id()).isNotNull(),
                () -> assertThat(allReservations.get(1).name()).isEqualTo("멍구"),
                () -> assertThat(allReservations.get(1).date()).isEqualTo("2000-11-03"),
                () -> assertThat(allReservations.get(1).time().id()).isNotNull(),
                () -> assertThat(allReservations.get(1).time().startAt()).isEqualTo("10:00")
        );
    }

    @Test
    @DisplayName("정상적으로 삭제를 한다.")
    void deleteReservationTest() {
        // given
        ReservationRequest request = ReservationRequest.of("코기", "2000-11-02", 1L);
        ReservationResponse reservation = reservationService.createReservation(request);
        // when
        reservationService.deleteReservation(reservation.id());
        // then
        assertThat(reservationService.findAllReservations()).isEmpty();
    }

    @Test
    @DisplayName("정상적으로 예약 시간을 저장하고 dto를 반환한다.")
    void createReservationTimeTest() {
        // given
        ReservationTimeRequest request = ReservationTimeRequest.of("09:00");
        // when
        ReservationTimeResponse reservationTime = reservationService.createReservationTime(request);
        // then
        assertThat(reservationTime.startAt()).isEqualTo("09:00");
        assertThat(reservationTime.id()).isNotNull();
    }

    @Test
    @DisplayName("모든 예약 시간을 dto로 조회한다.")
    void findAllReservationTimesTest() {
        // when
        List<ReservationTimeResponse> allReservationTimes = reservationService.findAllReservationTimes();
        // then
        assertAll(
                () -> assertThat(allReservationTimes).hasSize(1),
                () -> assertThat(allReservationTimes.get(0).id()).isNotNull(),
                () -> assertThat(allReservationTimes.get(0).startAt()).isEqualTo("10:00")
        );
    }

    @Test
    @DisplayName("정상적인 요청에 대해 삭제를 진행한다.")
    void deleteReservationTimeTest() {
        // given
        ReservationTimeRequest request = ReservationTimeRequest.of("09:00");
        ReservationTimeResponse reservationTime = reservationService.createReservationTime(request);
        // when
        reservationService.deleteReservationTime(reservationTime.id());
        // then
        List<ReservationTimeResponse> allReservationTimes = reservationService.findAllReservationTimes();
        assertAll(
                () -> assertThat(allReservationTimes).hasSize(1),
                () -> assertThat(allReservationTimes.get(0).id()).isNotNull(),
                () -> assertThat(allReservationTimes.get(0).startAt()).isEqualTo("10:00")
        );
    }
}