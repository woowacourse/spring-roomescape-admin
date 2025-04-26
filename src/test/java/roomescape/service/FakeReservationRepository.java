package roomescape.service;

import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation insert(Reservation reservation) {
        Reservation reservationEntity = reservation.toEntity(index.getAndIncrement());
        reservations.add(reservationEntity);
        return reservationEntity;
    }

    @Override
    public boolean existByDateAndTimeId(LocalDate date, Long timeId) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().getId().equals(timeId));
    }

    @Override
    public int deleteById(Long id) {
        Reservation deleteReservation = findById(id);
        if (deleteReservation != null) {
            reservations.remove(deleteReservation);
            return 1;
        }
        return 0;
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id입니다."));
    }
}
