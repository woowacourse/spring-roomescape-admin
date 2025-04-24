package roomescape.console.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTimeFormatter;
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
                        new ReservationDate(ReservationDateTimeFormatter.parseDate(reservationEntity.getDate())),
                        new ReservationTime(reservationEntity.getTimeEntity().getId(),
                                ReservationDateTimeFormatter.parseTime(reservationEntity.getTimeEntity().getStartAt()))
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

    @Override
    public boolean isReservationDateTimeTaken(ReservationDate reservationDate, ReservationTime reservationTime) {
        return reservations.values().stream()
                .anyMatch(reservationEntity ->
                        isSameDate(reservationDate, reservationEntity)
                                && isSameTime(reservationTime, reservationEntity));
    }

    private boolean isSameDate(ReservationDate reservationDate, ReservationEntity reservationEntity) {
        return reservationEntity.getDate().equals(reservationDate.getStartDate().toString());
    }

    private boolean isSameTime(ReservationTime reservationTime, ReservationEntity reservationEntity) {
        return reservationEntity.getTimeEntity().getStartAt()
                .equals(reservationTime.getStartTime().toString());
    }
}
