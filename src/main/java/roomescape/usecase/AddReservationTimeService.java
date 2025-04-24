package roomescape.usecase;

import java.time.LocalTime;
import org.springframework.stereotype.Service;


@Service
public class AddReservationTimeService implements AddReservationTimeUseCase {

    private final ReservationTimeRepository reservationTimeRepository;

    @Override
    public void addReservationTime(final LocalTime startAt) {

    }
}
