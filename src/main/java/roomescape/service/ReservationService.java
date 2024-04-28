package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.exception.IllegalReservationTimeException;
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
        if (isConflict(reservation)) {
            throw new IllegalReservationTimeException("해당 시간 예약이 이미 존재합니다: " + reservation.getStartDateTime());
        }
        return reservationRepository.save(reservation);
    }

    private boolean isConflict(Reservation reservation) {
        return reservationRepository.existByReservationTime(reservation.getStartDate(), reservation.getGameTimeId());
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
