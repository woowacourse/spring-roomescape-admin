package roomescape.domain.reservation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservation.entity.ReservationTime;
import roomescape.domain.reservation.repository.ReservationTimeRepository;
import roomescape.domain.reservation.request.ReservationTimeCreateRequest;
import roomescape.domain.reservation.response.ReservationTimeResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public ReservationTimeResponse saveReservationTime(ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = new ReservationTime(
                request.startAt()
        );

        ReservationTime savedTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponse.from(savedTime);
    }

    @Transactional
    public void deleteReservationTimeBy(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
