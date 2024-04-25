package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

public interface TimeDao {
    ReservationTime add(ReservationTimeAddRequest request);

    Optional<ReservationTime> findById(Long id);

    List<ReservationTime> findAll();

    void delete(Long id);

    void deleteAll();

    boolean isExist(Long id);

    boolean isExist(LocalTime time);
}
