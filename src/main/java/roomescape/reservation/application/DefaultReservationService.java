package roomescape.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.reservation.application.converter.ReservationConverter;
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
    public List<ReservationResponseDto> getAll() {
        return ReservationConverter.toDto(reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDto create(final ReservationRequestDto reservationRequestDto) {
        final Reservation saved = reservationRepository.save(ReservationConverter.toDomain(reservationRequestDto));
        return ReservationConverter.toDto(saved);
    }

    @Override
    public void delete(final long id) {
        reservationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        reservationRepository.deleteById(id);
    }
}
