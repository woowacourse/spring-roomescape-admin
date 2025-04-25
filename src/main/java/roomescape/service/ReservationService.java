package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponseDto createReservation(final ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId());
        Reservation reservation = new Reservation(reservationRequestDto.name(), reservationRequestDto.date(),
                reservationTime);
        Long id = reservationRepository.add(reservation);
        Reservation savedReservation = reservationRepository.findById(id);
        return ReservationResponseDto.from(savedReservation);
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public void deleteReservation(final Long id) {
        reservationRepository.removeById(id);
    }
}
