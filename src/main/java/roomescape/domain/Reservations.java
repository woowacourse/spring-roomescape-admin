package roomescape.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private static final String NOT_EXIST_RESERVATION = "존재하지 않는 예약입니다.";
    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public Reservations(List<Reservation> reservations){
        this.reservations = reservations;
    }

    public Reservation add(String name, String date, String time){
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                name,
                date,
                time
        );
        reservations.add(reservation);
        return reservation;
    }

    public void delete(Long id){
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if(!removed){
            throw new IllegalArgumentException(NOT_EXIST_RESERVATION);
        }
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }
}