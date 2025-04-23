package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long id = reservationTimeRepository.add(reservationTimeCreateRequest.toReservationTime());
        return ReservationTimeResponse.toDto(reservationTimeRepository.findById(id));
    }

    public ReservationTimeResponse getReservationTimeById(Long id) {
        return ReservationTimeResponse.toDto(reservationTimeRepository.findById(id));
    }

    public void deleteReservationTimeById(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
