package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.ReservationQueryingRepository;
import roomescape.repository.ReservationUpdatingRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationQueryingRepository reservationQueryingRepository;
    private final ReservationUpdatingRepository reservationUpdatingRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationQueryingRepository reservationQueryingRepository, ReservationUpdatingRepository reservationUpdatingRepository, ReservationTimeService reservationTimeService) {
        this.reservationQueryingRepository = reservationQueryingRepository;
        this.reservationUpdatingRepository = reservationUpdatingRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAll() {
        return reservationQueryingRepository.findAll();
    }

    public Reservation save(ReservationRequest request) {
        ReservationTime time = reservationTimeService.findById(request.getTimeId());
        Long id = reservationUpdatingRepository.insert(request);
        return new Reservation(id, request.getName(), LocalDate.parse(request.getDate()), time);
    }

    public void delete(Long id) {
        reservationUpdatingRepository.delete(id);
    }
}