package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.repository.ReservationRepository;

@Service
public class RoomReservationService {
    private final ReservationRepository reservationRepository;

    public RoomReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.getAllReservation();
    }

    @Transactional
    public Reservation addReservation(ReservationCommand reservationCommand) {
        return reservationRepository.addReservation(reservationCommand);
    }

    public int deleteReservation(long id) {
        return reservationRepository.deleteReservation(id);
    }
}
