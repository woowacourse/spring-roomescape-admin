package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> readReservations() {
        return reservationRepository.readReservations()
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        ReservationDao reservationDao = new ReservationDao(reservationRequest.getName(),
                reservationRequest.getDate(), reservationRequest.getTime());
        Long id = reservationRepository.createReservation(reservationDao);
        return new ReservationResponse(id, reservationDao.getName(),
                reservationDao.getDate(), reservationDao. getTime());
    }

    public boolean deleteReservation(Long id) {
        return reservationRepository.deleteReservation(id) == id;
    }
}
