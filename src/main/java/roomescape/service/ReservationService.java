package roomescape.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AtomicLong index = new AtomicLong(1);

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toEntity(index.getAndIncrement());
        return reservationRepository.save(reservation);
    }

    public Reservation deleteReservation(long id) {
        if (!reservationRepository.isExist(id)) {
            throw new IllegalArgumentException("해당 ID 없음");
        }
        return reservationRepository.delete(id);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
