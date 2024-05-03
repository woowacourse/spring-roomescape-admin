package roomescape.core.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.core.model.Reservation;

public class ReservationMemoryRepository implements ReservationRepository {
    private static final int ZERO_RESERVATION = 0;
    private static final int ONE_RESERVATION = 1;
    private final ReservationTimeMemoryRepository reservationTimeMemoryRepository;
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public ReservationMemoryRepository(ReservationTimeMemoryRepository reservationTimeMemoryRepository) {
        this.reservationTimeMemoryRepository = reservationTimeMemoryRepository;
    }

    @Override
    public Long createReservation(Reservation reservation) {
        Long newId = id.incrementAndGet();
        Reservation savedReservation = new Reservation(newId, reservation.getName(), reservation.getDate(),
                reservationTimeMemoryRepository.findById(reservation.getTime().getId()));
        reservations.put(newId, savedReservation);
        return newId;
    }

    @Override
    public List<Reservation> readReservations() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation readReservationById(Long id) {
        return reservations.get(id);
    }

    @Override
    public int deleteReservation(Long id) {
        if (reservations.remove(id) == null) {
            return ZERO_RESERVATION;
        }
        return ONE_RESERVATION;
    }
}
