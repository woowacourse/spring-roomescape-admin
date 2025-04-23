package roomescape.console.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.persist.entity.ReservationEntity;
import roomescape.persist.entity.ReservationTimeEntity;
import roomescape.persist.repository.ReservationRepository;

public final class MemoryReservationRepository implements ReservationRepository {

    private final Map<Long, ReservationEntity> reservations = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .map(reservationEntity -> new Reservation(
                        reservationEntity.getId(),
                        reservationEntity.getName(),
                        new ReservationDate(LocalDate.parse(reservationEntity.getDate(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                        new ReservationTime(reservationEntity.getTimeEntity().getId(),
                                LocalTime.parse(reservationEntity.getTimeEntity().getStartAt(),
                                        DateTimeFormatter.ofPattern("HH:mm")))
                ))
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        long id = idGenerator.getAndIncrement();
        ReservationEntity reservationEntity = new ReservationEntity(
                id,
                reservation.getName(),
                reservation.getDate().getStartDate().toString(),
                new ReservationTimeEntity(
                        reservation.getTime().getId(),
                        reservation.getTime().getStartTime().toString()
                )
        );
        reservations.put(id, reservationEntity);
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public void removeById(long id) {
        reservations.remove(id);
    }
}
