package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository {
    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Reservation findById(long id);

    void deleteById(long id);
}
