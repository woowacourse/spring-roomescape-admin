package roomescape.domain.reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReservationTimes {

    private final Map<Long, ReservationTime> reservationTimes;

    public ReservationTimes(Map<Long, ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public ReservationTimes() {
        this(new HashMap<>());
    }

    public void add(Long id, ReservationTime reservationTime) {
        if (hasDuplicatedId(id)) {
            throw new IllegalArgumentException("시간 객체의 중복된 키가 존재합니다");
        }

        reservationTimes.put(id, reservationTime);
    }

    public void delete(Long id) {
        reservationTimes.remove(id);
    }

    public Set<ReservationTime> getReservationTimes() {
        return Set.copyOf(reservationTimes.values());
    }

    private boolean hasDuplicatedId(Long otherId) {
        return reservationTimes.keySet()
                .stream()
                .anyMatch(id -> id.equals(otherId));
    }
}
