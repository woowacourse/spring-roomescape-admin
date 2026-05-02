package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> selectReservations() {
        return reservationRepository.selectReservations();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.selectById(reservationRequest.getTimeId());
        return reservationRepository.insertReservation(reservationRequest, reservationTime);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteReservation(id);
    }
}
