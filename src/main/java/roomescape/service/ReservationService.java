package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationJoinedDto> allReservations() {
        return reservationRepository.findAllJoinedDto();
    }

    public Reservation saveReservation(String name, LocalDate date, Long reservationTimeId) {
        Reservation transientReservation = Reservation.transientOf(name, date, reservationTimeId);
        return reservationRepository.save(transientReservation);
    }

    public void removeReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findReservation(long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
