package roomescape.dao.reservation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.resetvationTime.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;

public class InMemoryReservationDao implements ReservationDao {

    private final List<Reservation> reservations;
    private final ReservationTimeDao reservationTimeDao;
    private final AtomicLong index = new AtomicLong(1);

    public InMemoryReservationDao(final List<Reservation> reservations, final ReservationTimeDao reservationTimeDao) {
        this.reservations = reservations;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public long create(final ReservationCreateRequest reservationCreateRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationCreateRequest.timeId());
        Reservation reservation = new Reservation(index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationTime
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    @Override
    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.isEqualId(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        reservations.remove(reservation);
    }
}
