package roomescape.mapper;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;

@Component
public class ReservationMapper {

    public CreateReservationResponse toCreateReservationResponse(Reservation reservation) {
        return new CreateReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public Reservation toReservation(CreateReservationRequest request) {
        return Reservation.from(
                request.name(),
                request.date(),
                request.timeId()
        );
    }
}
