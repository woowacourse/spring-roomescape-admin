package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository {

    List<ReservationJoinedDto> findAll();

    Reservation findById(long id);

    ReservationJoinedDto findJoinedDtoById(long id);

    long save(Reservation reservation);

    void deleteById(long id);
}
