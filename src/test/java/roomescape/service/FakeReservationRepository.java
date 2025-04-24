package roomescape.service;

import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationRepository implements ReservationRepository {

    AtomicLong index = new AtomicLong(1L);
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long add(final Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.add(new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime()));
        return id;
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return Optional.of(reservation);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(final Long id) {
        reservations.remove(findById(id).get());
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
