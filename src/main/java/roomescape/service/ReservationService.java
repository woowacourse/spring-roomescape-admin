package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {


    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public List<Reservation> allReservations() {
        return reservations;
    }

    public void saveReservation(ReservationRequest reservationRequest) {
        long id = index.getAndIncrement();
        String name = reservationRequest.name();
        String date = reservationRequest.date();
        String time = reservationRequest.time();
        Reservation reservation = new Reservation(id, name, date, time);
        reservations.add(reservation);
    }

    public void removeReservation(long id) {
        reservations.remove((int) id);
    }
}
