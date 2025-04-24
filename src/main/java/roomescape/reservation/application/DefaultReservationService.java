package roomescape.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.application.converter.ReservationConverter;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;
import roomescape.reservation_time.domain.ReservationTime;
import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.domain.ReservationTimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Override
    public List<ReservationResponseDto> getAll() {
        return ReservationConverter.toDto(
                reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDto create(final ReservationRequestDto reservationRequestDto) {
        final ReservationTime reservationTime =
                reservationTimeRepository.findById(ReservationTimeId.from(reservationRequestDto.timeId()))
                        .orElseThrow(NoSuchElementException::new);
        return ReservationConverter.toDto(
                reservationRepository.save(
                        ReservationConverter.toDomain(reservationRequestDto, reservationTime)));
    }

    @Override
    public void delete(final ReservationId id) {
        reservationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        reservationRepository.deleteById(id);
    }
}
