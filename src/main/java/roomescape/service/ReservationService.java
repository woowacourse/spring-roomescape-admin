package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> readAll() {
        return reservationRepository.readAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        if (reservationRepository.isAnyReservationConflictWith(reservation)) {
            throw new IllegalStateException(
                    "해당 예약 시간에 예약이 이미 존재합니다: " + reservation.getStartDate() + " " + reservation.getStartTime());
        }
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
