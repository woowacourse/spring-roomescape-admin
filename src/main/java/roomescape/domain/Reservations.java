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

    public List<Reservation> getSchedule() {
        return List.copyOf(schedule);
    }
}
