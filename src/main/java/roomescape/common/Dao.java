package roomescape.common;

import java.util.List;
import roomescape.reservation.Reservation;

public interface Dao<T> {
    T add(T t);

    List<T> getAll();

    void deleteById(Long id);
}
