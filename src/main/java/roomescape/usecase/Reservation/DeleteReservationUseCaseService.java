package roomescape.usecase.Reservation;

import org.springframework.stereotype.Service;

@Service
public class DeleteReservationUseCaseService implements DeleteReservationUseCase {
    private final ReservationRepository reservationRepository;

    public DeleteReservationUseCaseService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void deleteReservation(final long id) {
        reservationRepository.deleteReservation(id);
    }
}
