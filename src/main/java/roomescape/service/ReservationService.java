package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationDto> findAllReservations() {
        List<Reservation> result = reservationRepository.findAll();

        return result.stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto saveReservation(ReservationCreateDto request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId());
        Reservation reservation = request.toEntity(reservationTime);

        Long saveId = reservationRepository.save(reservation);
        Reservation saved = Reservation.builder()
                .id(saveId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservationTime)
                .build();

        return ReservationDto.from(saved);
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}
