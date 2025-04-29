package roomescape.reservation.repository;

import org.springframework.http.HttpStatus;
import roomescape.globalException.CustomException;
import roomescape.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private static final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "존재하지 않는 예약 id로 요청하였습니다."));
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation savedReservation = new Reservation(id.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getReservationTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = findByIdOrThrow(id);
        reservations.remove(reservation);
    }

    private Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }
}

