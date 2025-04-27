package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {

    RowMapper<Reservation> createRowMapper();

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
