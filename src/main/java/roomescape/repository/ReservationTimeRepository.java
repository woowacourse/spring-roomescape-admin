package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.ReservationTime;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationTimeRepository {
    ReservationTime add(ReservationTime time);

    Optional<ReservationTime> findById(Long id);

    List<ReservationTime> findAll();

    void delete(Long id);
}
