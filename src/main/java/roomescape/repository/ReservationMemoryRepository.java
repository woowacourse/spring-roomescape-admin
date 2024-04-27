package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.Reservation;

public class ReservationMemoryRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private static final AtomicLong INDEX = new AtomicLong(1L);

    @Override
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    @Override
    public Reservation addReservation(Reservation reservationDto) {
        Reservation reservation = new Reservation(
                INDEX.getAndIncrement(),
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationDto.getTime());
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public long deleteReservation(long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아이디입니다."));
        reservations.remove(findReservation);
        return id;
    }
}
