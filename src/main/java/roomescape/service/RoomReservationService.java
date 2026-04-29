package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class RoomReservationService {
    private static final String INVALID_RESERVATION_ID = "해당 예약은 존재하지 않습니다.";

    private final ReservationRepository reservationRepository;

    public RoomReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservation() {
        return List.copyOf(reservationRepository.getAllReservation());
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.addReservation(reservation);
    }

    public void deleteReservation(long id) {
       Optional<Reservation> reservation = reservationRepository.getReservation(id);

       if(reservation.isEmpty()) {
           throw new NoSuchElementException(INVALID_RESERVATION_ID);
       }

        reservationRepository.deleteReservation(id);
    }
}
