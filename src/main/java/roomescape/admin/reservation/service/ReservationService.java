package roomescape.admin.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.service.exception.NoSuchDeleteIdException;
import roomescape.admin.reservation.service.port.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        int rowCount = reservationRepository.delete(id);
        if (rowCount == 0) {
            throw new NoSuchDeleteIdException();
        }
    }
}
