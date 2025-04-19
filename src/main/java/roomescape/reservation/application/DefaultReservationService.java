package roomescape.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;

    public DefaultReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationResponseDto> getReservations() {
        return ReservationResponseDto.from(reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDto createReservation(final ReservationRequestDto reservationRequestDto) {
        final Reservation saved = reservationRepository.save(reservationRequestDto.toDomain());
        return ReservationResponseDto.from(saved);
    }

    @Override
    public void delete(final long id) {
        reservationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        reservationRepository.deleteById(id);
    }
}
