package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto create(final ReservationTimeRequestDto request) {
        final Long id = reservationTimeRepository.save(new ReservationTime(request.getStartAt()));
        return new ReservationTimeResponseDto(id, request.getStartAt());
    }

    public List<ReservationTimeResponseDto> find() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    public void delete(@PathVariable("id") final Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
