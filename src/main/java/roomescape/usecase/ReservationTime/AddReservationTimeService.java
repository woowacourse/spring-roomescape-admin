package roomescape.usecase.ReservationTime;

import java.time.LocalTime;
import org.springframework.stereotype.Service;
import roomescape.enttity.ReservationTime.ReservationTime;


@Service
public class AddReservationTimeService implements AddReservationTimeUseCase {

    private final ReservationTimeRepository reservationTimeRepository;

    public AddReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public ReservationTimeOutput addReservationTime(final LocalTime startAt) {
        ReservationTime reservationTime = new ReservationTime(null, startAt);
        return reservationTimeRepository.addReservationTime(reservationTime);
    }
}
