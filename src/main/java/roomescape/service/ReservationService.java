package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation add(String name, String date, String time) {
        return reservationRepository.save(
                Reservation.constructWithNoId(name, date, time)
        );
    }

    public List<Reservation> find() {
        return reservationRepository.findAll();
    }

    public void delete(Long targetId) {
        reservationRepository.delete(targetId);
    }
}
