package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                  final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ReservationTimeResponseDto create(final ReservationTimeRequestDto request) {
        final Long id = reservationTimeRepository.save(new ReservationTime(request.getStartAt()));
        return new ReservationTimeResponseDto(id, request.getStartAt());
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    @Transactional
    public void delete(final long id) {
        final List<Reservation> reservations = reservationRepository.findByTimeId(id);
        if (!reservations.isEmpty()) {
            throw new IllegalArgumentException("Reservation exists with this time");
        }
        reservationTimeRepository.deleteById(id);
    }
}
