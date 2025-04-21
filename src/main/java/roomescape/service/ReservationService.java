package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toEntity();
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        boolean isDeleted = reservationRepository.delete(id);
        if (!isDeleted) {
            throw new IllegalStateException("해당하는 id가 없습니다");
        }
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
