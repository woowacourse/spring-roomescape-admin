package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public void add(final Reservation reservation) {
        validateUnique(reservation.getId());
        reservations.add(reservation);
    }

    public void removeById(final Long id) {
        reservations.remove(findById(id));
    }

    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(rs -> rs.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    private void validateUnique(final Long id) {
        final boolean isNotUnique = reservations.stream().anyMatch(reservation -> reservation.getId().equals(id));
        if (isNotUnique) {
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
