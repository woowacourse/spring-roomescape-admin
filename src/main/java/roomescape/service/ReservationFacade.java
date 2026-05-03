package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;

@Service
public class ReservationFacade {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationFacade(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public Reservation createReservation(ReservationRequest request) {
        reservationTimeService.findById(request.timeId());
        return reservationService.create(request);
    }

    public void deleteReservationTime(Long id) {
        if (reservationService.existsByTimeId(id)) {
            throw new IllegalArgumentException("[ERROR] 해당 시간에 예약이 존재하여 삭제할 수 없습니다.");
        }

        reservationTimeService.delete(id);
    }
}
