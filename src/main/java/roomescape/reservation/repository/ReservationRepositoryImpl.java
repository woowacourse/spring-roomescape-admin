package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final int INIT_INDEX = 1;

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(INIT_INDEX);

    @Override
    public long generateId() {
        return index.getAndIncrement();
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.stream()
                .toList();
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.sameId(id))
                .findFirst();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (hasID(reservation)) {
            return updateReservation(reservation);
        }
        return addNewReservation(reservation);
    }

    @Override
    public void deleteAll() {
        reservations.clear();
        index.set(INIT_INDEX);
    }

    @Override
    public void deleteById(long id) {
        Reservation findReservation = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));

        reservations.remove(findReservation);
    }

    private Reservation updateReservation(Reservation reservation) {
        int index = getIndex(reservation);
        reservations.add(index, reservation);
        return reservation;
    }

    private Reservation addNewReservation(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    private boolean hasID(Reservation compareReservation) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.sameId(compareReservation));
    }

    private int getIndex(Reservation compareReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);

            if (reservation.sameId(compareReservation)) {
                return i;
            }
        }

        throw new IllegalArgumentException("해당 객체가 존재하지 않습니다!");
    }
}
