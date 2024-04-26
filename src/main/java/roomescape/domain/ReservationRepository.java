package roomescape.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    Reservation create(Reservation reservation);

    void deleteById(Long id);

}
