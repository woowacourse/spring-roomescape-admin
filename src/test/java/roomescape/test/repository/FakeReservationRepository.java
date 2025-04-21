package roomescape.test.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    AtomicLong index = new AtomicLong(1L);
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public long addReservation(Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.add(new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime()));
        return id;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public void deleteReservation(Long id) {
        Optional<Reservation> findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny();
        findReservation.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다." + id));
        reservations.remove(findReservation.get());
        System.out.println(reservations.size());
    }
}
