package roomescape.mapper;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

@Component
public class ReservationMapper {

    private final ReservationTimeService reservationTimeService;

    public ReservationMapper(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    public Reservation toReservation(ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeService.findReservationTime(reservationRequestDto.getTimeId());
        return new Reservation(
                null,
                reservationRequestDto.getName(),
                reservationRequestDto.getDate(),
                reservationTime
        );
    }
}
