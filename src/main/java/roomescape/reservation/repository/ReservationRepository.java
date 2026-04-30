package roomescape.reservation.repository;

import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Long save(ReservationRequestDto requestDto);

    void delete(Long id);

    Reservation findById(Long id);

    List<Reservation> findAll();
}
