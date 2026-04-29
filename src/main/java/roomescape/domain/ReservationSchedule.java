package roomescape.domain;

import java.util.ArrayList;
import java.util.List;

public class ReservationSchedule {

    private final List<Reservation> schedule;

    public ReservationSchedule(List<Reservation> schedule) {
        this.schedule = new ArrayList<>(schedule);
    }

    public List<Reservation> getSchedule() {
        return schedule;
    }
}
