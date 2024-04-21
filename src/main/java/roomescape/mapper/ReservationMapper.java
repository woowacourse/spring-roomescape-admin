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

    public Reservation toReservation(ReservationRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeService.findReservationTime(requestDto.getTimeId());
        return new Reservation(
                null,
                requestDto.getName(),
                requestDto.getDate(),
                reservationTime
        );
    }
}
