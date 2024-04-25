package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation addReservation(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);

    void deleteAll();
}
