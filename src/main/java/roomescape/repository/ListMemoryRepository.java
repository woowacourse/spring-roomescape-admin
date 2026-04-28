package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ListMemoryRepository implements RoomescapeRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation savedReservation = reservation.withId(sequence.incrementAndGet());
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public boolean deleteById(long id) {
        return reservations.removeIf(r -> r.getId() != null && r.getId().equals(id));
    }
}
