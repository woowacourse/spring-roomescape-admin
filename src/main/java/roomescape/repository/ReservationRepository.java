package roomescape.repository;

import java.util.List;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(ReservationRequestDto requestDto);

    void delete(Long id);
}
