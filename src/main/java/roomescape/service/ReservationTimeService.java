package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeResponse;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long id = reservationTimeRepository.add(reservationTimeCreateRequest.toReservationTime());
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(id);
        if (reservationTime.isPresent()) {
            return ReservationTimeResponse.from(reservationTime.get());
        } else {
            throw new IllegalStateException("예약 시간 추가가 정상적으로 되지 않았습니다.");
        }
    }

    public ReservationTimeResponse getReservationTimeById(Long id) {
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(id);
        if (reservationTime.isPresent()) {
            return ReservationTimeResponse.from(reservationTime.get());
        } else {
            throw new IllegalArgumentException("해당하는 예약 시간이 없습니다.");
        }
    }

    public void deleteReservationTimeById(Long id) {
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(id);
        if (reservationTime.isPresent()) {
            reservationTimeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당하는 예약 시간이 없습니다.");
        }
    }
}
