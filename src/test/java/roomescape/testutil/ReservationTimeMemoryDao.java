package roomescape.testutil;

import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationTimeSaveDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationTimeMemoryDao implements ReservationTimeDao {

    private final AtomicLong reservationTimeId = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public ReservationTime save(ReservationTimeSaveDto reservationTimeSaveDto) {
        final Long savedReservationTimeId = reservationTimeId.getAndIncrement();
        final ReservationTime reservationTime = new ReservationTime(savedReservationTimeId, reservationTimeSaveDto.startAt());
        reservationTimes.add(reservationTime);
        return reservationTime;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public boolean deleteById(Long id) {
        final Optional<ReservationTime> findReservationTime = reservationTimes.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
        return findReservationTime.map(reservationTimes::remove).orElse(false);
    }
}
