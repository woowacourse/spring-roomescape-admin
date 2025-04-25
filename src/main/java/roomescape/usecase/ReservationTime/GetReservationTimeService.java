package roomescape.usecase.ReservationTime;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetReservationTimeService implements GetReservationTimeUseCase {

    private final ReservationTimeRepository reservationTimeRepository;

    public GetReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public List<ReservationTimeOutput> getReservationTime() {
        return reservationTimeRepository.getReservationTime();
    }
}
