package roomescape.service;

import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationRepository implements ReservationRepository {

    AtomicLong index = new AtomicLong();
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long add(final Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.add(new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime()));
        return id;
    }

    @Override
    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    @Override
    public void deleteById(final Long id) {
        reservations.remove(findById(id));
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
