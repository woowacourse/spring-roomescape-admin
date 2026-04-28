package roomescape.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public void save(String name, String date, String time) {
        Reservation reservation = new Reservation(name, date, time);
        reservations.put(idGenerator.incrementAndGet(), reservation);
    }

    public Map<Long, Reservation> findAll() {
        return Map.copyOf(reservations);
    }
}