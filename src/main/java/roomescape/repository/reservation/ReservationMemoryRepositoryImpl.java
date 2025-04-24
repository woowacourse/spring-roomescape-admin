package roomescape.repository.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;

@Repository("reservationMemoryRepository")
public class ReservationMemoryRepositoryImpl implements ReservationRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Long addAndGetId(Reservation requestReservation) {
        Long newId = id.getAndIncrement();
        Reservation reservation = new Reservation(
                newId,
                requestReservation.getName(),
                requestReservation.getDate(),
                requestReservation.getTime()
        );
        reservations.add(reservation);
        return newId;
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        // TODO: 예외 발생 처리 필요
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new InvalidReservationException("존재하지 않는 예약입니다."));
    }
}
