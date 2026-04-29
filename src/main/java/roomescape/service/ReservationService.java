package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.command.ReservationCreateCommand;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(
            ReservationRepository reservationRepository
    ) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(
            ReservationCreateCommand createCommand
    ) {
        Reservation reservation = Reservation.create(
                createCommand.name(),
                createCommand.date()
        );
        return reservationRepository.create(reservation, createCommand.timeId());
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }
}
