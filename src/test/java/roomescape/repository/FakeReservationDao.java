package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.domain.Reservation;

public class FakeReservationDao implements ReservationDao {

    private final Map<Long, Reservation> storage = new HashMap<>();
    private long sequence = 1L;

    @Override
    public List<ReservationJoinedDto> findAll() {
        return storage.values().stream()
                .map(this::toJoinedDto)
                .toList();
    }

    @Override
    public Reservation findById(long reservationId) {
        return storage.get(reservationId);
    }

    @Override
    public ReservationJoinedDto findJoinedDtoById(long reservationId) {
        return toJoinedDto(storage.get(reservationId));
    }

    @Override
    public long insert(Reservation reservation) {
        long id = sequence++;
        Reservation newReservation = new Reservation(id, reservation.name(), reservation.date(),
                reservation.reservationTimeId());
        storage.put(id, newReservation);
        return id;
    }

    @Override
    public void deleteById(long reservationId) {
        storage.remove(reservationId);
    }

    private ReservationJoinedDto toJoinedDto(Reservation reservation) {
        return new ReservationJoinedDto(reservation.id(), reservation.name(), reservation.date(),
                reservation.reservationTimeId(), LocalTime.of(10, 0));
    }
}
