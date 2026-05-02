package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationJoinedDto;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationJoinedDto> allReservations() {
        return reservationRepository.findAllJoinedDto();
    }

    public ReservationJoinedDto saveReservation(String name, LocalDate date, Long reservationTimeId) {
        Reservation transientReservation = Reservation.transientOf(name, date, reservationTimeId);
        long reservationId = reservationRepository.save(transientReservation);
        return reservationRepository.findJoinedDtoById(reservationId);
    }

    public void removeReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findReservation(long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
