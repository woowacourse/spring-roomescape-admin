package roomescape.reservation_time.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation_time.application.converter.ReservationTimeConverter;
import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.domain.ReservationTimeRepository;
import roomescape.reservation_time.ui.dto.ReservationTimeRequestDto;
import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultReservationTimeService implements ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Override
    public List<ReservationTimeResponseDto> getAll() {
        return ReservationTimeConverter.toDto(
                reservationTimeRepository.findAll());
    }

    @Override
    public ReservationTimeResponseDto create(final ReservationTimeRequestDto reservationTimeRequestDto) {
        return ReservationTimeConverter.toDto(
                reservationTimeRepository.save(ReservationTimeConverter.toDomain(reservationTimeRequestDto)));
    }

    @Override
    public void delete(final ReservationTimeId id) {
        reservationTimeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        reservationTimeRepository.deleteById(id);
    }
}
