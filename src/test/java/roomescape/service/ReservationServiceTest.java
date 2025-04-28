package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;

class ReservationServiceTest {
    private final MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(
            new MemoryReservationTimeRepository());
    private final ReservationService reservationService = new ReservationService(memoryReservationRepository,
            reservationTimeService);

    @Test
    @DisplayName("예약 생성")
    void test1() {
        // given
        Long timeId = reservationTimeService.addTime("10:00").getId();

        // when
        Reservation reservation = reservationService.addReservation(
                new ReservationRequestDto("테스트", "2025-05-05", timeId));

        // then
        assertAll(() -> assertThat(reservation).isNotNull(),
                () -> assertThat(reservation.getId()).isNotNull(),
                () -> assertThat(reservation.getUserName().getName()).isEqualTo("테스트")
        );
    }

    @Test
    @DisplayName("예약 전체 조회")
    void test2() {
        // given
        Long timeId_1 = reservationTimeService.addTime("10:00").getId();
        Long timeId_2 = reservationTimeService.addTime("11:00").getId();
        Reservation reservation = reservationService.addReservation(
                new ReservationRequestDto("띠용", "2025-05-05", timeId_1));
        Reservation reservation2 = reservationService.addReservation(
                new ReservationRequestDto("구구", "2025-05-06", timeId_2));

        // when & then
        assertThat(reservationService.getAllReservations()).contains(reservation, reservation2);
    }

    @Test
    @DisplayName("예약 삭제")
    void test3() {
        // given
        Long timeId = reservationTimeService.addTime("10:00").getId();
        Reservation reservation = reservationService.addReservation(
                new ReservationRequestDto("띠용", "2025-05-05", timeId));

        // when
        reservationService.deleteReservation(reservation.getId());

        // then
        assertThat(reservationService.getAllReservations()).doesNotContain(reservation);
    }
}
