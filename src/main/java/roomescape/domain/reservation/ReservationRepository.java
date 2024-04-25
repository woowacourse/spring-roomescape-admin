package roomescape.domain.reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation create(Reservation reservation);

    void delete(Long id);
}
