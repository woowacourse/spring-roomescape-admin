package roomescape.reservationtime.service;

import org.springframework.stereotype.Service;
import roomescape.reservationtime.controller.ReservationTimeRequest;
import roomescape.reservationtime.controller.ReservationTimeResponse;
import roomescape.reservationtime.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTimeResponse saveReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeRepository.save(reservationTimeRequest.getStartAt());
        return new ReservationTimeResponse(id, reservationTimeRequest.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}