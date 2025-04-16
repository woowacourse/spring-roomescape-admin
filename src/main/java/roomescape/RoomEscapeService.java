package roomescape;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

@Service
public class RoomEscapeService {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public Reservation convertReservation(ReservationRequestDto reservationDto) {
        return new Reservation(
                ID_GENERATOR.incrementAndGet(),
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
    }
}
