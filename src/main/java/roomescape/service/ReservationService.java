package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationRequest request) {
        ReservationTime time = reservationTimeRepository.findById(request.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다: " + request.getTimeId()));

        Reservation reservation = new Reservation(null, request.getName(), request.getDate(), time);
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
