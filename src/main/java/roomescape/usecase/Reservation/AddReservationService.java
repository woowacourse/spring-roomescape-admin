package roomescape.usecase.Reservation;

import org.springframework.stereotype.Service;
import roomescape.enttity.ReservationTime.ReservationTime;
import roomescape.usecase.ReservationTime.ReservationTimeRepository;


@Service
public class AddReservationService implements AddReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;


    public AddReservationService(final ReservationRepository reservationRepository,
                                 final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;

        this.reservationTimeRepository = reservationTimeRepository;
    }
    // 시나리오가 존재하는 곳

    @Override
    public ReservationOutput addReservation(final ReservationInput reservationInput) {
        ReservationTime reservationTime = reservationTimeRepository.getReservationTime(reservationInput.timeId());
        Reservation reservation = new Reservation(null, reservationInput.name(), reservationInput.date(),
                reservationTime);
        Reservation reservationWithId = reservationRepository.addReservation(reservation);
        return ReservationOutput.from(reservationWithId);
    }
}
