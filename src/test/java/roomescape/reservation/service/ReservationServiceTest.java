package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@SpringBootTest
@Transactional
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약을 저장한다")
    void 예약_저장() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(14, 0)).build());
        ReservationRequest request = new ReservationRequest("SAN",
                LocalDate.of(2026, 5, 3), time.getId());

        // when
        ReservationResponse response = reservationService.saveReservation(request);

        // then
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("SAN");
    }

    @Test
    @DisplayName("예약을 조회한다")
    void 예약_조회() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(10, 0)).build());
        reservationService.saveReservation(new ReservationRequest("브라운",
                LocalDate.of(2026, 5, 3), time.getId()));
        reservationService.saveReservation(new ReservationRequest("네오",
                LocalDate.of(2026, 5, 4), time.getId()));

        // when
        List<ReservationResponse> all = reservationService.findAllReservations();

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void 예약_삭제() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(10, 0)).build());
        ReservationResponse saved = reservationService.saveReservation(
                new ReservationRequest("이산", LocalDate.of(2026, 5, 3), time.getId()));

        // when
        reservationService.deleteById(saved.id());

        // then
        assertThat(reservationService.findAllReservations()).isEmpty();
    }

    @Test
    @DisplayName("삭제하려는 ID의 예약이 없으면 예외가 발생한다")
    void 예약_삭제_에러_발생() {
        // when & then
        assertThatThrownBy(() -> reservationService.deleteById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
