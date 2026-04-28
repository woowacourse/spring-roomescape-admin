package roomescape.service;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Component
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation add(String name, String date, String time) {
        return reservationRepository.save(
                Reservation.constructWithNoId(name, date, time)
        );
    }
}
