package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(ReservationRepository repository, ReservationTimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> getReservationList() {
        return repository.findAll();
    }

    public Reservation addReservation(ReservationRequest request) {
        ReservationTime reservationTime = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 예약 시간입니다."));
        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                reservationTime
        );

        return repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        repository.delete(id);
    }
}
