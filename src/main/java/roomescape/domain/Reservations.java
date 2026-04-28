package roomescape.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public Long save(String name, String date, String time) {
        Reservation reservation = new Reservation(name, date, time);
        long id = idGenerator.incrementAndGet();
        reservations.put(id, reservation);

        return id;
    }

    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public Map<Long, Reservation> findAll() {
        return Map.copyOf(reservations);
    }

    public void deleteById(long id) {
        reservations.remove(id);
    }
}
