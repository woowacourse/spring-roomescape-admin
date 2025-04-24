package roomescape.usecase;

import java.time.LocalTime;
import org.springframework.stereotype.Service;


@Service
public class AddReservationTimeService implements AddReservationTimeUseCase {

    private final ReservationTimeRepository reservationTimeRepository;

    public AddReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public ReservationTimeResponseDto addReservationTime(final LocalTime startAt) {
        return reservationTimeRepository.addReservationTime(startAt);
    }
}
