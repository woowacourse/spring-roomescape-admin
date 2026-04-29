package roomescape.reservation.service;

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
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeService.findById(reservationRequest.timeId());
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

    public void deleteById(long id) {
        if(reservationRepository.existsById(id))
            throw new IllegalArgumentException("[ERROR] 삭제할 예약이 존재하지 않습니다.");

        reservationRepository.deleteById(id);
    }

}
