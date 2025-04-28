package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public class StubReservationRepository implements ReservationRepository {

    List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(
                    1L,
                    "폰트",
                    LocalDate.of(2025, 4, 1),
                    new ReservationTime(1L, LocalTime.of(10, 0))
            ),
            new Reservation(
                    2L,
                    "폰트",
                    LocalDate.of(2025, 4, 1),
                    new ReservationTime(2L, LocalTime.of(11, 0))
            )
    ));

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
