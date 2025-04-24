package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    List<ReservationResponseDto> findAllReservations();

    Reservation findById(Long id);

    Long saveAndReturnId(final String name, final LocalDate requestDate, final Long timeId);

    void deleteById(final Long id);
}
