package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;

@Repository
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);
}
