package roomescape.reservation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationCreateDto;
import roomescape.reservation.dto.ReservationDto;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.service.ReservationTimeService;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public List<ReservationDto> findAllReservations() {
        List<Reservation> result = reservationRepository.findAll();

        return result.stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto saveReservation(ReservationCreateDto request) {
        ReservationTime reservationTime = reservationTimeService.findById(request.timeId());
        validateDuplicateReservation(request);
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

    private void validateDuplicateReservation(ReservationCreateDto request) {
        Boolean existsByDateAndTime = reservationRepository.existsByDateAndTime(request.date(), request.timeId());
        if (existsByDateAndTime) {
            throw new ReservationException("[ERROR] 이미 해당 날짜와 시간에 예약이 존재합니다.");
        }
    }
}
