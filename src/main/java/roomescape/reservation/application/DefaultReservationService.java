package roomescape.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.application.converter.ReservationConverter;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<ReservationResponseDto> getAll() {
        return ReservationConverter.toDto(
                reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDto create(final ReservationRequestDto reservationRequestDto) {
        return ReservationConverter.toDto(
                reservationRepository.save(
                        ReservationConverter.toDomain(reservationRequestDto)));
    }

    @Override
    public void delete(final long id) {
        reservationRepository.findById(id)
                .orElseThrow();

        reservationRepository.deleteById(id);
    }
}
