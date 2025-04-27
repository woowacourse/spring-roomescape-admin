package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.repository.stub.FakeReservationRepository;
import roomescape.reservation.repository.stub.FakeReservationTimeRepository;

class ReservationServiceTest {

    ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeRepository());
    ReservationService reservationService = new ReservationService(new FakeReservationRepository(),
            reservationTimeService);
    long timeId;

    @BeforeEach
    void setUp() {
        timeId = reservationTimeService.addTime(
                new ReservationTimeRequest(LocalTime.of(12, 1))).id();
    }

    @DisplayName("예약 리스트를 가져온다")
    @Test
    void getReservations() {
        // given
        reservationService.addReservation(
                new ReservationRequest("test", LocalDate.of(2024, 12, 1), timeId));

        // when
        List<ReservationResponse> reservations = reservationService.getReservations();

        // then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addReservation() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest("test", LocalDate.of(2024, 12, 1), timeId);

        // when
        ReservationResponse reservationResponse = reservationService.addReservation(reservationRequest);

        // then
        assertThat(reservationResponse.name()).isEqualTo("test");
        assertThat(reservationResponse.date()).isEqualTo(LocalDate.of(2024, 12, 1));
    }

    @DisplayName("아이디에 해당하는 예약을 삭제한다.")
    @Test
    void deleteReservationById() {
        // given
        long id = reservationService.addReservation(
                new ReservationRequest("test", LocalDate.of(2024, 12, 1), timeId)).id();

        // when
        boolean isDeleted = reservationService.deleteReservationById(id);

        // then
        assertThat(isDeleted).isTrue();
    }
}
