package roomescape.reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(ReservationRequest request);

    List<Reservation> findAll();

    void deleteById(Long id);

}
