package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final Reservations reservations = new Reservations(new ArrayList<>());

    public List<Reservation> findAll(){
        return reservations.getReservations();
    }

    public Reservation create(String name, String date, String time){
        return reservations.add(name, date, time);
    }

    public void delete(Long id){
        reservations.delete(id);
    }
}
