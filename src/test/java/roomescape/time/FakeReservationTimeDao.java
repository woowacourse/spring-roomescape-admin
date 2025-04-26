package roomescape.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private final List<Long> invokeDeleteId = new ArrayList<>();
    private Long NEXT_ID = 1L;

    @Override
    public Long saveTime(final ReservationTime reservationTime) {
        final ReservationTime saveReservationTime = new ReservationTime(NEXT_ID++, reservationTime.startAt());
        reservationTimes.add(saveReservationTime);
        return saveReservationTime.id();
    }

    @Override
    public List<ReservationTime> findAllTime() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public ReservationTime findTimeById(final Long id) {
        return reservationTimes.stream()
                .filter(time -> Objects.equals(time.id(), id))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void deleteTimeById(final Long id) {
        reservationTimes.stream()
                .filter(time -> Objects.equals(time.id(), id))
                .findAny()
                .ifPresent(time -> reservationTimes.remove(time));

        invokeDeleteId.add(id);
    }

    public boolean isInvokeDeleteId(final Long id) {
        return invokeDeleteId.stream()
                .anyMatch(timeId -> Objects.equals(timeId, id));
    }

    public void clear(){
        this.NEXT_ID = 1L;
        this.reservationTimes.clear();
        this.invokeDeleteId.clear();
    }
}
