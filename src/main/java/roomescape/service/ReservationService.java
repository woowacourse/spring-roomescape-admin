package roomescape.service;

import java.time.LocalDateTime;
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
        if (isConflict(reservation)) {
            LocalDateTime conflictDateTime = LocalDateTime.of(reservation.getStartDate(), reservation.getStartTime());
            throw new IllegalStateException("해당 시간 예약이 이미 존재합니다: " + conflictDateTime);
        }
        return reservationRepository.save(reservation);
    }

    private boolean isConflict(Reservation reservation) {
        return reservationRepository.readAll().stream()
                .filter(reservation::isConflictWith)
                .findAny()
                .isPresent();
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
