package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

class ReservationServiceTest {

    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @DisplayName("예약 조회 및 생성에 성공한다.")
    @Test
    void create() {
        //given
        String name = "choco";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(19, 55, 18);
        ReservationRequest reservationRequest = new ReservationRequest(name, date, time);

        //when
        reservationService.create(reservationRequest);

        //then
        assertThat(reservationService.findAllReservations()).hasSize(1);
        assertThat(reservationService.findAllReservations().get(0).name()).isEqualTo(name);
    }

    @DisplayName("삭제에 성공한다.")
    @Test
    void delete() {
        //given
        String name = "choco";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(19, 55, 18);
        ReservationRequest reservationRequest = new ReservationRequest(name, date, time);
        reservationService.create(reservationRequest);
        ReservationResponse reservationResponse = reservationService.findAllReservations().get(0);

        //when
        boolean deleted = reservationService.delete(reservationResponse.id());

        //then
        assertThat(deleted).isTrue();
        assertThat(reservationService.findAllReservations()).hasSize(0);
    }
}
