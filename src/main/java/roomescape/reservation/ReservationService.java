package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationCreateRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.time.ReservationTime;
import roomescape.reservation.time.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto save(ReservationCreateRequestDto request) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId());
        Reservation newReservation = reservationRepository.save(request.toEntity(time));

        return ReservationResponseDto.from(newReservation);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
