package roomescape.core.service;

import org.springframework.stereotype.Service;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.repository.ReservationTimeRepository;
import roomescape.core.service.request.ReservationRequestDto;
import roomescape.core.service.response.ReservationResponseDto;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto save(ReservationRequestDto reservationDtoRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationDtoRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약할 수 없는 시간입니다. timeId: " + reservationDtoRequest.timeId()));
        Reservation reservation = reservationDtoRequest.toEntity(reservationTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponseDto.from(savedReservation);
    }

    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }
}
