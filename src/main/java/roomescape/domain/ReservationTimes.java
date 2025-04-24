package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservationTimes {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    public synchronized void addReservationTime(ReservationTime reservationTime) {
        reservationTimes.add(reservationTime);
    }

    public List<ReservationTime> getReservationTimes() {
        return Collections.unmodifiableList(reservationTimes);
    }

    public synchronized void deleteById(long id) {
        ReservationTime reservationTime = findById(id);
        reservationTimes.remove(reservationTime);
    }

    public synchronized ReservationTime findById(long id) {
        return reservationTimes.stream()
            .filter(reservation -> reservation.getId() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약번호 입니다."));
    }
}
