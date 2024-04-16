package roomescape.domain.reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);
}
