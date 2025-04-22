package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    long add(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);
}
