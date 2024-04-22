package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findReservations();
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    // TODO : Service 계층이 DTO를 파라미터로 받는 것은 어색한가? DTO 대신 각 정보들을 분리한 파라미터로 받아오는 것이 맞나?
    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        return reservationRepository.createReservation(reservationCreateRequest);
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteReservationById(id);
    }
}
