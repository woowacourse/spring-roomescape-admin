package roomescape.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Reservations {
    // TODO map 형태로 자료 변경
    // TODO 이전 날짜를 요청한 경우 검증
    // TODO 모든 데이터가 전부 들어왔는지
    // TODO 현재 날짜 기준 이전 날짜를 들어온 경우

    private final Map<Long, Reservation> reservations;

    public Reservations(Map<Long, Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservations() {
        this(new HashMap<>());
    }

    public void add(Long id, Reservation reservation) {
        reservations.put(id, reservation);
    }

    public Reservation find(Long id) {
        return reservations.values()
                .stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Long id) {
        reservations.remove(id, find(id));
    }

    public Set<Reservation> getReservations() {
        return Set.copyOf(reservations.values());
    }
}
