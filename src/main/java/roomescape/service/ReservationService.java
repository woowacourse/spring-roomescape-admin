package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequest;
import roomescape.domain.TimeSlot;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> read() {
        return reservationRepository.read();
    }

    public Reservation create(ReservationRequest reservationRequest) {
        Long reservationId = reservationRepository.create(reservationRequest);
        TimeSlot timeSlot = timeRepository.findById(reservationRequest.timeId());

        return reservationRequest.toEntity(reservationId, timeSlot);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
