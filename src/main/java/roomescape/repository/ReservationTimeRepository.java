package roomescape.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import roomescape.domain.reservation.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    ReservationTime add(ReservationTime time);

    Optional<ReservationTime> findById(Long id);


    public List<ReservationTime> findAll();

    public void delete(Long id);
}
