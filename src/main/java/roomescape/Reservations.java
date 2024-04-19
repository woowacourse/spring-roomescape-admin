package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public Reservations() {
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
