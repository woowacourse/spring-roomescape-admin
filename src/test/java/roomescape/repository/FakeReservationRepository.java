package roomescape.repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation save(final Reservation reservation) {
        long index = reservations.size() + 1;
        ReservationTime reservationTime = new ReservationTime(reservation.getTime().getId(), LocalTime.now());
        Reservation insertedReservation = new Reservation(index, reservation.getName(), reservation.getDate(),
                reservationTime);
        reservations.add(insertedReservation);
        return insertedReservation;
    }

    @Override
    public void delete(final long id) {
        Reservation selectedReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id가 존재하지 않습니다."));
        reservations.remove(selectedReservation);
    }
}
