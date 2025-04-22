package roomescape.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final Clock clock;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.clock = clock;
    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto createReservation(final CreateReservationDto createReservationDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationDto.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));

        Reservation reservation = createReservationDto.toReservationWith(reservationTime, LocalDateTime.now(clock));
        Reservation savedReservation = reservationRepository.add(reservation);
        return ReservationDto.from(savedReservation);
    }

    public void deleteReservation(final Long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservationRepository.deleteById(id);
    }
}
