package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<ReservationInfo> getReservationsInfo() {
        return reservations.stream()
                .map(ReservationInfo::from)
                .toList();
    }
}
