package roomescape.domain;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    private final List<Reservation> schedule;

    public Reservations(List<Reservation> schedule) {
        this.schedule = new ArrayList<>(schedule);
    }

    public boolean hasOverlapTime(ReservationTime reservationTime) {
        return schedule.stream()
                .anyMatch(reservation -> reservation.isOverlapping(reservationTime));
    }

    public void removeById(Long reservationId) {
        Reservation toDelete = schedule.stream()
                .filter(reservation -> reservation.isSameId(reservationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 정보입니다."));
        schedule.remove(toDelete);
    }

    public List<Reservation> getSchedule() {
        return List.copyOf(schedule);
    }
}
