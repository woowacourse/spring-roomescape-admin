package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public ReservationResponseDto create(final ReservationRequestDto request) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId());
        final Reservation reservation = new Reservation(request.getName(), request.getDate(), reservationTime);
        final Long id = reservationRepository.save(reservation);

        return new ReservationResponseDto(id, reservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }
}
