package roomescape.domain;

import java.util.ArrayList;
import java.util.List;

public class ReservationTimes {
    private final List<ReservationTime> reservationTimes;

    public ReservationTimes() {
        this.reservationTimes = new ArrayList<>();
    }

    public void add(ReservationTime reservationTime) {
        reservationTimes.add(reservationTime);
    }

    public void remove(Long id) {
        boolean removed = reservationTimes.removeIf(reservationTime -> reservationTime.isSameId(id));
        if (!removed) {
            throw new IllegalArgumentException("일치하는 ID의 예약을 찾을 수 없습니다.");
        }
    }

    public List<ReservationTime> getReservationTimes() {
        return new ArrayList<>(reservationTimes);
    }
}
