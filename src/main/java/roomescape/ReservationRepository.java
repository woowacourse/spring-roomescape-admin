package roomescape;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private final List<Reservation> reservations;

    public ReservationRepository() {
        reservations = new ArrayList<>();
    }

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }
}
