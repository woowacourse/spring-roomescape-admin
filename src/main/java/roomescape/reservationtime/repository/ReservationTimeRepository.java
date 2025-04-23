package roomescape.reservationtime.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import roomescape.reservationtime.domain.ReservationTime;

public interface ReservationTimeRepository {

    RowMapper<ReservationTime> createRowMapper();

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long id);
}
