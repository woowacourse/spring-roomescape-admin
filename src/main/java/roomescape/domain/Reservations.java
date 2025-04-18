package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import roomescape.dto.ReservationRes;

public class Reservations {

    private final ConcurrentHashMap<Long, Reservation> reservations;
    private Long index;

    public Reservations() {
        this.reservations = new ConcurrentHashMap<>();
        this.index = 1L;
    }

    public long add(final Reservation reservation) {
        reservations.put(index, reservation);
        return index++;
    }

    public boolean isExistById(final Long id) {
        return reservations.containsKey(id);
    }

    public void deleteBy(final Long id) {
        reservations.remove(id);
    }

    public List<ReservationRes> getReservations() {
        final List<ReservationRes> reservationRes = new ArrayList<>();
        for (Entry<Long, Reservation> each : reservations.entrySet()) {
            reservationRes.add(ReservationRes.from(each.getKey(), each.getValue()));
        }
        return reservationRes;
    }
}
