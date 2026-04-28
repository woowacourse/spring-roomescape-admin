package roomescape;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong nextKey = new AtomicLong(1L);

    public void add(Reservation reservation) {
        for (Reservation value : reservations.values()) {
            if (reservation.isSameDateTime(value)) {
                throw new IllegalArgumentException("[ERROR] 중복된 예약입니다.");
            }
        }

        Long key = nextKey.getAndIncrement();
        reservations.put(key, reservation);
    }

    public int size() {
        return reservations.size();
    }

    public List<Reservation> getAllReservations() {
        return reservations.values()
                .stream()
                .toList();
    }

    public Reservation getById(Long id) {
        validatePresent(id);
        return reservations.get(id);
    }

    public void deleteById(Long id) {
        validatePresent(id);
        reservations.remove(id);
    }

    private void validatePresent(Long id) {
        if (!reservations.containsKey(id)) {
            throw new NoSuchElementException("[ERROR] 존재하지 않는 예약입니다.");
        }
    }
}
