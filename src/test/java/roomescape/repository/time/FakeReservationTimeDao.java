package roomescape.repository.time;

import java.util.List;
import java.util.Optional;
import roomescape.domain.time.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {

    final List<ReservationTime> reservationTimes;
    long id = 1L;

    public FakeReservationTimeDao(List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public void addAll(List<ReservationTime> reservationTimes) {
        for (ReservationTime reservationTime : reservationTimes) {
            save(reservationTime);
        }
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.stream()
            .filter(reservationTime -> reservationTime.getId() == id)
            .findFirst();
    }

    @Override
    public void save(ReservationTime reservationTime) {
        reservationTime.setId(id++);
        reservationTimes.add(reservationTime);
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public void deleteById(long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }
}
