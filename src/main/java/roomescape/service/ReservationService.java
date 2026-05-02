package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.command.ReservationSaveCommand;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation saveReservation(ReservationSaveCommand command) {
        ReservationTime reservationTime = reservationTimeRepository.findById(command.timeId());
        Reservation reservation = new Reservation(null, command.name(), command.date(), reservationTime);
        return reservationRepository.addReservation(reservation);
    }
}
