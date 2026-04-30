package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.ReservationQueryingRepository;
import roomescape.repository.ReservationUpdatingRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationQueryingRepository reservationQueryingRepository;
    private final ReservationUpdatingRepository reservationUpdatingRepository;

    public ReservationService(ReservationQueryingRepository reservationQueryingRepository, ReservationUpdatingRepository reservationUpdatingRepository) {
        this.reservationQueryingRepository = reservationQueryingRepository;
        this.reservationUpdatingRepository = reservationUpdatingRepository;
    }

    public List<Reservation> findAll() {
        return reservationQueryingRepository.findAll();
    }

    public Reservation save(ReservationRequest request) {
        Long id = reservationUpdatingRepository.insert(request);
        return reservationQueryingRepository.findById(id);
    }

    public void delete(Long id) {
        reservationUpdatingRepository.delete(id);
    }
}