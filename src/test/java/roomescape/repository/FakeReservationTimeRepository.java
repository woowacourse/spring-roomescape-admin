package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        long index = reservationTimes.size() + 1;
        ReservationTime insertedReservationTime = new ReservationTime(index, reservationTime.getStartAt());
        reservationTimes.add(insertedReservationTime);
        return insertedReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public void delete(final long id) {
        ReservationTime selectedReservationTime = reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id가 존재하지 않습니다."));
        reservationTimes.remove(selectedReservationTime);
    }
}
