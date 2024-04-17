package roomescape.domain.reservation;

import java.util.List;

import roomescape.dto.ReservationDto;

public interface ReservationRepository {
    Reservation save(ReservationDto reservationDto);

    List<Reservation> findAll();

    void deleteById(Long id);
}
