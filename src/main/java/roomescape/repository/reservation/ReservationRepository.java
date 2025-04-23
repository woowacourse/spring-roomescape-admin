package roomescape.repository.reservation;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;

@Repository
public interface ReservationRepository {
    List<Reservation> findAll();

    Long addAndGetId(CreateReservationDto createReservationDto);

    void deleteById(Long id);

    Reservation findById(Long id);
}
