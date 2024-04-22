package roomescape.service;

import org.springframework.stereotype.Component;
import roomescape.controller.reservation.ReservationRequest;
import roomescape.controller.reservation.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationMapper {

    private final ReservationTimeMapper timeMapper;

    public ReservationMapper(ReservationTimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    public Reservation map(ReservationRequest request) {
        return new Reservation(
                null,
                request.name(),
                request.date(),
                new ReservationTime(request.timeId(), null)
        );
    }

    public ReservationResponse map(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE),
                timeMapper.map(reservation.time())
        );
    }
}
