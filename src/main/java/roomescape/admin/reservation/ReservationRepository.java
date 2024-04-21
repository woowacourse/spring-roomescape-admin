package roomescape.admin.reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    int delete(Long id);
}
