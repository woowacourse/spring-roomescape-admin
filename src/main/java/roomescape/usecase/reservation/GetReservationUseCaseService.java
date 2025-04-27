package roomescape.usecase.reservation;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetReservationUseCaseService implements GetReservationUseCase {
    private final ReservationRepository reservationRepository;

    public GetReservationUseCaseService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationOutput> getReservationOutput() {
        return reservationRepository.getAllReservations();
    }
}
