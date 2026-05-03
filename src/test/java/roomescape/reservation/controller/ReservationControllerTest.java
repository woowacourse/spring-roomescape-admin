package roomescape.reservation.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.RequestReservation;
import roomescape.reservation.service.ReservationService;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationControllerTest {

    private final FakeReservationService fakeReservationService;
    private final ReservationController reservationController;

    public ReservationControllerTest() {
        this.fakeReservationService = new FakeReservationService();
        this.reservationController = new ReservationController(fakeReservationService);
    }

    @Test
    void 예약_목록_조회_요청을_Service에_전달하고_결과를_반환한다() {
        List<Reservation> reservations = List.of(
                new Reservation(
                        1L, "브라운", LocalDate.of(2026, 5, 10),
                        new ReservationTime(1L, LocalTime.of(10, 0))
                )
        );
        fakeReservationService.toReturnReservations = reservations;
        List<Reservation> result = reservationController.getReservations();
        Assertions.assertThat(result).isSameAs(reservations);
    }

    @Test
    void 예약_생성_요청을_받으면_DTO_필드를_Service에_전달하고_결과를_반환한다() {
        RequestReservation request = new RequestReservation("브라운", LocalDate.of(2026, 5, 10), 1L);
        Reservation created = new Reservation(
                99L, "브라운", LocalDate.of(2026, 5, 10),
                new ReservationTime(1L, LocalTime.of(10, 0))
        );
        fakeReservationService.toReturn = created;

        Reservation result = reservationController.createReservation(request);

        Assertions.assertThat(fakeReservationService.capturedName).isEqualTo("브라운");
        Assertions.assertThat(fakeReservationService.capturedDate).isEqualTo(LocalDate.of(2026, 5, 10));
        Assertions.assertThat(fakeReservationService.capturedTimeId).isEqualTo(1L);
        Assertions.assertThat(result).isSameAs(created);
    }

    @Test
    void 예약_삭제_요청을_받으면_PathVariable_id를_Service에_전달한다() {
        reservationController.deleteReservation(7L);
        Assertions.assertThat(fakeReservationService.deletedId).isEqualTo(7L);
    }

    static class FakeReservationService implements ReservationService {

        String capturedName;
        LocalDate capturedDate;
        Long capturedTimeId;
        Long deletedId;
        List<Reservation> toReturnReservations = List.of();
        Reservation toReturn;

        @Override
        public List<Reservation> getReservations() {
            return toReturnReservations;
        }

        @Override
        public Reservation createReservation(String name, LocalDate date, Long timeId) {
            this.capturedName = name;
            this.capturedDate = date;
            this.capturedTimeId = timeId;
            return toReturn;
        }

        @Override
        public void deleteReservation(Long id) {
            this.deletedId = id;
        }
    }
}
