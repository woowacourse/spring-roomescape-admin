package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
