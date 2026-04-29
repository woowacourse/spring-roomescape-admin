package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.controller.dto.ReservationRequest;
import roomescape.reservation.controller.dto.ReservationResponse;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.service.ReservationTimeService;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeService.getById(reservationRequest.timeId());

        LocalDate date = reservationRequest.date();
        LocalTime time = reservationTime.getStartAt();
        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("중복으로 예약을 생성할 수 없습니다.");
        }

        Reservation reservation = Reservation.createNew(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTime
        );
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public void deleteById(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 예약이 존재하지 않습니다.");
        }

        reservationRepository.deleteById(id);
    }

    public ReservationResponse getById(long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        return ReservationResponse.from(reservation);
    }

}
