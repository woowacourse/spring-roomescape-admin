package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.service.ReservationTimeService;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeService.findById(reservationRequestDto.timeId());
        Reservation reservation = Reservation.create(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTime
        );
        return ReservationResponseDto.from(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

}
